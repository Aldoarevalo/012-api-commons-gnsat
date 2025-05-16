package py.com.nsa.api.commons.components.cfg.recadv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.cfg.recadv.model.Recadv;
import py.com.nsa.api.commons.components.cfg.recadv.model.RecadvId;
import py.com.nsa.api.commons.components.cfg.recadv.repository.RecadvRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecadvService {

    @Autowired
    private RecadvRepository recadvRepository;

    public Recadv.MensajeRespuesta getAllRecadv() {
        try {
            List<Recadv> recadvs = recadvRepository.findAll();
            if (recadvs.isEmpty()) {
                return new Recadv.MensajeRespuesta(204L, "No se encontraron recepciones.", null);
            }
            return new Recadv.MensajeRespuesta(200L, "Recepciones obtenidas exitosamente.", recadvs);
        } catch (Exception e) {
            return new Recadv.MensajeRespuesta(500L, "Error al obtener las recepciones: " + e.getMessage(), null);
        }
    }

    public Recadv.MensajeRespuesta getRecadvByStorerKeyAndExternReceiptKey(String wmsStorerkey, String wmsExternreceiptkey) {
        try {
            RecadvId id = new RecadvId(wmsStorerkey, wmsExternreceiptkey);
            Optional<Recadv> recadv = recadvRepository.findById(id);
            if (recadv.isPresent()) {
                return new Recadv.MensajeRespuesta(200L, "Recepción obtenida exitosamente.", List.of(recadv.get()));
            } else {
                return new Recadv.MensajeRespuesta(204L, "No se encontró la recepción.", null);
            }
        } catch (Exception e) {
            return new Recadv.MensajeRespuesta(500L, "Error al obtener la recepción: " + e.getMessage(), null);
        }
    }

    @Transactional
    public Recadv.MensajeRespuesta insertRecadv(Recadv recadv) {
        try {
            Recadv savedRecadv = recadvRepository.save(recadv);
            return new Recadv.MensajeRespuesta(200L, "Recepción creada exitosamente.", List.of(savedRecadv));
        } catch (Exception e) {
            return new Recadv.MensajeRespuesta(500L, "Error al insertar la recepción: " + e.getMessage(), null);
        }
    }

    @Transactional
    public Recadv.MensajeRespuesta updateRecadv(Recadv recadv) {
        try {
            RecadvId id = new RecadvId(recadv.getWmsStorerkey(), recadv.getWmsExternreceiptkey());
            if (recadvRepository.existsById(id)) {
                Recadv updatedRecadv = recadvRepository.save(recadv);
                return new Recadv.MensajeRespuesta(200L, "Recepción actualizada exitosamente.", List.of(updatedRecadv));
            } else {
                return new Recadv.MensajeRespuesta(204L, "No se encontró la recepción para actualizar.", null);
            }
        } catch (Exception e) {
            return new Recadv.MensajeRespuesta(500L, "Error al actualizar la recepción: " + e.getMessage(), null);
        }
    }

    @Transactional
    public Recadv.MensajeRespuesta deleteRecadv(String wmsStorerkey, String wmsExternreceiptkey) {
        try {
            RecadvId id = new RecadvId(wmsStorerkey, wmsExternreceiptkey);
            if (recadvRepository.existsById(id)) {
                recadvRepository.deleteById(id);
                return new Recadv.MensajeRespuesta(200L, "Recepción eliminada exitosamente.", null);
            } else {
                return new Recadv.MensajeRespuesta(204L, "No se encontró la recepción para eliminar.", null);
            }
        } catch (Exception e) {
            return new Recadv.MensajeRespuesta(500L, "Error al eliminar la recepción: " + e.getMessage(), null);
        }
    }

    public Recadv.MensajeRespuesta getRecadvByType(String wmsType) {
        try {
            List<Recadv> recadvs = recadvRepository.findByWmsType(wmsType);
            if (recadvs.isEmpty()) {
                return new Recadv.MensajeRespuesta(204L, "No se encontraron recepciones del tipo especificado.", null);
            }
            return new Recadv.MensajeRespuesta(200L, "Recepciones obtenidas exitosamente.", recadvs);
        } catch (Exception e) {
            return new Recadv.MensajeRespuesta(500L, "Error al obtener las recepciones por tipo: " + e.getMessage(), null);
        }
    }

    public Recadv.MensajeRespuesta getRecadvByDateRange(Date startDate, Date endDate) {
        try {
            List<Recadv> recadvs = recadvRepository.findByExpectedReceiptDateBetween(startDate, endDate);
            if (recadvs.isEmpty()) {
                return new Recadv.MensajeRespuesta(204L, "No se encontraron recepciones en el rango de fechas especificado.", null);
            }
            return new Recadv.MensajeRespuesta(200L, "Recepciones obtenidas exitosamente.", recadvs);
        } catch (Exception e) {
            return new Recadv.MensajeRespuesta(500L, "Error al obtener las recepciones por rango de fechas: " + e.getMessage(), null);
        }
    }

    public Recadv.MensajeRespuesta getMostRecentRecadvByStorerKey(String wmsStorerkey) {
        try {
            List<Recadv> recadvs = recadvRepository.findMostRecentByWmsStorerkey(wmsStorerkey, PageRequest.of(0, 1));
            if (recadvs.isEmpty()) {
                return new Recadv.MensajeRespuesta(204L, "No se encontró una recepción reciente para el StorerKey especificado.", null);
            }
            return new Recadv.MensajeRespuesta(200L, "Recepción más reciente obtenida exitosamente.", recadvs);
        } catch (Exception e) {
            return new Recadv.MensajeRespuesta(500L, "Error al obtener la recepción más reciente: " + e.getMessage(), null);
        }
    }
}