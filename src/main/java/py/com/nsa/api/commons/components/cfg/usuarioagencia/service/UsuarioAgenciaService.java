package py.com.nsa.api.commons.components.cfg.usuarioagencia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.cfg.agencia.repository.AgenciaRepository;
import py.com.nsa.api.commons.components.cfg.usuario_rol.model.CfgUsuarioRol;
import py.com.nsa.api.commons.components.cfg.usuario_rol.repository.CfgUsuarioRolRepository;
import py.com.nsa.api.commons.components.cfg.usuarioagencia.model.UsuarioAgencia;
import py.com.nsa.api.commons.components.cfg.usuarioagencia.repository.UsuarioAgenciaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UsuarioAgenciaService {

    @Autowired
    private UsuarioAgenciaRepository usuarioAgenciaRepository;

    @Autowired
    private AgenciaRepository repository;

    @Autowired
    private CfgUsuarioRolRepository cfgUsuarioRolRepository;

    public UsuarioAgencia.MensajeRespuesta getUsuarioAgenciasAll() {
        try {
            // Obtener todas las relaciones usuario-agencia
            List<UsuarioAgencia> usuarioAgencias = usuarioAgenciaRepository.findAll();

            // Verificar si la lista está vacía
            if (usuarioAgencias.isEmpty()) {
                return new UsuarioAgencia.MensajeRespuesta(204L, "No se encontraron registros de usuario-agencia.",
                        null);
            }

            // Obtener los IDs de usuarios únicos
            List<Long> usuarioIds = usuarioAgencias.stream()
                    .map(UsuarioAgencia::getUsuCod)
                    .collect(Collectors.toList());

            // Buscar todas las agencias asociadas a esos usuarios
            List<UsuarioAgencia> agenciasDeUsuarios = usuarioAgenciaRepository.findAllByUsuarioUsuCodIn(usuarioIds);

            // Crear un mapa de usuCod a lista de agencias
            Map<Long, List<Map<String, Object>>> agenciasPorUsuario = agenciasDeUsuarios.stream()
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
                                        return agenciaInfo;
                                    },
                                    Collectors.toList())));

            // Enriquecer la lista de usuarios con las agencias correspondientes
            List<UsuarioAgencia> usuariosagenciaEnriquecidos = usuarioAgencias.stream()
                    .peek(usuario -> {
                        List<Map<String, Object>> agencias = agenciasPorUsuario.getOrDefault(usuario.getUsuCod(),
                                new ArrayList<>());
                        usuario.setAgencias(agencias); // Asignar la lista de agencias al usuario
                    })
                    .collect(Collectors.toList());

            return new UsuarioAgencia.MensajeRespuesta(200L, "Usuario-Agencia obtenidos exitosamente.",
                    usuariosagenciaEnriquecidos);
        } catch (Exception e) {
            return new UsuarioAgencia.MensajeRespuesta(500L,
                    "Error al obtener los registros de usuario-agencia: " + e.getMessage(), null);
        }
    }

    public Agencia.MensajeRespuesta getAgenciasUsuariosAll(Long usuCod) {
        try {
            // Verificar que usuCod no sea null antes de hacer la consulta
            if (usuCod == null) {
                return new Agencia.MensajeRespuesta(204L, "El código de usuario (usuCod) es requerido.", null);
            }

            // Obtener los roles del usuario
            List<CfgUsuarioRol> rolesUsuario = cfgUsuarioRolRepository.findByUsuCod(usuCod);

            // Verificar si el usuario tiene rol ADMIN
            boolean isAdmin = rolesUsuario.stream()
                    .anyMatch(rol -> rol.getRol() != null && "ADMIN".equals(rol.getRol().getRolNombre()))
                    && usuCod.equals(5326L);

            List<Agencia> agencias;
            if (isAdmin) {
                // Si es admin, obtener todas las agencias
                agencias = repository.findAll();
            } else {
                // Si no es admin, obtener agencias filtradas
                agencias = repository.findAllWithFilters(usuCod);
            }

            if (agencias.isEmpty()) {
                return new Agencia.MensajeRespuesta(204L, "No se encontraron Agencias.", null);
            }

            return new Agencia.MensajeRespuesta(200L, "Agencias obtenidas exitosamente.", agencias);

        } catch (Exception e) {
            System.err.println("Error al obtener las Agencias: " + e.getMessage());
            e.printStackTrace();
            return new Agencia.MensajeRespuesta(500L, "Error al obtener las Agencias: " + e.getMessage(), null);
        }
    }

    public UsuarioAgencia.MensajeRespuesta insertarUsuarioAgencia(UsuarioAgencia usuarioAgencia) {
        try {
            UsuarioAgencia nuevoUsuarioAgencia = usuarioAgenciaRepository.save(usuarioAgencia);
            return new UsuarioAgencia.MensajeRespuesta(200L, "Usuario-Agencia creado exitosamente.",
                    List.of(nuevoUsuarioAgencia));
        } catch (Exception e) {
            return new UsuarioAgencia.MensajeRespuesta(500L,
                    "Error al insertar el registro usuario-agencia: " + e.getMessage(), null);
        }
    }

    public UsuarioAgencia.MensajeRespuesta getUsuarioAgeFiltered(UsuarioAgencia filtro) {
        List<UsuarioAgencia> usuarioAgencia = usuarioAgenciaRepository.findAll();

        if (filtro.getUsuCod() != null) {
            usuarioAgencia = usuarioAgencia.stream()
                    .filter(a -> a.getUsuCod().equals(filtro.getUsuCod()))
                    .collect(Collectors.toList());
        }

        if (filtro.getAgCod() != null) {
            usuarioAgencia = usuarioAgencia.stream()
                    .filter(a -> a.getAgCod().equals(filtro.getAgCod()))
                    .collect(Collectors.toList());
        }

        if (!usuarioAgencia.isEmpty()) {
            return new UsuarioAgencia.MensajeRespuesta(200L, "Usuario Agencia encontradas", usuarioAgencia);
        } else {
            return new UsuarioAgencia.MensajeRespuesta(204L, "No se encontraron Usuario Agencia", null);
        }
    }

    public UsuarioAgencia.MensajeRespuesta updateUsuarioAgencia(UsuarioAgencia usuarioAgencia) {
        try {
            if (usuarioAgencia.getUsuAgeCod() == null
                    || !usuarioAgenciaRepository.existsById(usuarioAgencia.getUsuAgeCod())) {
                return new UsuarioAgencia.MensajeRespuesta(204L, "Usuario-Agencia no encontrado.", null);
            }
            UsuarioAgencia updatedUsuarioAgencia = usuarioAgenciaRepository.save(usuarioAgencia);
            return new UsuarioAgencia.MensajeRespuesta(200L, "Usuario-Agencia actualizado exitosamente.",
                    List.of(updatedUsuarioAgencia));
        } catch (Exception e) {
            return new UsuarioAgencia.MensajeRespuesta(500L,
                    "Error al actualizar el registro usuario-agencia: " + e.getMessage(), null);
        }
    }

    public UsuarioAgencia.MensajeRespuesta deleteUsuarioAgencia(Long usuAgeCod) {
        try {
            // Verificar si el registro con el ID proporcionado existe
            if (usuarioAgenciaRepository.existsById(usuAgeCod)) {
                // Eliminar el registro si existe
                usuarioAgenciaRepository.deleteById(usuAgeCod);
                return new UsuarioAgencia.MensajeRespuesta(200L, "Usuario-Agencia eliminado exitosamente.", null);
            } else {
                // Si no existe, devolver respuesta con código 204
                return new UsuarioAgencia.MensajeRespuesta(204L,
                        "Registro usuario-agencia con código " + usuAgeCod + " no encontrado.", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el registro usuario-agencia porque está referenciado por otros registros.";
            return new UsuarioAgencia.MensajeRespuesta(400L,
                    "Error al eliminar el registro usuario-agencia: " + mensaje, null);
        } catch (Exception e) {
            // Otros errores generales
            return new UsuarioAgencia.MensajeRespuesta(500L,
                    "Error al eliminar el registro usuario-agencia con código " + usuAgeCod + ": " + e.getMessage(),
                    null);
        }
    }

}
