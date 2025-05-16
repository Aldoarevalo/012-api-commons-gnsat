package py.com.nsa.api.commons.components.cfg.ostrpt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.ostrpt.model.Ostrpt;
import py.com.nsa.api.commons.components.cfg.ostrpt.service.OstrptService;

import jakarta.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/ostrpt")
public class OstrptController {
    private static final Logger logger = LoggerFactory.getLogger(OstrptController.class);

    @Autowired
    private OstrptService ostrptService;

    @GetMapping("/lista")
    public ResponseEntity<Ostrpt.MensajeRespuesta> getOstrptAll(
            @RequestParam(value = "wmsStorerkey", required = false) String wmsStorerkey,
            @RequestParam(value = "wmsExternorderkey", required = false) String wmsExternorderkey) {
        try {
            Ostrpt.MensajeRespuesta respuesta = ostrptService.getOstrptAll(wmsStorerkey, wmsExternorderkey);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener todos los reportes de salida: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Ostrpt.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Ostrpt.MensajeRespuesta> insertarOstrpt(@Valid @RequestBody Ostrpt ostrpt) {
        try {
            Ostrpt.MensajeRespuesta respuesta = ostrptService.insertarOstrpt(ostrpt);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al insertar el reporte de salida: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Ostrpt.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Ostrpt.MensajeRespuesta> updateOstrpt(@Valid @RequestBody Ostrpt ostrpt) {
        try {
            Ostrpt.MensajeRespuesta respuesta = ostrptService.updateOstrpt(ostrpt);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al actualizar el reporte de salida: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Ostrpt.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Ostrpt.MensajeRespuesta> deleteOstrpt(
            @RequestParam("wmsStorerkey") String wmsStorerkey,
            @RequestParam("wmsExternorderkey") String wmsExternorderkey) {
        try {
            Ostrpt.MensajeRespuesta respuesta = ostrptService.deleteOstrpt(wmsStorerkey, wmsExternorderkey);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al eliminar el reporte de salida: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Ostrpt.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/type/{wmsType}")
    public ResponseEntity<Ostrpt.MensajeRespuesta> getOstrptByType(@PathVariable String wmsType) {
        try {
            Ostrpt.MensajeRespuesta respuesta = ostrptService.getOstrptByType(wmsType);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener reportes de salida por tipo: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Ostrpt.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<Ostrpt.MensajeRespuesta> getOstrptByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {
            Ostrpt.MensajeRespuesta respuesta = ostrptService.getOstrptByDateRange(startDate, endDate);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener reportes de salida por rango de fechas: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Ostrpt.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/most-recent/{wmsStorerkey}")
    public ResponseEntity<Ostrpt.MensajeRespuesta> getMostRecentOstrptByStorerKey(@PathVariable String wmsStorerkey) {
        try {
            Ostrpt.MensajeRespuesta respuesta = ostrptService.getMostRecentOstrptByStorerKey(wmsStorerkey);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener el reporte de salida más reciente: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Ostrpt.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}