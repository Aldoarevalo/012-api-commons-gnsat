package py.com.nsa.api.commons.components.cfg.ostrpt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.cfg.ostrpt.model.Ostrpt;
import py.com.nsa.api.commons.components.cfg.ostrpt.model.OstrptId;
import py.com.nsa.api.commons.components.cfg.ostrpt.repository.OstrptRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OstrptService {

    @Autowired
    private OstrptRepository ostrptRepository;

    public Ostrpt.MensajeRespuesta getOstrptAll(String wmsStorerkey, String wmsExternorderkey) {
        try {
            List<Ostrpt> ostrpts;
            if (wmsStorerkey == null || wmsStorerkey.isEmpty() || wmsExternorderkey == null || wmsExternorderkey.isEmpty()) {
                ostrpts = ostrptRepository.findAll();
            } else {
                ostrpts = ostrptRepository.getListaOstrpt(wmsStorerkey, wmsExternorderkey);
            }
            if (ostrpts.isEmpty()) {
                return new Ostrpt.MensajeRespuesta(200L, "No se encontraron reportes de salida.", null);
            }
            return new Ostrpt.MensajeRespuesta(200L, "Reportes de salida obtenidos exitosamente.", ostrpts);
        } catch (Exception e) {
            return new Ostrpt.MensajeRespuesta(500L, "Error al obtener los reportes de salida: " + e.getMessage(), null);
        }
    }

    @Transactional
    public Ostrpt.MensajeRespuesta insertarOstrpt(Ostrpt ostrpt) {
        try {
            Ostrpt nuevoOstrpt = ostrptRepository.save(ostrpt);
            return new Ostrpt.MensajeRespuesta(200L, "Reporte de salida creado exitosamente.", List.of(nuevoOstrpt));
        } catch (Exception e) {
            return new Ostrpt.MensajeRespuesta(500L, "Error al insertar el reporte de salida: " + e.getMessage(), null);
        }
    }

    public Ostrpt.MensajeRespuesta updateOstrpt(Ostrpt ostrpt) {
        try {
            OstrptId id = new OstrptId(ostrpt.getWmsStorerkey(), ostrpt.getWmsExternorderkey());
            if (!ostrptRepository.existsById(id)) {
                return new Ostrpt.MensajeRespuesta(204L, "Reporte de salida no encontrado.", null);
            }
            Ostrpt updatedOstrpt = ostrptRepository.save(ostrpt);
            return new Ostrpt.MensajeRespuesta(200L, "Reporte de salida actualizado exitosamente.", List.of(updatedOstrpt));
        } catch (Exception e) {
            return new Ostrpt.MensajeRespuesta(500L, "Error al actualizar el reporte de salida: " + e.getMessage(), null);
        }
    }

    public Ostrpt.MensajeRespuesta deleteOstrpt(String wmsStorerkey, String wmsExternorderkey) {
        try {
            OstrptId id = new OstrptId(wmsStorerkey, wmsExternorderkey);
            if (ostrptRepository.existsById(id)) {
                ostrptRepository.deleteById(id);
                return new Ostrpt.MensajeRespuesta(200L, "Reporte de salida eliminado exitosamente", null);
            } else {
                return new Ostrpt.MensajeRespuesta(204L, "Reporte de salida no encontrado", null);
            }
        } catch (Exception e) {
            return new Ostrpt.MensajeRespuesta(500L, "Error al eliminar el reporte de salida: " + e.getMessage(), null);
        }
    }

    public Ostrpt.MensajeRespuesta getOstrptByType(String wmsType) {
        try {
            List<Ostrpt> ostrpts = ostrptRepository.findByWmsType(wmsType);
            if (ostrpts.isEmpty()) {
                return new Ostrpt.MensajeRespuesta(204L, "No se encontraron reportes de salida del tipo especificado.", null);
            }
            return new Ostrpt.MensajeRespuesta(200L, "Reportes de salida obtenidos exitosamente.", ostrpts);
        } catch (Exception e) {
            return new Ostrpt.MensajeRespuesta(500L, "Error al obtener los reportes de salida por tipo: " + e.getMessage(), null);
        }
    }

    public Ostrpt.MensajeRespuesta getOstrptByDateRange(Date startDate, Date endDate) {
        try {
            List<Ostrpt> ostrpts = ostrptRepository.findByDateRange(startDate, endDate);
            if (ostrpts.isEmpty()) {
                return new Ostrpt.MensajeRespuesta(204L, "No se encontraron reportes de salida en el rango de fechas especificado.", null);
            }
            return new Ostrpt.MensajeRespuesta(200L, "Reportes de salida obtenidos exitosamente.", ostrpts);
        } catch (Exception e) {
            return new Ostrpt.MensajeRespuesta(500L, "Error al obtener los reportes de salida por rango de fechas: " + e.getMessage(), null);
        }
    }

    public Ostrpt.MensajeRespuesta getMostRecentOstrptByStorerKey(String wmsStorerkey) {
        try {
            List<Ostrpt> ostrpts = ostrptRepository.findMostRecentByWmsStorerkey(wmsStorerkey, PageRequest.of(0, 1));
            if (ostrpts.isEmpty()) {
                return new Ostrpt.MensajeRespuesta(204L, "No se encontró un reporte de salida reciente para el StorerKey especificado.", null);
            }
            return new Ostrpt.MensajeRespuesta(200L, "Reporte de salida más reciente obtenido exitosamente.", ostrpts);
        } catch (Exception e) {
            return new Ostrpt.MensajeRespuesta(500L, "Error al obtener el reporte de salida más reciente: " + e.getMessage(), null);
        }
    }
}