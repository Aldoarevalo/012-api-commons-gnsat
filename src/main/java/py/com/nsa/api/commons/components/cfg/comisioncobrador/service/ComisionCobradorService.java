package py.com.nsa.api.commons.components.cfg.comisioncobrador.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.comisioncobrador.modelo.ComisionCobrador;
import py.com.nsa.api.commons.components.cfg.comisioncobrador.repository.ComisionCobradorRepository;

import java.util.List;


@Service
public class ComisionCobradorService {

    private static final Logger logger = LoggerFactory.getLogger(ComisionCobradorService.class);
    @Autowired
    private ComisionCobradorRepository comisionCobradorRepository;

    public ComisionCobrador.MensajeRespuesta getComisionesCobrador() {
        try {
            List<ComisionCobrador> comisiones = comisionCobradorRepository.findAll();
            if (comisiones.isEmpty()) {
                return new ComisionCobrador.MensajeRespuesta(204L, "No se encontraron comisiones de cobrador.", null);
            }
            return new ComisionCobrador.MensajeRespuesta(200L, "Comisiones de cobrador obtenidas exitosamente.", comisiones);
        } catch (Exception e) {
            logger.error("<=== Error al obtener las comisiones de cobrador: {} ===>", e.getMessage(), e);
            return new ComisionCobrador.MensajeRespuesta(500L, "Error al obtener las comisiones de cobrador: " + e.getMessage(), null);
        }
    }

    public ComisionCobrador.MensajeRespuesta insertarComisionCobrador(ComisionCobrador comisionCobrador) {
        try {
            /*if (comisionCobradorRepository.existsByComDescripcionIgnoreCase(comisionCobrador.getComDescripcion())) {
                return new ComisionCobrador.MensajeRespuesta(204L, "Ya existe una comisión de cobrador con la misma descripción.", null);
            }*/
            Long nextComCodigo = comisionCobradorRepository.getNextComCodigo(
                    comisionCobrador.getComCodigo());
            comisionCobrador.setComCodigo(nextComCodigo);
            ComisionCobrador nuevaComision = comisionCobradorRepository.save(comisionCobrador);
            return new ComisionCobrador.MensajeRespuesta(200L, "Comisión de cobrador creada exitosamente.", List.of(nuevaComision));
        } catch (Exception e) {
            logger.error("<=== Error al insertar comisión de cobrador: {} ===>", e.getMessage(), e);
            return new ComisionCobrador.MensajeRespuesta(500L, "Error al insertar la comisión de cobrador: " + e.getMessage(), null);
        }
    }

    public ComisionCobrador.MensajeRespuesta updateComisionCobrador(ComisionCobrador comisionCobrador) {
        try {
            /*if (comisionCobradorRepository.existsByComDescripcionIgnoreCase(comisionCobrador.getComDescripcion())) {
                return new ComisionCobrador.MensajeRespuesta(204L, "Ya existe una comisión de cobrador con la misma descripción.", null);
            }*/
            if (comisionCobradorRepository.existsById(comisionCobrador.getComCodigo())) {
                ComisionCobrador updatedComisionCobrador = comisionCobradorRepository.save(comisionCobrador);
                return new ComisionCobrador.MensajeRespuesta(200L, "Comisión de Cobrador actualizado exitosamente.", List.of(updatedComisionCobrador));
            } else {
                return new ComisionCobrador.MensajeRespuesta(204L, "Comisión de Cobrador no encontrado.", null);
            }
        } catch (Exception e) {
            return new ComisionCobrador.MensajeRespuesta(500L, "Error al actualizar la Comisión de  cobrador: " + e.getMessage(), null);
        }
    }

    public ComisionCobrador.MensajeRespuesta deleteComisionCobrador(Long comCodigo) {
        try {
            if (comisionCobradorRepository.existsById(comCodigo)) {
                comisionCobradorRepository.deleteById(comCodigo);
                return new ComisionCobrador.MensajeRespuesta(200L, "Comisión de cobrador  eliminada exitosamente", null);
            } else {
                return new ComisionCobrador.MensajeRespuesta(204L, "Comisión de cobrador con código " + comCodigo + " no encontrada", null);
            }
        } catch (JpaSystemException e) {
            // Personalizamos el mensaje de error
            String mensaje = "No se puede eliminar la Comisión del Cobrador porque está referenciado por otros registros"; // Mensaje
            // personalizado
            return new ComisionCobrador.MensajeRespuesta(204L,
                    "Error al eliminar la Comisión del Cobrador: " + mensaje, null);
        } catch (Exception e) {
            logger.error("<=== Error al eliminar comisión de cobrador: {} ===>", e.getMessage(), e);
            return new ComisionCobrador.MensajeRespuesta(500L, "Error al eliminar la comisión de cobrador con código " + comCodigo + ": " + e.getMessage(), null);
        }
    }
}