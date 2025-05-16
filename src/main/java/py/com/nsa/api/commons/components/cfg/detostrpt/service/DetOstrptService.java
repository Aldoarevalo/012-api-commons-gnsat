package py.com.nsa.api.commons.components.cfg.detostrpt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.detostrpt.model.DetOstrpt;
import py.com.nsa.api.commons.components.cfg.detostrpt.model.DetOstrptId;
import py.com.nsa.api.commons.components.cfg.detostrpt.repository.DetOstrptRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DetOstrptService {

    @Autowired
    private DetOstrptRepository detOstrptRepository;

    public DetOstrpt.MensajeRespuesta getDetOstrptAll(String wmsStorerkey, String wmsExternorderkey) {
        try {
            List<DetOstrpt> detOstrpts;
            if (wmsStorerkey == null || wmsStorerkey.isEmpty() || wmsExternorderkey == null || wmsExternorderkey.isEmpty()) {
                detOstrpts = detOstrptRepository.findAll();
            } else {
                detOstrpts = detOstrptRepository.getListaDetOstrpt(wmsStorerkey, wmsExternorderkey);
            }
            if (detOstrpts.isEmpty()) {
                return new DetOstrpt.MensajeRespuesta(200L, "No se encontraron detalles de reporte de salida.", null);
            }
            return new DetOstrpt.MensajeRespuesta(200L, "Detalles de reporte de salida obtenidos exitosamente.", detOstrpts);
        } catch (Exception e) {
            return new DetOstrpt.MensajeRespuesta(500L, "Error al obtener los detalles de reporte de salida: " + e.getMessage(), null);
        }
    }

    @Transactional
    public DetOstrpt.MensajeRespuesta insertarDetOstrpt(DetOstrpt detOstrpt) {
        try {
            // Obtener el siguiente wmsIddet
            Long nextWmsIddet = detOstrptRepository.getNextWmsIddet(detOstrpt.getWmsStorerkey(), detOstrpt.getWmsExternorderkey());
            detOstrpt.setWmsIddet(nextWmsIddet);

            DetOstrpt nuevoDetOstrpt = detOstrptRepository.save(detOstrpt);
            return new DetOstrpt.MensajeRespuesta(200L, "Detalle de reporte de salida creado exitosamente.", List.of(nuevoDetOstrpt));
        } catch (Exception e) {
            return new DetOstrpt.MensajeRespuesta(500L, "Error al insertar el detalle de reporte de salida: " + e.getMessage(), null);
        }
    }

    public DetOstrpt.MensajeRespuesta updateDetOstrpt(DetOstrpt detOstrpt) {
        try {
            DetOstrptId id = new DetOstrptId(detOstrpt.getWmsStorerkey(), detOstrpt.getWmsExternorderkey(), detOstrpt.getWmsIddet());
            if (!detOstrptRepository.existsById(id)) {
                return new DetOstrpt.MensajeRespuesta(204L, "Detalle de reporte de salida no encontrado.", null);
            }
            DetOstrpt updatedDetOstrpt = detOstrptRepository.save(detOstrpt);
            return new DetOstrpt.MensajeRespuesta(200L, "Detalle de reporte de salida actualizado exitosamente.", List.of(updatedDetOstrpt));
        } catch (Exception e) {
            return new DetOstrpt.MensajeRespuesta(500L, "Error al actualizar el detalle de reporte de salida: " + e.getMessage(), null);
        }
    }

    public DetOstrpt.MensajeRespuesta deleteDetOstrpt(String wmsStorerkey, String wmsExternorderkey, Long wmsIddet) {
        try {
            DetOstrptId id = new DetOstrptId(wmsStorerkey, wmsExternorderkey, wmsIddet);
            if (detOstrptRepository.existsById(id)) {
                detOstrptRepository.deleteById(id);
                return new DetOstrpt.MensajeRespuesta(200L, "Detalle de reporte de salida eliminado exitosamente", null);
            } else {
                return new DetOstrpt.MensajeRespuesta(204L, "Detalle de reporte de salida no encontrado", null);
            }
        } catch (JpaSystemException e) {
            String mensaje = "No se puede eliminar el detalle de reporte de salida porque está referenciado por otros registros";
            return new DetOstrpt.MensajeRespuesta(204L, "Error al eliminar el detalle de reporte de salida: " + mensaje, null);
        } catch (Exception e) {
            return new DetOstrpt.MensajeRespuesta(500L, "Error al eliminar el detalle de reporte de salida: " + e.getMessage(), null);
        }
    }

    public DetOstrpt.MensajeRespuesta getDetOstrptByStorerKeyExternOrderKeyAndSku(String wmsStorerkey, String wmsExternorderkey, String wmsSku) {
        try {
            List<DetOstrpt> detOstrpts = detOstrptRepository.findByWmsStorerkeyAndWmsExternorderkeyAndWmsSku(wmsStorerkey, wmsExternorderkey, wmsSku);
            if (detOstrpts.isEmpty()) {
                return new DetOstrpt.MensajeRespuesta(204L, "No se encontraron detalles de reporte de salida para los parámetros especificados.", null);
            }
            return new DetOstrpt.MensajeRespuesta(200L, "Detalles de reporte de salida obtenidos exitosamente.", detOstrpts);
        } catch (Exception e) {
            return new DetOstrpt.MensajeRespuesta(500L, "Error al obtener los detalles de reporte de salida: " + e.getMessage(), null);
        }
    }
}