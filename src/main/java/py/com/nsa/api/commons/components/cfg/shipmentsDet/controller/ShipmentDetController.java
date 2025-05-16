package py.com.nsa.api.commons.components.cfg.shipmentsDet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.shipmentsDet.model.ShipmentDet;
import py.com.nsa.api.commons.components.cfg.shipmentsDet.service.ShipmentDetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/shipment-det")
public class ShipmentDetController {
    private static final Logger logger = LoggerFactory.getLogger(ShipmentDetController.class);

    @Autowired
    private ShipmentDetService shipmentDetService;

    @GetMapping("/lista")
    public ResponseEntity<ShipmentDet.MensajeRespuesta> getShipmentDetAll(
            @RequestParam(required = false) String tmsWhseid,
            @RequestParam(required = false) String tmsStorerkey,
            @RequestParam(required = false) String tmsExternorderkey) {
        try {
            ShipmentDet.MensajeRespuesta respuesta = shipmentDetService.getShipmentDetAll(tmsWhseid, tmsStorerkey, tmsExternorderkey);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener los detalles de envío: {}", e.getMessage(), e);
            return new ResponseEntity<>(new ShipmentDet.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<ShipmentDet.MensajeRespuesta> insertShipmentDet(@Valid @RequestBody ShipmentDet shipmentDet) {
        try {
            ShipmentDet.MensajeRespuesta respuesta = shipmentDetService.insertShipmentDet(shipmentDet);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al insertar el detalle de envío: {}", e.getMessage(), e);
            return new ResponseEntity<>(new ShipmentDet.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ShipmentDet.MensajeRespuesta> updateShipmentDet(@Valid @RequestBody ShipmentDet shipmentDet) {
        try {
            ShipmentDet.MensajeRespuesta respuesta = shipmentDetService.updateShipmentDet(shipmentDet);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al actualizar el detalle de envío: {}", e.getMessage(), e);
            return new ResponseEntity<>(new ShipmentDet.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ShipmentDet.MensajeRespuesta> deleteShipmentDet(
            @RequestParam String tmsWhseid,
            @RequestParam String tmsStorerkey,
            @RequestParam String tmsExternorderkey,
            @RequestParam String tmsExternlineno) {
        try {
            ShipmentDet.MensajeRespuesta respuesta = shipmentDetService.deleteShipmentDet(
                    tmsWhseid, tmsStorerkey, tmsExternorderkey, tmsExternlineno);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al eliminar el detalle de envío: {}", e.getMessage(), e);
            return new ResponseEntity<>(new ShipmentDet.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-storer-order")
    public ResponseEntity<ShipmentDet.MensajeRespuesta> getByStorerkeyAndExternorderkey(
            @RequestParam String tmsStorerkey,
            @RequestParam String tmsExternorderkey) {
        try {
            ShipmentDet.MensajeRespuesta respuesta = shipmentDetService.getByStorerkeyAndExternorderkey(
                    tmsStorerkey, tmsExternorderkey);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener detalles de envío por StorerKey y ExternOrderKey: {}", e.getMessage(), e);
            return new ResponseEntity<>(new ShipmentDet.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-sku/{tmsSku}")
    public ResponseEntity<ShipmentDet.MensajeRespuesta> getBySku(@PathVariable String tmsSku) {
        try {
            ShipmentDet.MensajeRespuesta respuesta = shipmentDetService.getBySku(tmsSku);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener detalles de envío por SKU: {}", e.getMessage(), e);
            return new ResponseEntity<>(new ShipmentDet.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-storer-sku")
    public ResponseEntity<ShipmentDet.MensajeRespuesta> getByStorerkeyAndSku(
            @RequestParam String tmsStorerkey,
            @RequestParam String tmsSku) {
        try {
            ShipmentDet.MensajeRespuesta respuesta = shipmentDetService.getByStorerkeyAndSku(tmsStorerkey, tmsSku);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener detalles de envío por StorerKey y SKU: {}", e.getMessage(), e);
            return new ResponseEntity<>(new ShipmentDet.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}