package py.com.nsa.api.commons.components.cfg.shipmentsDet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.cfg.shipmentsDet.model.ShipmentDet;
import py.com.nsa.api.commons.components.cfg.shipmentsDet.model.ShipmentDetId;
import py.com.nsa.api.commons.components.cfg.shipmentsDet.repository.ShipmentDetRepository;

import java.util.List;

@Service
public class ShipmentDetService {

    @Autowired
    private ShipmentDetRepository shipmentDetRepository;

    public ShipmentDet.MensajeRespuesta getShipmentDetAll(String tmsWhseid, String tmsStorerkey, String tmsExternorderkey) {
        try {
            List<ShipmentDet> shipmentDets;
            if (tmsWhseid == null || tmsStorerkey == null || tmsExternorderkey == null ||
                    tmsWhseid.isEmpty() || tmsStorerkey.isEmpty() || tmsExternorderkey.isEmpty()) {
                shipmentDets = shipmentDetRepository.findAll();
            } else {
                shipmentDets = shipmentDetRepository.getListaShipmentDet(tmsWhseid, tmsStorerkey, tmsExternorderkey);
            }
            if (shipmentDets.isEmpty()) {
                return new ShipmentDet.MensajeRespuesta(204L, "No se encontraron detalles de envío.", null);
            }
            return new ShipmentDet.MensajeRespuesta(200L, "Detalles de envío obtenidos exitosamente.", shipmentDets);
        } catch (Exception e) {
            return new ShipmentDet.MensajeRespuesta(500L, "Error al obtener los detalles de envío: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ShipmentDet.MensajeRespuesta insertShipmentDet(ShipmentDet shipmentDet) {
        try {
            ShipmentDet savedShipmentDet = shipmentDetRepository.save(shipmentDet);
            return new ShipmentDet.MensajeRespuesta(200L, "Detalle de envío creado exitosamente.", List.of(savedShipmentDet));
        } catch (Exception e) {
            return new ShipmentDet.MensajeRespuesta(500L, "Error al insertar el detalle de envío: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ShipmentDet.MensajeRespuesta updateShipmentDet(ShipmentDet shipmentDet) {
        try {
            ShipmentDetId id = new ShipmentDetId(
                    shipmentDet.getTmsWhseid(),
                    shipmentDet.getTmsStorerkey(),
                    shipmentDet.getTmsExternorderkey(),
                    shipmentDet.getTmsExternlineno()
            );
            if (!shipmentDetRepository.existsById(id)) {
                return new ShipmentDet.MensajeRespuesta(204L, "No se encontró el detalle de envío para actualizar.", null);
            }
            ShipmentDet updatedShipmentDet = shipmentDetRepository.save(shipmentDet);
            return new ShipmentDet.MensajeRespuesta(200L, "Detalle de envío actualizado exitosamente.", List.of(updatedShipmentDet));
        } catch (Exception e) {
            return new ShipmentDet.MensajeRespuesta(500L, "Error al actualizar el detalle de envío: " + e.getMessage(), null);
        }
    }

    @Transactional
    public ShipmentDet.MensajeRespuesta deleteShipmentDet(
            String tmsWhseid, String tmsStorerkey, String tmsExternorderkey, String tmsExternlineno) {
        try {
            ShipmentDetId id = new ShipmentDetId(tmsWhseid, tmsStorerkey, tmsExternorderkey, tmsExternlineno);
            if (shipmentDetRepository.existsById(id)) {
                shipmentDetRepository.deleteById(id);
                return new ShipmentDet.MensajeRespuesta(200L, "Detalle de envío eliminado exitosamente.", null);
            } else {
                return new ShipmentDet.MensajeRespuesta(204L, "No se encontró el detalle de envío para eliminar.", null);
            }
        } catch (Exception e) {
            return new ShipmentDet.MensajeRespuesta(500L, "Error al eliminar el detalle de envío: " + e.getMessage(), null);
        }
    }

    public ShipmentDet.MensajeRespuesta getByStorerkeyAndExternorderkey(String tmsStorerkey, String tmsExternorderkey) {
        try {
            List<ShipmentDet> shipmentDets = shipmentDetRepository.findByTmsStorerkeyAndTmsExternorderkey(
                    tmsStorerkey, tmsExternorderkey);
            if (shipmentDets.isEmpty()) {
                return new ShipmentDet.MensajeRespuesta(204L, "No se encontraron detalles de envío para los parámetros especificados.", null);
            }
            return new ShipmentDet.MensajeRespuesta(200L, "Detalles de envío obtenidos exitosamente.", shipmentDets);
        } catch (Exception e) {
            return new ShipmentDet.MensajeRespuesta(500L, "Error al obtener los detalles de envío: " + e.getMessage(), null);
        }
    }

    public ShipmentDet.MensajeRespuesta getBySku(String tmsSku) {
        try {
            List<ShipmentDet> shipmentDets = shipmentDetRepository.findByTmsSku(tmsSku);
            if (shipmentDets.isEmpty()) {
                return new ShipmentDet.MensajeRespuesta(204L, "No se encontraron detalles de envío para el SKU especificado.", null);
            }
            return new ShipmentDet.MensajeRespuesta(200L, "Detalles de envío obtenidos exitosamente.", shipmentDets);
        } catch (Exception e) {
            return new ShipmentDet.MensajeRespuesta(500L, "Error al obtener los detalles de envío por SKU: " + e.getMessage(), null);
        }
    }

    public ShipmentDet.MensajeRespuesta getByStorerkeyAndSku(String tmsStorerkey, String tmsSku) {
        try {
            List<ShipmentDet> shipmentDets = shipmentDetRepository.findByStorerkeyAndSku(tmsStorerkey, tmsSku);
            if (shipmentDets.isEmpty()) {
                return new ShipmentDet.MensajeRespuesta(204L,
                        "No se encontraron detalles de envío para el StorerKey y SKU especificados.", null);
            }
            return new ShipmentDet.MensajeRespuesta(200L, "Detalles de envío obtenidos exitosamente.", shipmentDets);
        } catch (Exception e) {
            return new ShipmentDet.MensajeRespuesta(500L,
                    "Error al obtener los detalles de envío por StorerKey y SKU: " + e.getMessage(), null);
        }
    }
}