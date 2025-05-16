package py.com.nsa.api.commons.components.cfg.usuario.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.cfg.usuario.repository.UsuarioRepository;
import py.com.nsa.api.commons.components.cfg.usuarioagencia.model.UsuarioAgencia;
import py.com.nsa.api.commons.components.cfg.usuarioagencia.repository.UsuarioAgenciaRepository;
import py.com.nsa.api.commons.components.cfg.usuarioagencia.service.UsuarioAgenciaService;
import py.com.nsa.api.commons.components.ref.empleado.model.Empleado;
import py.com.nsa.api.commons.components.ref.empleado.repository.EmpleadoRepository;
import py.com.nsa.api.commons.components.ref.empleado.service.EmpleadoService;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioAgenciaRepository usuarioAgenciaRepository;

    @Autowired
    private UsuarioAgenciaService usuarioAgenciaService; // Inyectar el servicio de UsuarioAgencia

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoService empleadoService;

    private final ObjectMapper objectMapper;

    public UsuarioService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public Usuario.MensajeRespuesta getUsuariosAll(Long usuCod) {
        try {
            List<Usuario> usuarios;

            // Filtrar solo por `usuCod` si se proporciona
            if (usuCod == null) {
                // Si no se proporciona usuCod, traer todos los usuarios
                usuarios = usuarioRepository.findAll();
            } else {
                // Si se proporciona usuCod, traer solo los usuarios con ese código
                usuarios = usuarioRepository.findAllByUsuCodIn(Collections.singletonList(usuCod));
            }

            // Verificar si se encontraron usuarios
            if (usuarios.isEmpty()) {
                return new Usuario.MensajeRespuesta(204L, "No se encontraron usuarios.", null);
            }

            // Obtener las agencias de los usuarios filtrados
            List<Long> usuarioIds = usuarios.stream()
                    .map(Usuario::getUsuCod)
                    .collect(Collectors.toList());
            List<UsuarioAgencia> usuarioAgencias = usuarioAgenciaRepository.findAllByUsuarioUsuCodIn(usuarioIds);

            // Filtrar los empleados asociados a los usuarios filtrados
            List<Long> empleadosAsociadosIds = usuarios.stream()
                    .map(Usuario::getEmpleado)
                    .filter(Objects::nonNull)
                    .map(Empleado::getECod)
                    .collect(Collectors.toList());

            // Obtener empleados usando getEmpleadosAll de EmpleadoService
            Empleado.MensajeRespuesta empleadosRespuesta = empleadoService.getEmpleadosAll();

            // Verificar si se obtuvieron empleados correctamente
            if (empleadosRespuesta.getStatus() != 200L) {
                return new Usuario.MensajeRespuesta(500L,
                        "Error al obtener empleados: " + empleadosRespuesta.getMensaje(), null);
            }

            // Lista de todos los empleados
            List<Empleado> todosLosEmpleados = empleadosRespuesta.getEmpleados();

            // Filtrar los empleados solo asociados a los usuarios filtrados
            List<Empleado> empleadosFiltrados = new ArrayList<>();
            if (usuCod != null) {
                // Filtrar empleados que están asociados con los usuarios cuyo código coincide
                // con `usuCod`
                empleadosFiltrados = todosLosEmpleados.stream()
                        .filter(empleado -> empleadosAsociadosIds.contains(empleado.getECod()))
                        .collect(Collectors.toList());
            } else {
                // Si no hay filtro de `usuCod`, traer todos los empleados
                empleadosFiltrados = todosLosEmpleados;
            }

            // Filtrar los empleados que no están asociados a los usuarios filtrados
            List<Empleado> empleadosNoAsociados = empleadosFiltrados.stream()
                    .filter(empleado -> empleado.getECod() == null ||
                            !empleadosAsociadosIds.contains(empleado.getECod()))
                    .collect(Collectors.toList());

            // Crear un mapa de usuarioCod -> lista de agencias
            Map<Long, List<Map<String, Object>>> agenciasPorUsuario = usuarioAgencias.stream()
                    .collect(Collectors.groupingBy(
                            ua -> ua.getUsuario().getUsuCod(),
                            Collectors.mapping(
                                    ua -> {
                                        Map<String, Object> agenciaInfo = new HashMap<>();
                                        agenciaInfo.put("agCod", ua.getAgencia().getAgCod());
                                        agenciaInfo.put("agNombreFantasia", ua.getAgencia().getAgNombreFantasia());
                                        agenciaInfo.put("usuNombre", ua.getUsuario().getUsuNombre());
                                        agenciaInfo.put("agenciapais",
                                                ua.getAgencia().getPaisagencia().getPaDescripcion());
                                        agenciaInfo.put("agenciaciudad",
                                                ua.getAgencia().getCiudadagencia().getCiuDescripcion());
                                        agenciaInfo.put("agenciabarrio",
                                                ua.getAgencia().getBarrioagencia().getBdescripcion());
                                        agenciaInfo.put("usuAgeCod", ua.getUsuAgeCod());
                                        agenciaInfo.put("agAgenciaPadre", ua.getAgencia().getAgAgenciaPadre());
                                        return agenciaInfo;
                                    },
                                    Collectors.toList())));

            // Enriquecer los usuarios con la información de las agencias
            List<Usuario> usuariosEnriquecidos = usuarios.stream()
                    .peek(usuario -> {
                        List<Map<String, Object>> agencias = agenciasPorUsuario.getOrDefault(usuario.getUsuCod(),
                                new ArrayList<>());
                        usuario.setAgencias(agencias);
                    })
                    .collect(Collectors.toList());

            // Agregar los empleados no asociados a los usuarios enriquecidos
            for (Empleado empleado : empleadosNoAsociados) {
                // Aquí puedes crear un Usuario falso (sin información real) para asociar el
                // empleado no asociado
                Usuario usuarioFalso = new Usuario();
                usuarioFalso.setEmpleado(empleado); // Asocia el empleado no asociado

                // Añadir a los usuarios enriquecidos
                usuariosEnriquecidos.add(usuarioFalso);
            }

            return new Usuario.MensajeRespuesta(200L, "Usuarios obtenidos exitosamente.", usuariosEnriquecidos);
        } catch (Exception e) {
            e.printStackTrace();
            return new Usuario.MensajeRespuesta(500L, "Error al obtener usuarios: " + e.getMessage(), null);
        }
    }

    // Método para quitar acentos de una cadena
    private String quitarAcentos(String texto) {
        String textoNormalizado = Normalizer.normalize(texto, Form.NFD);
        Pattern patron = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return patron.matcher(textoNormalizado).replaceAll("");
    }

    // Método para generar un nombre de usuario
    public String generarNombreUsuario(String nombre, String apellido) {
        // Obtener la primera letra del nombre sin acento
        String primeraLetraNombre = quitarAcentos(nombre.substring(0, 1).toLowerCase());

        // Obtener el primer apellido completo sin acento
        String[] palabrasApellido = quitarAcentos(apellido).split(" ");
        String primerApellido = palabrasApellido[0].toLowerCase();

        // Concatenar la inicial para formar el nombre de usuario por defecto
        String usuNombre = primeraLetraNombre + primerApellido;

        // Limitar el nombre de usuario a un máximo de 8 caracteres
        if (usuNombre.length() > 8) {
            usuNombre = usuNombre.substring(0, 8);
        }

        // Verificar si el nombre de usuario ya existe en la base de datos
        boolean usuarioExistente = usuarioRepository.existsByusuNombreIgnoreCase(usuNombre);

        // Mientras el nombreUsuario ya exista en la base de datos, sigue agregando
        // letras del primer nombre
        int nroletraNombre = 2; // Inicializamos con '2' para que se agregue la segunda letra del nombre
        while (usuarioExistente) {
            // Agrega la siguiente letra del nombre sin acento
            usuNombre = primeraLetraNombre + quitarAcentos(nombre.substring(1, nroletraNombre).toLowerCase())
                    + primerApellido;

            // Limitar el nombre de usuario a un máximo de 8 caracteres
            if (usuNombre.length() > 8) {
                usuNombre = usuNombre.substring(0, 8);
            }

            // Verificar nuevamente si el nombre de usuario ya existe
            usuarioExistente = usuarioRepository.existsByusuNombreIgnoreCase(usuNombre);

            // Incrementa el índice para el próximo intento
            nroletraNombre++;

            // Si se alcanza el final del nombre y aún se encuentran coincidencias, agregar
            // un número al final
            if (nroletraNombre > nombre.length()) {
                usuNombre = usuNombre + nroletraNombre;
            }
        }

        logger.info("<=== Nombre para usuario Generado {} ... ===>", usuNombre);
        return usuNombre;
    }

    // Método para generar una contraseña por defecto
    public String generarContraseñaPorDefecto() {
        return "ieRhA6dY0fXJ3LfWr04tAw=="; // Contraseña por defecto // 3202@sN --> es el pass por defecto
        // -> no es recomendable retornar en el front se usa @JsonIgnore para no mostrar
        // el campo
    }

    @Transactional
    public Usuario.MensajeRespuesta insertarUsuario(Usuario usuario) {
        try {

            // Verificar si ya existe otro empleado con el mismo ecod
            if (usuarioRepository.existsByeCod(usuario.getECod())) {
                return new Usuario.MensajeRespuesta(204L,
                        "Ya existe otro Usuario registrado con el Documento proporcionado.", null);
            }

            // Quitar acentos y generar nombre de usuario
            String usuNombre = generarNombreUsuario(usuario.getPnombre(), usuario.getPapellido());
            usuario.setUsuNombre(usuNombre);

            // Generar la contraseña por defecto
            usuario.setUsuContrasena(generarContraseñaPorDefecto());

            Usuario nuevoUsuario = usuarioRepository.save(usuario);

            // Crear el objeto UsuarioAgencia con el codigo generado de Usuario y la
            // agenciaId proporcionada
            for (Long agCod : usuario.getAgCod()) {
                UsuarioAgencia usuarioAgencia = UsuarioAgencia.builder()
                        .usuAgeCod(usuario.getUsuAgeCod())
                        .usuCod(nuevoUsuario.getUsuCod()) // Asigna el código del usuario recién insertado
                        .agCod(agCod) // Asigna el código de la agencia
                        // Asigna el código de la agencia(usuario.getAgCod()) // Asigna el código de la
                        // agencia
                        .build();

                // Guardar UsuarioAgencia en la base de datos
                UsuarioAgencia savedUsuarioAgencia = usuarioAgenciaRepository.save(usuarioAgencia);
            }

            return new Usuario.MensajeRespuesta(200L,
                    "Usuario creado exitosamente.", List.of(nuevoUsuario));
        } catch (Exception e) {
            e.printStackTrace();
            return new Usuario.MensajeRespuesta(500L, "Error al insertar el usuario: " + e.getMessage(), null);
        }
    }

    @Transactional
    public Usuario.MensajeRespuesta updateUsuario(Usuario usuario) {
        try {
            // Validación del código de usuario
            if (usuario.getUsuCod() == null || !usuarioRepository.existsById(usuario.getUsuCod())) {
                return new Usuario.MensajeRespuesta(204L, "Usuario no encontrado.", null);
            }

            String usuNombre = usuarioRepository.getusuNombre(usuario.getUsuCod());
            usuario.setUsuNombre(usuNombre);

            String usuContrasena = usuarioRepository.getusuContrasena(usuario.getUsuCod());
            usuario.setUsuContrasena(usuContrasena);

            Long eCod = usuarioRepository.getECod(usuario.getUsuCod());
            usuario.setECod(eCod);

            String usuCambioPass = usuarioRepository.getusuCambioPass(usuario.getUsuCod());
            usuario.setUsuCambioPass(usuCambioPass);

            Long usuContador = usuarioRepository.getusuContador(usuario.getUsuCod());
            usuario.setUsuContador(usuContador);

            Usuario updatedUsuario = usuarioRepository.save(usuario);

            // Crear el objeto UsuarioAgencia y llamar al método del servicio UsuarioAgencia

            for (Long agCod : usuario.getAgCod()) {
                UsuarioAgencia usuarioAgencia = UsuarioAgencia.builder()
                        .usuAgeCod(usuario.getUsuAgeCod())
                        .usuCod(usuario.getUsuCod())
                        .agCod(agCod)
                        .build();

                // Llamada al servicio de inserción
                UsuarioAgencia.MensajeRespuesta agenciaRespuesta = usuarioAgenciaService
                        .insertarUsuarioAgencia(usuarioAgencia);
                if (agenciaRespuesta.getStatus() != 200L) {
                    return new Usuario.MensajeRespuesta(500L,
                            "Error al insertar la agencia: " + agenciaRespuesta.getMensaje(), null);
                }
            }

            return new Usuario.MensajeRespuesta(200L, "Usuario actualizado exitosamente.", List.of(updatedUsuario));
        } catch (Exception e) {
            e.printStackTrace(); // Cambiar a un logger para mejor manejo de logs
            return new Usuario.MensajeRespuesta(500L, "Error al actualizar el usuario: " + e.getMessage(), null);
        }
    }

    @Transactional
    public Usuario.MensajeRespuesta acciones(Usuario usuario) {
        try {

            // Validación del código de usuario
            if (usuario.getUsuCod() == null || !usuarioRepository.existsById(usuario.getUsuCod())) {
                return new Usuario.MensajeRespuesta(204L, "Usuario no encontrado.", null);
            }

            if (usuario.getAction() == 1) {
                // Cambiar contraseña
                Usuario existingUsuario = usuarioRepository.findById(usuario.getUsuCod())
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuario.getUsuCod()));

                String usuNombre = usuarioRepository.getusuNombre(usuario.getUsuCod());
                usuario.setUsuNombre(usuNombre);
                Long eCod = usuarioRepository.getECod(usuario.getUsuCod());
                usuario.setECod(eCod);
                Long usuContador = usuarioRepository.getusuContador(usuario.getUsuCod());
                usuario.setUsuContador(usuContador);

                // Define passwords for comparison
                String defaultPassword = generarContraseñaPorDefecto(); // "ieRhA6dY0fXJ3LfWr04tAw=="
                String currentPassword = existingUsuario.getUsuContrasena(); // Current encrypted password from DB
                String newPassword = usuario.getUsuContrasena(); // New password from request (plaintext)

                // Encrypt the new password once for comparison
                String encryptedNewPassword = encrypt(newPassword);

                // Condición 1: No permitir cambiar a la contraseña actual
                if (currentPassword.equals(encryptedNewPassword)) {
                    return new Usuario.MensajeRespuesta(204L,
                            "La nueva contraseña no puede ser igual a la contraseña actual.", null);
                }

                // Condición 2: No permitir cambiar a la contraseña por defecto
                if (defaultPassword.equals(encryptedNewPassword)) {
                    return new Usuario.MensajeRespuesta(204L,
                            "No puedes usar la contraseña por defecto.", null);

                }

                // Si pasa las validaciones, actualizar la contraseña
                usuario.setUsuContrasena(encryptedNewPassword);
                usuario.setUsuCambioPass("S"); // Marcar como cambiada
                usuario.setUsuFechaMod(new Date()); // Actualizar fecha de modificación

                Usuario updatedUsuario = usuarioRepository.save(usuario);
                return new Usuario.MensajeRespuesta(200L,
                        "Usuario: " + usuario.getUsuNombre() + " Contraseña cambiada exitosamente.",
                        List.of(updatedUsuario));
            } else if (usuario.getAction() == 2) {
                String usuNombre = usuarioRepository.getusuNombre(usuario.getUsuCod());
                usuario.setUsuNombre(usuNombre);

                String usuContrasena = usuarioRepository.getusuContrasena(usuario.getUsuCod());
                usuario.setUsuContrasena(usuContrasena);

                Long eCod = usuarioRepository.getECod(usuario.getUsuCod());
                usuario.setECod(eCod);

                String usuCambioPass = usuarioRepository.getusuCambioPass(usuario.getUsuCod());
                usuario.setUsuCambioPass(usuCambioPass);

                Long usuContador = usuarioRepository.getusuContador(usuario.getUsuCod());
                usuario.setUsuContador(usuContador);

                /*
                 * Date usuFechavenc = usuarioRepository.getusuFechavenc(usuario.getUsuCod());
                 * usuario.setUsuFechavenc(usuFechavenc);
                 */

                Usuario updatedUsuario = usuarioRepository.save(usuario);

                return new Usuario.MensajeRespuesta(200L,
                        "Usuario: " + usuario.getUsuNombre() + " Activado exitosamente.", List.of(updatedUsuario));

            } else if (usuario.getAction() == 3) {
                String usuNombre = usuarioRepository.getusuNombre(usuario.getUsuCod());
                usuario.setUsuNombre(usuNombre);

                String usuContrasena = usuarioRepository.getusuContrasena(usuario.getUsuCod());
                usuario.setUsuContrasena(usuContrasena);

                Long eCod = usuarioRepository.getECod(usuario.getUsuCod());
                usuario.setECod(eCod);

                String usuCambioPass = usuarioRepository.getusuCambioPass(usuario.getUsuCod());
                usuario.setUsuCambioPass(usuCambioPass);

                Long usuContador = usuarioRepository.getusuContador(usuario.getUsuCod());
                usuario.setUsuContador(usuContador);

                Date usuFechavenc = usuarioRepository.getusuFechavenc(usuario.getUsuCod());
                usuario.setUsuFechavenc(usuFechavenc);

                Usuario updatedUsuario = usuarioRepository.save(usuario);
                return new Usuario.MensajeRespuesta(200L,
                        "Usuario: " + usuario.getUsuNombre() + " Desactivado exitosamente.", List.of(updatedUsuario));
            } else if (usuario.getAction() == 4) {
                // Cambiar contraseña
                Usuario existingUsuario = usuarioRepository.findById(usuario.getUsuCod())
                        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuario.getUsuCod()));

                String usuNombre = usuarioRepository.getusuNombre(usuario.getUsuCod());
                usuario.setUsuNombre(usuNombre);
                Long eCod = usuarioRepository.getECod(usuario.getUsuCod());
                usuario.setECod(eCod);
                Long usuContador = usuarioRepository.getusuContador(usuario.getUsuCod());
                usuario.setUsuContador(usuContador);

                // Define passwords for comparison
                String defaultPassword = generarContraseñaPorDefecto(); // "ieRhA6dY0fXJ3LfWr04tAw=="
                String currentPassword = existingUsuario.getUsuContrasena(); // Current encrypted password from DB
                String newPassword = usuario.getUsuContrasena(); // New password from request (plaintext)

                // Encrypt the new password once for comparison
                String encryptedNewPassword = encrypt(newPassword);

                // Condición 1: No permitir cambiar a la contraseña actual
                if (currentPassword.equals(encryptedNewPassword)) {
                    return new Usuario.MensajeRespuesta(204L,
                            "La nueva contraseña no puede ser igual a la contraseña actual.", null);
                }

                // Condición 2: No permitir cambiar a la contraseña por defecto
                if (defaultPassword.equals(encryptedNewPassword)) {
                    return new Usuario.MensajeRespuesta(204L,
                            "No puedes usar la contraseña por defecto.", null);

                }

                // Si pasa las validaciones, actualizar la contraseña
                usuario.setUsuContrasena(encryptedNewPassword);
                usuario.setUsuCambioPass("S"); // Marcar como cambiada
                usuario.setUsuFechaMod(new Date()); // Actualizar fecha de modificación

                Usuario updatedUsuario = usuarioRepository.save(usuario);
                return new Usuario.MensajeRespuesta(200L,
                        "Usuario: " + usuario.getUsuNombre() + " Contraseña cambiada exitosamente.",
                        List.of(updatedUsuario));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Cambiar a un logger para mejor manejo de logs
            return new Usuario.MensajeRespuesta(500L, "Error al modificar los datos del usuario: " + e.getMessage(),
                    null);
        }
        return new Usuario.MensajeRespuesta(200L, "ninguna acción fue especificada", null);
    }

    public Usuario.MensajeRespuesta getUsuarioFiltered(Usuario filtro) {
        // Configuración del ExampleMatcher para filtros de coincidencia parcial y sin
        // sensibilidad a mayúsculas
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues() // Ignorar valores nulos en el filtro
                .withMatcher("usuCod", ExampleMatcher.GenericPropertyMatchers.exact()) // Coincidencia exacta para
                                                                                       // usuCod
                .withMatcher("usuNombre", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()) // Coincidencia
                                                                                                          // parcial y
                                                                                                          // sin
                                                                                                          // mayúsculas
                .withMatcher("usuEstado", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()); // Coincidencia
                                                                                                           // parcial y
                                                                                                           // sin
                                                                                                           // mayúsculas

        // Crear un Example de Usuario usando el filtro y el matcher
        Example<Usuario> example = Example.of(filtro, matcher);

        // Realizar la consulta usando el filtro como Example
        List<Usuario> usuarios = usuarioRepository.findAll(example);

        // Devuelve el resultado en el formato requerido
        if (!usuarios.isEmpty()) {
            return new Usuario.MensajeRespuesta(200L, "Usuarios encontrados", usuarios);
        } else {
            return new Usuario.MensajeRespuesta(204L, "No se encontraron Usuarios", null);
        }
    }

    /*
     * public Usuario.MensajeRespuesta deleteUsuario(Long usuCod) {
     * try {
     * if (usuarioRepository.existsById(usuCod)) {
     * usuarioRepository.deleteById(usuCod);
     * return new Usuario.MensajeRespuesta(200L, "Usuario eliminado exitosamente.",
     * null);
     * } else {
     * return new Usuario.MensajeRespuesta(204L, "Usuario no encontrado.", null);
     * }
     * } catch (JpaSystemException e) {
     * // Personalizamos el mensaje de error
     * String mensaje =
     * "No se puede eliminar el Usuario porque está referenciado por otros registros"
     * ; // Mensaje
     * // personalizado
     * return new Usuario.MensajeRespuesta(204L,
     * "Error al eliminar el Usuario: " + mensaje, null);
     * } catch (Exception e) {
     * e.printStackTrace();
     * return new Usuario.MensajeRespuesta(500L, "Error al eliminar el usuario: " +
     * e.getMessage(), null);
     * }
     * }
     */

    public static String encrypt(String plainText) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static Key generateKey() {
        return new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
    }

    private static final String SECRET_KEY = "@NsA2023u1536@23";

}
