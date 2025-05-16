package py.com.nsa.api.commons.components.cfg.servicio_agencia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.cfg.servicio_agencia.model.ServicioAgencia;
import py.com.nsa.api.commons.components.cfg.servicio_agencia.model.pk.ServicioAgenciaPK;
import py.com.nsa.api.commons.components.cfg.servicio_agencia.repository.ServicioAgenciaRepository;

import java.util.Date;
import java.util.List;

@Service
public class ServicioAgenciaService {
    @Autowired
    private ServicioAgenciaRepository repository;

    public ServicioAgencia.MensajeRespuesta obtenerTodos() {
        try {
            List<ServicioAgencia> servicios = repository.findAll();
            if (servicios.isEmpty()) {
                return new ServicioAgencia.MensajeRespuesta(204L, "No se encontraron servicios de agencia.", null);
            }
            return new ServicioAgencia.MensajeRespuesta(200L, "Servicios de agencia obtenidos exitosamente.", servicios);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServicioAgencia.MensajeRespuesta(500L, "Error al obtener servicios de agencia: " + e.getMessage(), null);
        }
    }

    public ServicioAgencia.MensajeRespuesta getAgenciasFiltered(ServicioAgencia filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    // Campos de identificación
                    .withMatcher("agCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("parServicio", ExampleMatcher.GenericPropertyMatchers.exact())
                    // Campos de valores
                    .withMatcher("saComiPorcen", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("saComiFijo", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("saMoneda", ExampleMatcher.GenericPropertyMatchers.exact())
                    // Campos de usuario
                    .withMatcher("usuCodCreador", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("usuCodMod", ExampleMatcher.GenericPropertyMatchers.exact())
                    // Campos de fecha
                    .withMatcher("saFechaCrea", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("saFechaMod", ExampleMatcher.GenericPropertyMatchers.exact());

            Example<ServicioAgencia> example = Example.of(filtro, matcher);
            List<ServicioAgencia> servicios = repository.findAll(example);

            if (!servicios.isEmpty()) {
                return new ServicioAgencia.MensajeRespuesta(200L, "Servicios de agencia encontrados", servicios);
            } else {
                return new ServicioAgencia.MensajeRespuesta(204L, "No se encontraron servicios de agencia", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ServicioAgencia.MensajeRespuesta(500L, "Error al buscar servicios de agencia: " + e.getMessage(), null);
        }
    }

    public ServicioAgencia.MensajeRespuesta obtenerPorAgCod(Long agCod) {
        try {
            if (agCod == null) {
                return new ServicioAgencia.MensajeRespuesta(204L, "El código de agencia no puede ser nulo", null);
            }

            List<ServicioAgencia> servicios = repository.findByAgCod(agCod);
            if (servicios.isEmpty()) {
                return new ServicioAgencia.MensajeRespuesta(204L, "No se encontraron servicios para la agencia: " + agCod, null);
            }
            return new ServicioAgencia.MensajeRespuesta(200L, "Servicios de agencia obtenidos exitosamente", servicios);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServicioAgencia.MensajeRespuesta(500L, "Error al obtener servicios de agencia: " + e.getMessage(), null);
        }
    }

    public ServicioAgencia.MensajeRespuesta insert(ServicioAgencia servicioAgencia) {
        try {
            // Validar datos requeridos
            if (servicioAgencia.getParServicio() == null || servicioAgencia.getAgCod() == null) {
                return new ServicioAgencia.MensajeRespuesta(204L, "El código de servicio y código de agencia son requeridos", null);
            }

            servicioAgencia.setSaFechaCrea(new Date());
            servicioAgencia.setSaFechaMod(new Date());

            // Verificar si ya existe
            ServicioAgenciaPK pk = new ServicioAgenciaPK(servicioAgencia.getParServicio(), servicioAgencia.getAgCod());
            if (repository.existsById(pk)) {
                return new ServicioAgencia.MensajeRespuesta(204L, "Ya existe un servicio con los códigos especificados", null);
            }

            ServicioAgencia nuevoServicio = repository.save(servicioAgencia);
            return new ServicioAgencia.MensajeRespuesta(200L, "Servicio de agencia creado exitosamente", List.of(nuevoServicio));
        } catch (Exception e) {
            e.printStackTrace();
            return new ServicioAgencia.MensajeRespuesta(500L, "Error al crear servicio de agencia: " + e.getMessage(), null);
        }
    }

    public ServicioAgencia.MensajeRespuesta update(ServicioAgencia servicioAgencia) {
        try {
            // Validar datos requeridos
            if (servicioAgencia.getParServicio() == null || servicioAgencia.getAgCod() == null) {
                return new ServicioAgencia.MensajeRespuesta(204L, "El código de servicio y código de agencia son requeridos", null);
            }

            // Crear el objeto PK para verificar existencia
            ServicioAgenciaPK pk = new ServicioAgenciaPK(servicioAgencia.getParServicio(), servicioAgencia.getAgCod());

            // Verificar si existe
            if (!repository.existsById(pk)) {
                return new ServicioAgencia.MensajeRespuesta(204L, "No se encontró el servicio de agencia especificado", null);
            }

            // Obtener el servicio existente
            ServicioAgencia existingAgencia = repository.findById(pk)
                    .orElseThrow(() -> new RuntimeException("Servicio de agencia no encontrado."));

            // Mantener datos de creación
            servicioAgencia.setSaFechaCrea(existingAgencia.getSaFechaCrea());
            servicioAgencia.setUsuCodCreador(existingAgencia.getUsuCodCreador());

            // Actualizar fecha de modificación
            servicioAgencia.setSaFechaMod(new Date());

            ServicioAgencia servicioActualizado = repository.save(servicioAgencia);
            return new ServicioAgencia.MensajeRespuesta(200L, "Servicio de agencia actualizado exitosamente", List.of(servicioActualizado));
        } catch (Exception e) {
            e.printStackTrace();
            return new ServicioAgencia.MensajeRespuesta(500L, "Error al actualizar servicio de agencia: " + e.getMessage(), null);
        }
    }
    public ServicioAgencia.MensajeRespuesta delete(String parServicio, Long agCod) {
        try {
            // Validar datos requeridos
            if (parServicio == null || agCod == null) {
                return new ServicioAgencia.MensajeRespuesta(204L, "El código de servicio y código de agencia son requeridos", null);
            }

            // Verificar si existe
            ServicioAgenciaPK pk = new ServicioAgenciaPK(parServicio, agCod);
            if (!repository.existsById(pk)) {
                return new ServicioAgencia.MensajeRespuesta(204L, "No se encontró el servicio de agencia especificado", null);
            }
            repository.deleteById(parServicio, agCod);
            return new ServicioAgencia.MensajeRespuesta(200L,
                    String.format("Servicio de agencia eliminado exitosamente (Servicio: %s, Agencia: %d)", parServicio, agCod),
                    null);
        } catch (JpaSystemException e) {
            return new ServicioAgencia.MensajeRespuesta(204L,
                    "No se puede eliminar el servicio porque está referenciado por otros registros", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServicioAgencia.MensajeRespuesta(500L, "Error al eliminar servicio de agencia: " + e.getMessage(), null);
        }
    }
}