package py.com.nsa.api.commons.components.cfg.shipments.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.shipments.model.Shipment;
import py.com.nsa.api.commons.components.cfg.shipments.model.ShipmentWithDetailsDTO;
import py.com.nsa.api.commons.components.cfg.shipments.service.ShipmentService;

import jakarta.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {
    private static final Logger logger = LoggerFactory.getLogger(ShipmentController.class);

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/lista")
    public ResponseEntity<Shipment.MensajeRespuesta> getShipmentAll(
            @RequestParam(required = false) String tmsWhseid,
            @RequestParam(required = false) String tmsStorerkey,
            @RequestParam(required = false) String tmsExternorderkey) {
        try {
            Shipment.MensajeRespuesta respuesta = shipmentService.getShipmentAll(tmsWhseid, tmsStorerkey, tmsExternorderkey);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener los envíos: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Shipment.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Shipment.MensajeRespuesta> insertShipment(@Valid @RequestBody Shipment shipment) {
        try {
            Shipment.MensajeRespuesta respuesta = shipmentService.insertShipment(shipment);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al insertar el envío: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Shipment.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insertar-con-detalles")
    public ResponseEntity<Shipment.MensajeRespuesta> insertarEnvioConDetalles(
            @Valid @RequestBody ShipmentWithDetailsDTO envioConDetalles) {
        try {
            Shipment.MensajeRespuesta respuesta = shipmentService.insertarEnvioConDetalles(envioConDetalles);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al insertar el envío con detalles: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Shipment.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Shipment.MensajeRespuesta> updateShipment(@Valid @RequestBody Shipment shipment) {
        try {
            // Agregar logs para las fechas que ingresan en su formato crudo
            if (shipment.getTmsOrderDate() != null) {
                logger.info("Fecha de Orden cruda: valor={}, timestamp={}, clase={}",
                        shipment.getTmsOrderDate(),
                        shipment.getTmsOrderDate().getTime(),
                        shipment.getTmsOrderDate().getClass().getName());
            } else {
                logger.info("Fecha de Orden recibida: null");
            }

            if (shipment.getTmsInvoiceDate() != null) {
                logger.info("Fecha de Factura cruda: valor={}, timestamp={}, clase={}",
                        shipment.getTmsInvoiceDate(),
                        shipment.getTmsInvoiceDate().getTime(),
                        shipment.getTmsInvoiceDate().getClass().getName());
            } else {
                logger.info("Fecha de Factura recibida: null");
            }

            Shipment.MensajeRespuesta respuesta = shipmentService.updateShipment(shipment);

            logger.debug("Resultado de actualización: status={}, mensaje={}",
                    respuesta.getStatus(), respuesta.getMensaje());

            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al actualizar el envío: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Shipment.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Shipment.MensajeRespuesta> deleteShipment(
            @RequestParam String tmsWhseid,
            @RequestParam String tmsStorerkey,
            @RequestParam String tmsExternorderkey) {
        try {
            Shipment.MensajeRespuesta respuesta = shipmentService.deleteShipment(tmsWhseid, tmsStorerkey, tmsExternorderkey);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al eliminar el envío: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Shipment.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<Shipment.MensajeRespuesta> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {
            Shipment.MensajeRespuesta respuesta = shipmentService.getByDateRange(startDate, endDate);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener envíos por rango de fechas: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Shipment.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/invoice-date-range")
    public ResponseEntity<Shipment.MensajeRespuesta> getByInvoiceDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {
            Shipment.MensajeRespuesta respuesta = shipmentService.getByInvoiceDateRange(startDate, endDate);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener envíos por rango de fechas de factura: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Shipment.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/most-recent/{tmsStorerkey}")
    public ResponseEntity<Shipment.MensajeRespuesta> getMostRecentByStorerkey(@PathVariable String tmsStorerkey) {
        try {
            Shipment.MensajeRespuesta respuesta = shipmentService.getMostRecentByStorerkey(tmsStorerkey);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener el envío más reciente: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Shipment.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/procesado/{tmsProcesado}")
    public ResponseEntity<Shipment.MensajeRespuesta> getByProcesado(@PathVariable String tmsProcesado) {
        try {
            Shipment.MensajeRespuesta respuesta = shipmentService.getByProcesado(tmsProcesado);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener envíos por estado de procesamiento: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Shipment.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consignee/{tmsConsigneekey}")
    public ResponseEntity<Shipment.MensajeRespuesta> getByConsigneekey(@PathVariable String tmsConsigneekey) {
        try {
            Shipment.MensajeRespuesta respuesta = shipmentService.getByConsigneekey(tmsConsigneekey);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener envíos por destinatario: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Shipment.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}