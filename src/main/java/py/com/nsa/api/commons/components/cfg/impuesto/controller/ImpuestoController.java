package py.com.nsa.api.commons.components.cfg.impuesto.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.impuesto.model.Impuesto;
import py.com.nsa.api.commons.components.cfg.impuesto.service.ImpuestoService;

@RestController
@RequestMapping("/impuestos")
public class ImpuestoController {
    private static final Logger logger = LoggerFactory.getLogger(ImpuestoController.class);

    @Autowired
    private ImpuestoService impuestoService;

    @GetMapping("/lista")
    public ResponseEntity<Impuesto.MensajeRespuesta> getImpuestoAll() {
        try {
            Impuesto.MensajeRespuesta respuesta = impuestoService.getImpuestosAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los impuestos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Impuesto.MensajeRespuesta(500L, "Error al obtener los impuestos:" + e.getMessage(), null));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<Impuesto.MensajeRespuesta> getImpuestoByImpCod(@RequestParam("impCod") Long impCod) {
        try {
            Impuesto.MensajeRespuesta respuesta = impuestoService.getImpuestoByImpCod(impCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Impuesto.MensajeRespuesta(500L, "Error al buscar el impuesto: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Impuesto.MensajeRespuesta> insertImpuesto(@Valid @RequestBody Impuesto impuesto) {
        try {
            logger.debug("Recibiendo petición POST con impuesto: {}", impuesto);

            Impuesto.MensajeRespuesta respuesta = impuestoService.insertarImpuesto(impuesto);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (409L == respuesta.getStatus()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el impuesto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Impuesto.MensajeRespuesta(500L, "Error al insertar el impuesto: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Impuesto.MensajeRespuesta> updateImpuesto(@Valid @RequestBody Impuesto impuesto) {
        try {
            logger.info("Actualizando impuesto con impCod: {}", impuesto.getImpCod());
            Impuesto.MensajeRespuesta respuesta = impuestoService.updateImpuesto(impuesto);

            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else if (respuesta.getStatus() == 409L) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el impuesto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Impuesto.MensajeRespuesta(500L, "Error al actualizar el impuesto: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Impuesto.MensajeRespuesta> deleteImpuesto(@RequestParam("impCod") Long impCod) {
        try {
            Impuesto.MensajeRespuesta respuesta = impuestoService.deleteImpuesto(impCod);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else if (respuesta.getStatus() == 409L) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el impuesto: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Impuesto.MensajeRespuesta(500L, "Error al eliminar el impuesto: " + e.getMessage(), null));
        }
    }
}