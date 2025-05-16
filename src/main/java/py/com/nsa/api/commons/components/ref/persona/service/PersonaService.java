package py.com.nsa.api.commons.components.ref.persona.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.empleado.model.Empleado;
import py.com.nsa.api.commons.components.ref.empleado.repository.EmpleadoRepository;
import py.com.nsa.api.commons.components.ref.pdoc.model.PDoc;
import py.com.nsa.api.commons.components.ref.pdoc.repository.PDocRepository;
import py.com.nsa.api.commons.components.ref.persona.model.Persona;
import py.com.nsa.api.commons.components.ref.persona.repository.PersonaRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PDocRepository pDocRepository; // Inyección del repositorio PDocRepository

    public Persona.MensajeRespuesta getPersonasAll() {
        try {
            List<Persona> personas = personaRepository.findAll();
            if (personas.isEmpty()) {
                return new Persona.MensajeRespuesta(204L, "No se encontraron personas.", null);
            }

            // Enriquecer cada persona con sus documentos
            for (Persona persona : personas) {
                List<Object[]> documentosRaw = personaRepository.findDocumentosByPcod(persona.getPcod());
                List<Map<String, Object>> documentos = documentosRaw.stream().map(row -> {
                    Map<String, Object> doc = new HashMap<>();
                    doc.put("docCod", row[0]);           // DOC_COD
                    doc.put("pdocNroDoc", row[1]);       // P_DOC_NRO_DOC
                    doc.put("pcod", row[2]);             // P_COD
                    doc.put("pVencimiento", row[3]);     // P_VENCIMIENTO
                    doc.put("docNombre", row[4]);        // DOC_NOMBRE
                    doc.put("docDescripcion", row[5]);   // DOC_DESCRIPCION
                    return doc;
                }).collect(Collectors.toList());

                // Asignar los documentos a la persona (necesitas un campo para esto)
                persona.setDocumentos(documentos); // Asegúrate de tener un setter para esto
            }

            return new Persona.MensajeRespuesta(200L, "Personas obtenidas exitosamente.", personas);
        } catch (Exception e) {
            e.printStackTrace();
            return new Persona.MensajeRespuesta(500L, "Error al obtener personas: " + e.getMessage(), null);
        }
    }

    public Persona.MensajeRespuesta getPersonaFiltered(Persona filtro) {
        // Configuración del ExampleMatcher para filtros de coincidencia parcial y sin
        // sensibilidad a mayúsculas
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withMatcher("pcod", ExampleMatcher.GenericPropertyMatchers.exact()) // Coincidencia exacta para pcod
                .withMatcher("paCod", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("pnombre", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("papellido", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("pemail", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("pesFisica", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase())
                .withMatcher("psexo", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());

        // Crea un Example de Persona con el filtro y el matcher
        Example<Persona> example = Example.of(filtro, matcher);

        // Realiza la consulta usando el filtro como Example
        List<Persona> personas = personaRepository.findAll(example);

        // Devuelve el resultado en el formato requerido
        if (!personas.isEmpty()) {
            // Obtener los PCOD de las personas encontradas
            List<Long> pcods = personas.stream()
                    .map(Persona::getPcod)  // Obtener el código de la persona (pcod)
                    .collect(Collectors.toList());

            // Obtener los empleados asociados a las personas filtradas
            List<Empleado> empleadosAsociados = empleadoRepository.findByPersonaPcodIn(pcods);

            // Crear un mapa de pcod -> lista de empleados
            Map<Long, List<Map<String, Object>>> empleadosPorPersona = empleadosAsociados.stream()
                    .collect(Collectors.groupingBy(
                            empleado -> empleado.getPersona().getPcod(),
                            Collectors.mapping(
                                    empleado -> {
                                        Map<String, Object> empleadoInfo = new HashMap<>();
                                        empleadoInfo.put("ecod", empleado.getECod());
                                        empleadoInfo.put("pcod", empleado.getPcod());
                                        empleadoInfo.put("ccodCargo", empleado.getCCodCargo());
                                        empleadoInfo.put("parDescripcion", empleado.getParvalor().getParDescripcion());
                                        empleadoInfo.put("efechContratacion", empleado.getEFechContratacion());
                                        empleadoInfo.put("eesPropio", empleado.getEEsPropio());
                                        return empleadoInfo;
                                    },
                                    Collectors.toList())));

            // Enriquecer las personas con la información de los empleados
            List<Persona> personasConEmpleados = personas.stream()
                    .peek(persona -> {
                        List<Map<String, Object>> empleados = empleadosPorPersona.getOrDefault(persona.getPcod(), new ArrayList<>());
                        persona.setEmpleado(empleados);  // Aquí asignas los empleados a cada persona
                    })
                    .collect(Collectors.toList());

            // Retornar la respuesta con las personas enriquecidas
            return new Persona.MensajeRespuesta(200L, "Personas encontradas", personasConEmpleados );
        } else {
            return new Persona.MensajeRespuesta(204L, "No se encontraron Personas", null);
        }
    }


    public Persona.MensajeRespuesta insertarPersona(Persona persona) {
        try {
            // Validar que documentos no sea nulo o vacío
            if (persona.getDocumentos() == null || persona.getDocumentos().isEmpty()) {
                return new Persona.MensajeRespuesta(400L, "El campo 'documentos' es obligatorio.", null);
            }

            // Validar si ya existe una persona con el mismo correo electrónico
            if (existePersonaConMismoEmail(persona.getPemail())) {
                return new Persona.MensajeRespuesta(204L, "Ya existe una persona con la misma dirección de correo electrónico.", null);
            }

            // Validar si ya existe una persona con el mismo nombre, apellido y número de documento
            if (persona.getPDocNroDoc() != null && !persona.getPDocNroDoc().isEmpty()) {
                for (String pDocNroDoc : persona.getPDocNroDoc()) {
                    if (existePersonaConMismoNombreApellidoYDocumento(persona.getPnombre(), persona.getPapellido(), pDocNroDoc)) {
                        return new Persona.MensajeRespuesta(204L, "Ya existe una persona con el mismo nombre, apellido y número de documento.", null);
                    }
                }
            }

            // Lista para acumular errores de documentos duplicados
            List<String> erroresDocumentos = new ArrayList<>();

            // Validar si ya existe un documento con el mismo número pero de otra persona
            for (Map<String, Object> documento : persona.getDocumentos()) {
                String pDocNroDoc = (String) documento.get("pdocNroDoc");
                if (pDocNroDoc != null) {
                    List<PDoc> docsExistentesPorNumero = pDocRepository.findByPDocNroDocIgnoreCase(pDocNroDoc);
                    for (PDoc docExistente : docsExistentesPorNumero) {
                        if (!docExistente.getPcod().equals(persona.getPcod())) {
                            erroresDocumentos.add("Ya existe un documento con el número " + pDocNroDoc + " pero de otra persona.");
                        }
                    }
                }
            }

            // Si hay errores en los documentos, devolverlos todos juntos
            if (!erroresDocumentos.isEmpty()) {
                String mensajeError = String.join("; ", erroresDocumentos); // Unir todos los errores en un solo mensaje
                return new Persona.MensajeRespuesta(204L, mensajeError, null);
            }

            // Si todas las validaciones pasan, guardar la nueva persona
            Persona nuevaPersona = personaRepository.save(persona);
            return new Persona.MensajeRespuesta(200L, "Persona creada exitosamente.", List.of(nuevaPersona));
        } catch (Exception e) {
            e.printStackTrace();
            return new Persona.MensajeRespuesta(500L, "Error al insertar persona: " + e.getMessage(), null);
        }
    }


    // Método para verificar si ya existe una persona con el mismo correo electrónico
    private boolean existePersonaConMismoEmail(String pemail) {
        List<Persona> personasConMismoEmail = personaRepository.findByPemailIgnoreCase(pemail);
        return !personasConMismoEmail.isEmpty();
    }

    // Método para verificar si ya existe una persona con el mismo nombre, apellido y número de documento
    private boolean existePersonaConMismoNombreApellidoYDocumento(String pnombre, String papellido, String pDocNroDoc) {
        List<PDoc> docsExistentes = personaRepository.findByNombreAndApellidoAndPDocNroDoc(pnombre, papellido, pDocNroDoc);
        return !docsExistentes.isEmpty();
    }


    public Persona.MensajeRespuesta updatePersona(Persona persona) {
        try {
            // Validar que la persona exista
            if (persona.getPcod() == null || !personaRepository.existsById(persona.getPcod())) {
                return new Persona.MensajeRespuesta(204L, "Persona no encontrada.", null);
            }

            // Lista para acumular errores
            List<String> errores = new ArrayList<>();

            // Validar si ya existe otra persona con el mismo correo y un código diferente
            List<Persona> personasConMismoCorreo = personaRepository.findByPemailIgnoreCase(persona.getPemail());
            for (Persona personaExistente : personasConMismoCorreo) {
                if (!personaExistente.getPcod().equals(persona.getPcod())) {
                    errores.add("El correo: " + persona.getPemail() + ", ya está registrado por otra persona.");
                }
            }

            // Validar si ya existe una persona con el mismo nombre, apellido y número de documento
            if (persona.getPDocNroDoc() != null && !persona.getPDocNroDoc().isEmpty()) {
                for (String pDocNroDoc : persona.getPDocNroDoc()) {
                    if (existePersonaConMismoNombreApellidoYDocumento(persona.getPnombre(), persona.getPapellido(), pDocNroDoc)) {
                        errores.add("Ya existe una persona con el mismo nombre, apellido y número de documento: " + pDocNroDoc);
                    }
                }
            }

            // Validar si ya existe un documento con los mismos datos pero de otra persona
            if (persona.getDocumentos() != null && !persona.getDocumentos().isEmpty()) {
                for (Map<String, Object> documento : persona.getDocumentos()) {
                    String pDocNroDoc = (String) documento.get("pdocNroDoc");
                    if (pDocNroDoc != null) {
                        List<PDoc> docsExistentesPorNumero = pDocRepository.findByPDocNroDocIgnoreCase(pDocNroDoc);
                        for (PDoc docExistente : docsExistentesPorNumero) {
                            if (!docExistente.getPcod().equals(persona.getPcod())) {
                                errores.add("Ya existe un documento con el número " + pDocNroDoc + " pero de otra persona.");
                            }
                        }
                    }
                }
            }

            // Si hay errores, devolverlos todos juntos
            if (!errores.isEmpty()) {
                String mensajeError = String.join("; ", errores); // Unir todos los errores en un solo mensaje
                return new Persona.MensajeRespuesta(204L, mensajeError, null);
            }

            // Guardar la persona actualizada
            Persona personaActualizada = personaRepository.save(persona);
            return new Persona.MensajeRespuesta(200L, "Persona actualizada exitosamente.", List.of(personaActualizada));
        } catch (Exception e) {
            e.printStackTrace();
            return new Persona.MensajeRespuesta(500L, "Error al actualizar persona: " + e.getMessage(), null);
        }
    }

    public Persona.MensajeRespuesta deletePersona(Long pcodigo) {
        try {
            if (personaRepository.existsById(pcodigo)) {
                personaRepository.deleteById(pcodigo);
                return new Persona.MensajeRespuesta(200L, "Persona eliminada exitosamente.", null);
            } else {
                return new Persona.MensajeRespuesta(204L, "Persona con código " + pcodigo + " no encontrada.", null);
            }
        } catch (JpaSystemException e) {
            // Personalizamos el mensaje de error
            String mensaje = "No se puede eliminar la persona porque está referenciada por otros registros"; // Mensaje
            // personalizado
            return new Persona.MensajeRespuesta(204L,
                    "Error al eliminar la persona: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Persona.MensajeRespuesta(500L,
                    "Error al eliminar persona con código " + pcodigo + ": " + e.getMessage(), null);
        }
    }

    public Map<String, Object> getCiudadByPais(Long paCod) {
        try {
            List<Object[]> results = personaRepository.findCiudadByPais(paCod);
            Map<String, Object> response = new HashMap<>();
            List<Map<String, Object>> barrios = new ArrayList<>();

            for (Object[] result : results) {
                Map<String, Object> detalle = new HashMap<>();
                // detalle.put("bcod", result[0]);
                // detalle.put("bdescripcion", result[1]);
                detalle.put("ciuCod", result[0]);
                detalle.put("ciuDescripcion", result[1]);
                detalle.put("dpCod", result[2]);
                detalle.put("dpDescripcion", result[3]);
                detalle.put("paCod", result[4]);
                detalle.put("paDescripcion", result[4]);

                barrios.add(detalle);
            }

            if (barrios.isEmpty()) {
                response.put("status", 204);
                response.put("mensaje",
                        "No se encontraron Ciudades para el código de pais proporcionado");
                response.put("detalles", null);
            } else {
                response.put("status", 200);
                response.put("mensaje", "Ciudades obtenidas exitosamente");
                response.put("detalles", barrios);
            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("mensaje", "Error al obtener los detalles del barrio: " + e.getMessage());
            errorResponse.put("detalles", null);
            return errorResponse;
        }
    }

    public Map<String, Object> getBarriosByCiudad(Long ciuCod) {
        try {
            List<Object[]> results = personaRepository.findBarriosByCiudad(ciuCod);
            Map<String, Object> response = new HashMap<>();
            List<Map<String, Object>> barrios = new ArrayList<>();

            for (Object[] result : results) {
                Map<String, Object> detalle = new HashMap<>();
                detalle.put("bcod", result[0]);
                detalle.put("bdescripcion", result[1]);
                detalle.put("ciuCod", result[2]);
                detalle.put("ciuDescripcion", result[3]);
                detalle.put("dpCod", result[4]);
                detalle.put("dpDescripcion", result[5]);
                detalle.put("paCod", result[6]);
                detalle.put("paDescripcion", result[7]);

                barrios.add(detalle);
            }

            if (barrios.isEmpty()) {
                response.put("status", 204);
                response.put("mensaje",
                        "No se encontraron barrios para el código de ciudad proporcionado");
                response.put("detalles", null);
            } else {
                response.put("status", 200);
                response.put("mensaje", "barrios obtenidos exitosamente");
                response.put("detalles", barrios);
            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("mensaje", "Error al obtener los detalles del barrio: " + e.getMessage());
            errorResponse.put("detalles", null);
            return errorResponse;
        }
    }

    public Map<String, Object> getbPostal(Long paCod) {
        try {
            List<Object[]> results = personaRepository.findbPostal(paCod);
            Map<String, Object> response = new HashMap<>();
            List<Map<String, Object>> bPostales = new ArrayList<>();

            for (Object[] result : results) {
                Map<String, Object> detalle = new HashMap<>();
                detalle.put("bpostal", result[0]);
                bPostales.add(detalle);
            }

            if (bPostales.isEmpty()) {
                response.put("status", 204);
                response.put("mensaje",
                        "No se encontraron los codigos postales para el código de barrio proporcionado");
                response.put("detalles", null);
            } else {
                response.put("status", 200);
                response.put("mensaje", "Código postale obtenido exitosamente");
                response.put("detalles", bPostales);
            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("mensaje", "Error al obtener los detalles del barrio: " + e.getMessage());
            errorResponse.put("detalles", null);
            return errorResponse;
        }
    }

    public Map<String, Object> getbyDocumento(String PDocNroDoc) {
        try {
            List<Object[]> results = personaRepository.findByCi(PDocNroDoc);
            Map<String, Object> response = new HashMap<>();
            List<Map<String, Object>> persona = new ArrayList<>();

            for (Object[] result : results) {
                Map<String, Object> detalle = new HashMap<>();
                detalle.put("pcod", result[0]);
                detalle.put("bcod", result[1]);
                detalle.put("paCod", result[2]);
                detalle.put("pdocNroDoc", result[3]);
                detalle.put("pnombre", result[4]);
                detalle.put("papellido", result[5]);
                detalle.put("pdireccion", result[6]);
                detalle.put("pemail", result[7]);
                detalle.put("pesFisica", result[8]);
                detalle.put("pfechaNacimiento", result[9]);
                detalle.put("psexo", result[10]);
                detalle.put("ptelefono", result[11]);

                // Datos de Empleado
                detalle.put("ecod", result[12]);
                detalle.put("efechContratacion", result[13]);
                detalle.put("eesPropio", result[14]);
                detalle.put("ccodCargo", result[15]);

                // Datos del Usuario
                detalle.put("usuCod", result[16]);

                // ciudad
                detalle.put("ciuCod", result[17]);
                persona.add(detalle);

                //cliente
                detalle.put("ccod", result[18]);

                //doccod
                detalle.put("docNombre", result[19]);
                detalle.put("docDescripcion", result[20]);
                detalle.put("docCod", result[21]);
            }

            if (persona.isEmpty()) {
                response.put("status", 204);
                response.put("mensaje", "No se encontraron personas con el nombre proporcionado");
                response.put("detalles", null);
            } else {
                response.put("status", 200);
                response.put("mensaje", "Persona obtenida exitosamente");
                response.put("detalles", persona);
            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("mensaje", "Error al obtener los detalles de la persona: " + e.getMessage());
            errorResponse.put("detalles", null);
            return errorResponse;
        }
    }

}
