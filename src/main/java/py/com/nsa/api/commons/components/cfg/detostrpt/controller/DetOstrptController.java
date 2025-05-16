package py.com.nsa.api.commons.components.cfg.detostrpt.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.detostrpt.model.DetOstrpt;
import py.com.nsa.api.commons.components.cfg.detostrpt.service.DetOstrptService;

@RestController
@RequestMapping("/detostrpt")
public class DetOstrptController {
    private static final Logger logger = LoggerFactory.getLogger(DetOstrptController.class);

    @Autowired
    private DetOstrptService detOstrptService;

    @GetMapping("/lista")
    public ResponseEntity<DetOstrpt.MensajeRespuesta> getDetOstrptAll(
            @RequestParam(value = "wmsStorerkey", required = false) String wmsStorerkey,
            @RequestParam(value = "wmsExternorderkey", required = false) String wmsExternorderkey) {
        try {
            DetOstrpt.MensajeRespuesta respuesta = detOstrptService.getDetOstrptAll(wmsStorerkey, wmsExternorderkey);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener los detalles de reporte de salida: {}", e.getMessage(), e);
            return new ResponseEntity<>(new DetOstrpt.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<DetOstrpt.MensajeRespuesta> insertarDetOstrpt(@Valid @RequestBody DetOstrpt detOstrpt) {
        try {
            DetOstrpt.MensajeRespuesta respuesta = detOstrptService.insertarDetOstrpt(detOstrpt);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al insertar el detalle de reporte de salida: {}", e.getMessage(), e);
            return new ResponseEntity<>(new DetOstrpt.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<DetOstrpt.MensajeRespuesta> updateDetOstrpt(@Valid @RequestBody DetOstrpt detOstrpt) {
        try {
            DetOstrpt.MensajeRespuesta respuesta = detOstrptService.updateDetOstrpt(detOstrpt);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al actualizar el detalle de reporte de salida: {}", e.getMessage(), e);
            return new ResponseEntity<>(new DetOstrpt.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DetOstrpt.MensajeRespuesta> deleteDetOstrpt(
            @RequestParam("wmsStorerkey") String wmsStorerkey,
            @RequestParam("wmsExternorderkey") String wmsExternorderkey,
            @RequestParam("wmsIddet") Long wmsIddet) {
        try {
            DetOstrpt.MensajeRespuesta respuesta = detOstrptService.deleteDetOstrpt(wmsStorerkey, wmsExternorderkey, wmsIddet);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al eliminar el detalle de reporte de salida: {}", e.getMessage(), e);
            return new ResponseEntity<>(new DetOstrpt.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<DetOstrpt.MensajeRespuesta> getDetOstrptByStorerKeyExternOrderKeyAndSku(
            @RequestParam("wmsStorerkey") String wmsStorerkey,
            @RequestParam("wmsExternorderkey") String wmsExternorderkey,
            @RequestParam("wmsSku") String wmsSku) {
        try {
            DetOstrpt.MensajeRespuesta respuesta = detOstrptService.getDetOstrptByStorerKeyExternOrderKeyAndSku(wmsStorerkey, wmsExternorderkey, wmsSku);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al buscar detalles de reporte de salida: {}", e.getMessage(), e);
            return new ResponseEntity<>(new DetOstrpt.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}