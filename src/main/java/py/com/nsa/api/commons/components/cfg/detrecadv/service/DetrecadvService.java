package py.com.nsa.api.commons.components.cfg.detrecadv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.detrecadv.model.Detrecadv;
import py.com.nsa.api.commons.components.cfg.detrecadv.model.DetrecadvId;
import py.com.nsa.api.commons.components.cfg.detrecadv.repository.DetrecadvRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class DetrecadvService {

    @Autowired
    private DetrecadvRepository detrecadvRepository;

    public Detrecadv.MensajeRespuesta getDetrecadvAll(String wmsStorerkey, String wmsExternreceiptkey) {
        try {
            List<Detrecadv> detrecadvs;
            if (wmsStorerkey == null || wmsStorerkey.isEmpty() || wmsExternreceiptkey == null || wmsExternreceiptkey.isEmpty()) {
                detrecadvs = detrecadvRepository.findAll();
            } else {
                detrecadvs = detrecadvRepository.getListaDetrecadv(wmsStorerkey, wmsExternreceiptkey);
            }
            if (detrecadvs.isEmpty()) {
                return new Detrecadv.MensajeRespuesta(200L, "No se encontraron detalles de recepción.", null);
            }
            return new Detrecadv.MensajeRespuesta(200L, "Detalles de recepción obtenidos exitosamente.", detrecadvs);
        } catch (Exception e) {
            System.err.println("Error al obtener los detalles de recepción: " + e.getMessage());
            e.printStackTrace();
            return new Detrecadv.MensajeRespuesta(500L, "Error al obtener los detalles de recepción: " + e.getMessage(), null);
        }
    }

    @Transactional
    public Detrecadv.MensajeRespuesta insertarDetrecadv(Detrecadv detrecadv) {
        try {
            // Obtener el siguiente wmsId
            Long nextWmsId = detrecadvRepository.getNextWmsId(detrecadv.getWmsStorerkey(), detrecadv.getWmsExternreceiptkey());
            detrecadv.setWmsId(nextWmsId);

            Detrecadv nuevoDetrecadv = detrecadvRepository.save(detrecadv);
            return new Detrecadv.MensajeRespuesta(200L, "Detalle de recepción creado exitosamente.", List.of(nuevoDetrecadv));
        } catch (Exception e) {
            return new Detrecadv.MensajeRespuesta(500L, "Error al insertar el detalle de recepción: " + e.getMessage(), null);
        }
    }


    public Detrecadv.MensajeRespuesta updateDetrecadv(Detrecadv detrecadv) {
        try {
            DetrecadvId id = new DetrecadvId(detrecadv.getWmsStorerkey(), detrecadv.getWmsExternreceiptkey(), detrecadv.getWmsId());
            if (!detrecadvRepository.existsById(id)) {
                return new Detrecadv.MensajeRespuesta(204L, "Detalle de recepción no encontrado.", null);
            }
            Detrecadv updatedDetrecadv = detrecadvRepository.save(detrecadv);
            return new Detrecadv.MensajeRespuesta(200L, "Detalle de recepción actualizado exitosamente.", List.of(updatedDetrecadv));
        } catch (Exception e) {
            return new Detrecadv.MensajeRespuesta(500L, "Error al actualizar el detalle de recepción: " + e.getMessage(), null);
        }
    }

    public Detrecadv.MensajeRespuesta deleteDetrecadv(String wmsStorerkey, String wmsExternreceiptkey, Long wmsId) {
        try {
            DetrecadvId id = new DetrecadvId(wmsStorerkey, wmsExternreceiptkey, wmsId);
            if (detrecadvRepository.existsById(id)) {
                detrecadvRepository.deleteById(id);
                return new Detrecadv.MensajeRespuesta(200L, "Detalle de recepción eliminado exitosamente", null);
            } else {
                return new Detrecadv.MensajeRespuesta(204L, "Detalle de recepción no encontrado", null);
            }
        } catch (JpaSystemException e) {
            String mensaje = "No se puede eliminar el detalle de recepción porque está referenciado por otros registros";
            return new Detrecadv.MensajeRespuesta(204L, "Error al eliminar el detalle de recepción: " + mensaje, null);
        } catch (Exception e) {
            return new Detrecadv.MensajeRespuesta(500L, "Error al eliminar el detalle de recepción: " + e.getMessage(), null);
        }
    }

    public Detrecadv.MensajeRespuesta getDetrecadvByStorerKeyExternReceiptKeyAndSku(String wmsStorerkey, String wmsExternreceiptkey, String wmsSku) {
        try {
            List<Detrecadv> detrecadvs = detrecadvRepository.findByWmsStorerkeyAndWmsExternreceiptkeyAndWmsSku(wmsStorerkey, wmsExternreceiptkey, wmsSku);
            if (detrecadvs.isEmpty()) {
                return new Detrecadv.MensajeRespuesta(204L, "No se encontraron detalles de recepción para los parámetros especificados.", null);
            }
            return new Detrecadv.MensajeRespuesta(200L, "Detalles de recepción obtenidos exitosamente.", detrecadvs);
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el Registro porque está referenciado por otros registros.";
            return new Detrecadv.MensajeRespuesta(204L, "Error al eliminar el Registro: " + mensaje, null);
        } catch (Exception e) {
            return new Detrecadv.MensajeRespuesta(500L, "Error al obtener los detalles de recepción: " + e.getMessage(), null);
        }
    }
}