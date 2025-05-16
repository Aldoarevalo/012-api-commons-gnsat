package py.com.nsa.api.commons.components.ref.empleado.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.empleado.model.Empleado;
import py.com.nsa.api.commons.components.ref.empleado.repository.EmpleadoRepository;
import py.com.nsa.api.commons.components.ref.persona.model.Persona;
import py.com.nsa.api.commons.components.ref.persona.repository.PersonaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmpleadoService {

    private static final Logger logger = LoggerFactory.getLogger(EmpleadoService.class);
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    public Empleado.MensajeRespuesta getEmpleadosAll() {
        try {
            // Obtener todos los empleados
            List<Empleado> empleados = empleadoRepository.findAll();

            // Si no hay empleados, retornar respuesta sin datos
            if (empleados.isEmpty()) {
                return new Empleado.MensajeRespuesta(204L, "No se encontraron empleados.", null);
            }

            // Obtener los IDs de las personas asociadas a empleados
            List<Long> personasAsociadasIds = empleados.stream()
                    .map(Empleado::getPersona) // Obtener la persona asociada a cada empleado
                    .filter(Objects::nonNull) // Ignorar empleados sin persona asociada
                    .map(Persona::getPcod) // Obtener el código de la persona
                    .collect(Collectors.toList());

            // Obtener las personas que no están asociadas a ningún empleado
            List<Persona> personasNoAsociadas = personaRepository.findAllByPcodNotIn(personasAsociadasIds);

            // Crear una lista enriquecida basada en empleados existentes
            List<Empleado> empleadosEnriquecidos = new ArrayList<>(empleados);

            // Agregar las personas no asociadas como empleados ficticios
            for (Persona persona : personasNoAsociadas) {
                Empleado empleadoFalso = new Empleado();
                empleadoFalso.setPersona(persona); // Asocia la persona no asociada
                // Asigna otros campos ficticios si es necesario
                empleadosEnriquecidos.add(empleadoFalso);
            }

            // Retornar la lista enriquecida
            return new Empleado.MensajeRespuesta(200L, "Empleados y personas no asociadas obtenidos exitosamente.",
                    empleadosEnriquecidos);

        } catch (Exception e) {
            e.printStackTrace();
            return new Empleado.MensajeRespuesta(500L, "Error al obtener empleados: " + e.getMessage(), null);
        }
    }

    public Empleado.MensajeRespuesta getEmpleadosFiltered(Empleado filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("eCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("pcod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("cCodCargo", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("eEsPropio", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase())
                    .withMatcher("persona.pnombre", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("persona.papellido", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            Example<Empleado> example = Example.of(filtro, matcher);
            List<Empleado> empleados = empleadoRepository.findAll(example);

            if (empleados.isEmpty()) {
                return new Empleado.MensajeRespuesta(204L, "No se encontraron empleados.", null);
            }
            return new Empleado.MensajeRespuesta(200L, "Empleados encontrados.", empleados);
        } catch (Exception e) {
            logger.error("Error al filtrar empleados: ", e);
            return new Empleado.MensajeRespuesta(500L, "Error al filtrar empleados: " + e.getMessage(), null);
        }
    }

    public Empleado.MensajeRespuesta insertarEmpleado(Empleado empleado) {
        try {
            // Verificar si ya existe otro empleado con el mismo pcod
            if (empleadoRepository.existsByPcod(empleado.getPcod())) {
                return new Empleado.MensajeRespuesta(204L,
                        "Ya existe otro empleado registrado con el código de persona proporcionado.", null);
            }

            Empleado nuevoEmpleado = empleadoRepository.save(empleado);
            return new Empleado.MensajeRespuesta(200L, "Empleado creado exitosamente.", List.of(nuevoEmpleado));
        } catch (Exception e) {
            System.err.println("Error al insertar el empleado: " + e.getMessage());
            e.printStackTrace();
            return new Empleado.MensajeRespuesta(500L, "Error al insertar el empleado: " + e.getMessage(), null);
        }
    }

    public Empleado.MensajeRespuesta updateEmpleado(Empleado empleado) {
        try {
            if (empleado.getECod() == null || !empleadoRepository.existsById(empleado.getECod())) {
                return new Empleado.MensajeRespuesta(204L, "Empleado no encontrado.", null);
            }

            // Verificar si se está intentando cambiar el código
            if (!Objects.equals(empleado.getOldpcod(), empleado.getNuevapcod())) {
                return new Empleado.MensajeRespuesta(204L, "No se puede cambiar el código del empleado.", null);
            }

            Empleado updatedEmpleado = empleadoRepository.save(empleado);
            return new Empleado.MensajeRespuesta(200L, "Empleado actualizado exitosamente.", List.of(updatedEmpleado));
        } catch (Exception e) {
            System.err.println("Error al actualizar el empleado: " + e.getMessage());
            e.printStackTrace();
            return new Empleado.MensajeRespuesta(500L, "Error al actualizar el empleado: " + e.getMessage(), null);
        }
    }

    public Empleado.MensajeRespuesta deleteEmpleado(Long eCod) {
        try {

            if (empleadoRepository.existsById(eCod)) {
                empleadoRepository.deleteById(eCod);
                return new Empleado.MensajeRespuesta(200L, "Empleado eliminado exitosamente",
                        null);
            } else {
                return new Empleado.MensajeRespuesta(204L, "Empleado con código " + eCod + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Personalizamos el mensaje de error
            String mensaje = "No se puede eliminar el empleado porque está referenciado por otros registros"; // Mensaje
            // personalizado
            return new Empleado.MensajeRespuesta(204L,
                    "Error al eliminar el Empleado: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Empleado.MensajeRespuesta(500L,
                    "Error al eliminar el empleado con código " + eCod + ": " + e.getMessage(), null);
        }
    }

}
