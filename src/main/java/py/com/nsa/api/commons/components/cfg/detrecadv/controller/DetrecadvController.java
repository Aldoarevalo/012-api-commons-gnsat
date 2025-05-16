package py.com.nsa.api.commons.components.cfg.detrecadv.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.detrecadv.model.Detrecadv;
import py.com.nsa.api.commons.components.cfg.detrecadv.service.DetrecadvService;

@RestController
@RequestMapping("/detrecadv")
public class DetrecadvController {
    private static final Logger logger = LoggerFactory.getLogger(DetrecadvController.class);

    @Autowired
    private DetrecadvService detrecadvService;

    @GetMapping("/lista")
    public ResponseEntity<Detrecadv.MensajeRespuesta> getDetrecadvAll(
            @RequestParam(value = "wmsStorerkey", required = false) String wmsStorerkey,
            @RequestParam(value = "wmsExternreceiptkey", required = false) String wmsExternreceiptkey) {
        try {
            Detrecadv.MensajeRespuesta respuesta = detrecadvService.getDetrecadvAll(wmsStorerkey, wmsExternreceiptkey);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los detalles de recepción: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Detrecadv.MensajeRespuesta(500L, "Error al obtener detalles de recepción: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Detrecadv.MensajeRespuesta> insertarDetrecadv(
            @Valid @RequestBody Detrecadv detrecadv) {
        try {
            Detrecadv.MensajeRespuesta respuesta = detrecadvService.insertarDetrecadv(detrecadv);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el detalle de recepción: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Detrecadv.MensajeRespuesta(500L, "Error al insertar el detalle de recepción: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Detrecadv.MensajeRespuesta> updateDetrecadv(
            @Valid @RequestBody Detrecadv detrecadv) {
        try {
            Detrecadv.MensajeRespuesta respuesta = detrecadvService.updateDetrecadv(detrecadv);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el detalle de recepción con ID {}: {} ===>", detrecadv.getWmsId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Detrecadv.MensajeRespuesta(500L, "Error al actualizar el detalle de recepción: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Detrecadv.MensajeRespuesta> deleteDetrecadv(
            @RequestParam("wmsStorerkey") String wmsStorerkey,
            @RequestParam("wmsExternreceiptkey") String wmsExternreceiptkey,
            @RequestParam("wmsId") Long wmsId) {
        try {
            Detrecadv.MensajeRespuesta respuesta = detrecadvService.deleteDetrecadv(wmsStorerkey, wmsExternreceiptkey, wmsId);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el detalle de recepción: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Detrecadv.MensajeRespuesta(500L, "Error al eliminar el detalle de recepción: " + e.getMessage(), null));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<Detrecadv.MensajeRespuesta> getDetrecadvByStorerKeyExternReceiptKeyAndSku(
            @RequestParam("wmsStorerkey") String wmsStorerkey,
            @RequestParam("wmsExternreceiptkey") String wmsExternreceiptkey,
            @RequestParam("wmsSku") String wmsSku) {
        try {
            Detrecadv.MensajeRespuesta respuesta = detrecadvService.getDetrecadvByStorerKeyExternReceiptKeyAndSku(wmsStorerkey, wmsExternreceiptkey, wmsSku);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al buscar detalles de recepción: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Detrecadv.MensajeRespuesta(500L, "Error al buscar detalles de recepción: " + e.getMessage(), null));
        }
    }
}