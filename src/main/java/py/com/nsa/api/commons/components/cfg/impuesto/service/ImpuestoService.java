package py.com.nsa.api.commons.components.cfg.impuesto.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.impuesto.model.Impuesto;
import py.com.nsa.api.commons.components.cfg.impuesto.repository.ImpuestoRepository;
import java.util.List;

@Service
public class ImpuestoService {
    private static final Logger logger = LoggerFactory.getLogger(ImpuestoService.class);

    @Autowired
    private ImpuestoRepository impuestoRepository;

    public Impuesto.MensajeRespuesta getImpuestosAll() {
        try {
            List<Impuesto> impuestos = impuestoRepository.findAll();
            if (impuestos.isEmpty()) {
                return new Impuesto.MensajeRespuesta(204L, "No se encontraron impuestos.", null);
            }
            return new Impuesto.MensajeRespuesta(200L, "Impuestos encontrados.", impuestos);
        } catch (Exception e) {
            logger.error("Error al obtener impuestos: ", e);
            return new Impuesto.MensajeRespuesta(500L, "Error al obtener impuestos: " + e.getMessage(), null);
        }
    }

    public Impuesto.MensajeRespuesta getImpuestoByImpCod(Long impCod) {
        try {
            Impuesto impuesto = impuestoRepository.findByImpCod(impCod);

            if (impuesto == null) {
                return new Impuesto.MensajeRespuesta(204L, "No se encontró el impuesto con el código: " + impCod, null);
            }

            return new Impuesto.MensajeRespuesta(200L, "Impuesto encontrado.", List.of(impuesto));
        } catch (Exception e) {
            logger.error("Error al obtener el impuesto: ", e);
            return new Impuesto.MensajeRespuesta(500L, "Error al obtener el impuesto: " + e.getMessage(), null);
        }
    }

    public Impuesto.MensajeRespuesta insertarImpuesto(Impuesto impuesto) {
        try {
            logger.debug("Intentando insertar impuesto: {}", impuesto);

            // Verificar si ya existe un impuesto con la misma descripción
            Impuesto existeDescripcion = impuestoRepository.findByImpDescripcion(impuesto.getImpDescripcion());
            if (existeDescripcion != null) {
                logger.debug("Ya existe un impuesto con la descripción: {}", impuesto.getImpDescripcion());
                return new Impuesto.MensajeRespuesta(409L, "Ya existe un impuesto con la misma descripción.", null);
            }

            // Al ser autoincremental, no necesitamos validar el impCod existente
            logger.debug("Impuesto no existe, procediendo a guardar");
            Impuesto nuevoImpuesto = impuestoRepository.save(impuesto);
            logger.debug("Impuesto guardado exitosamente: {}", nuevoImpuesto);

            return new Impuesto.MensajeRespuesta(200L, "Impuesto creado exitosamente.", List.of(nuevoImpuesto));
        } catch (Exception e) {
            logger.error("Error al insertar el impuesto: ", e);
            return new Impuesto.MensajeRespuesta(500L, "Error al insertar el impuesto: " + e.getMessage(), null);
        }
    }

    public Impuesto.MensajeRespuesta updateImpuesto(Impuesto impuesto) {
        try {
            // Validar si el impuesto existe
            if (impuesto.getImpCod() == null) {
                return new Impuesto.MensajeRespuesta(400L, "El código de impuesto no puede ser nulo.", null);
            }

            if (!impuestoRepository.existsByImpCod(impuesto.getImpCod())) {
                return new Impuesto.MensajeRespuesta(404L, "Impuesto no encontrado con el código especificado.", null);
            }

            // Verificar si ya existe un impuesto con la misma descripción pero diferente código
            Impuesto existeDescripcion = impuestoRepository.findByImpDescripcion(impuesto.getImpDescripcion());
            if (existeDescripcion != null && !existeDescripcion.getImpCod().equals(impuesto.getImpCod())) {
                logger.debug("Ya existe un impuesto con la descripción: {}", impuesto.getImpDescripcion());
                return new Impuesto.MensajeRespuesta(409L, "Ya existe un impuesto con la misma descripción.", null);
            }

            // Guardar los cambios
            Impuesto impuestoActualizado = impuestoRepository.save(impuesto);
            return new Impuesto.MensajeRespuesta(200L, "Impuesto actualizado exitosamente.", List.of(impuestoActualizado));

        } catch (Exception e) {
            logger.error("Error al actualizar el impuesto: ", e);
            return new Impuesto.MensajeRespuesta(500L, "Error al actualizar el impuesto: " + e.getMessage(), null);
        }
    }

    public Impuesto.MensajeRespuesta deleteImpuesto(Long impCod) {
        try {
            // Verificar si el impuesto existe
            if (!impuestoRepository.existsByImpCod(impCod)) {
                return new Impuesto.MensajeRespuesta(404L, "No se encontró el impuesto con el código: " + impCod, null);
            }

            try {
                // Intentar eliminar, si falla por restricciones de clave foránea, capturar la excepción
                impuestoRepository.deleteById(impCod);
                return new Impuesto.MensajeRespuesta(200L, "Impuesto eliminado exitosamente.", null);
            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                // Capturar error de clave foránea
                return new Impuesto.MensajeRespuesta(409L, "No se puede eliminar el impuesto ya que está siendo utilizado en otros registros.", null);
            }

        } catch (Exception e) {
            logger.error("Error inesperado al eliminar el impuesto: ", e);
            return new Impuesto.MensajeRespuesta(500L, "Error inesperado al eliminar el impuesto: " + e.getMessage(), null);
        }
    }
}