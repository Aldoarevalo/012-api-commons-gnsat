package py.com.nsa.api.commons.components.trx.recorrido.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.trx.recorrido.model.TrxRecorrido;
import py.com.nsa.api.commons.components.trx.recorrido.model.TrxRecorridoId;
import py.com.nsa.api.commons.components.trx.recorrido.repository.TrxRecorridoRepository;

import java.util.Collections;
import java.util.List;

@Service
public class TrxRecorridoService {

    private static final Logger logger = LoggerFactory.getLogger(TrxRecorridoService.class);

    @Autowired
    private TrxRecorridoRepository repository;

    public TrxRecorrido.MensajeRespuesta listarTodos() {
        try {
            List<TrxRecorrido> recorridos = repository.findAll();
            if (recorridos.isEmpty()) {
                return new TrxRecorrido.MensajeRespuesta(204L, "No se encontraron recorridos.", null);
            }
            return new TrxRecorrido.MensajeRespuesta(200L, "Recorridos obtenidos exitosamente.", recorridos);
        } catch (Exception e) {
            logger.error("Error al listar recorridos: ", e);
            return new TrxRecorrido.MensajeRespuesta(500L, "Error interno al obtener recorridos: " + e.getMessage(), null);
        }
    }

    public TrxRecorrido.MensajeRespuesta insertar(TrxRecorrido recorrido) {
        try {
            if (recorrido == null) {
                return new TrxRecorrido.MensajeRespuesta(400L, "El recorrido no puede ser nulo.", null);
            }
            if (recorrido.getId() == null) {
                return new TrxRecorrido.MensajeRespuesta(400L, "La clave primaria (id) no puede ser nula.", null);
            }
            // Verificar si ya existe un registro con la misma clave primaria
            if (repository.existsById(recorrido.getId())) {
                return new TrxRecorrido.MensajeRespuesta(409L,
                        "Ya existe un recorrido con la combinación de id: " + recorrido.getId(), null);
            }

            logger.info("Recorrido antes de guardar: {}", recorrido);
            TrxRecorrido nuevo = repository.save(recorrido);
            return new TrxRecorrido.MensajeRespuesta(201L, "Recorrido creado exitosamente.", Collections.singletonList(nuevo));
        } catch (DataIntegrityViolationException e) {
            logger.error("Error de integridad al insertar recorrido (clave duplicada): ", e);
            return new TrxRecorrido.MensajeRespuesta(409L,
                    "No se puede insertar el recorrido porque ya existe un registro con id: " + recorrido.getId(), null);
        } catch (Exception e) {
            logger.error("Error al insertar recorrido: ", e);
            return new TrxRecorrido.MensajeRespuesta(500L, "Error interno al insertar recorrido: " + e.getMessage(), null);
        }
    }

    public TrxRecorrido.MensajeRespuesta actualizar(TrxRecorrido recorrido) {
        try {
            if (recorrido == null) {
                return new TrxRecorrido.MensajeRespuesta(400L, "El recorrido no puede ser nulo.", null);
            }
            if (recorrido.getId() == null) {
                return new TrxRecorrido.MensajeRespuesta(400L, "La clave primaria (id) no puede ser nula.", null);
            }
            if (!repository.existsById(recorrido.getId())) {
                return new TrxRecorrido.MensajeRespuesta(404L, "No se encontró el recorrido con id: " + recorrido.getId(), null);
            }
            TrxRecorrido actualizado = repository.save(recorrido);
            return new TrxRecorrido.MensajeRespuesta(200L, "Recorrido actualizado exitosamente.", Collections.singletonList(actualizado));
        } catch (Exception e) {
            logger.error("Error al actualizar recorrido: ", e);
            return new TrxRecorrido.MensajeRespuesta(500L, "Error interno al actualizar recorrido: " + e.getMessage(), null);
        }
    }

    public TrxRecorrido.MensajeRespuesta eliminar(TrxRecorridoId id) {
        try {
            if (id == null) {
                return new TrxRecorrido.MensajeRespuesta(400L, "La clave primaria (id) no puede ser nula.", null);
            }
            if (!repository.existsById(id)) {
                return new TrxRecorrido.MensajeRespuesta(404L, "No se encontró el recorrido con id: " + id, null);
            }
            repository.deleteById(id);
            return new TrxRecorrido.MensajeRespuesta(200L, "Recorrido eliminado exitosamente.", null);
        } catch (Exception e) {
            logger.error("Error al eliminar recorrido: ", e);
            return new TrxRecorrido.MensajeRespuesta(500L, "Error interno al eliminar recorrido: " + e.getMessage(), null);
        }
    }
}