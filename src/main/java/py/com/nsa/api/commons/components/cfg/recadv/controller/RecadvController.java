package py.com.nsa.api.commons.components.cfg.recadv.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.recadv.model.Recadv;
import py.com.nsa.api.commons.components.cfg.recadv.service.RecadvService;

import jakarta.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/recadv")
public class RecadvController {
    private static final Logger logger = LoggerFactory.getLogger(RecadvController.class);

    @Autowired
    private RecadvService recadvService;

    @GetMapping("/lista")
    public ResponseEntity<Recadv.MensajeRespuesta> getAllRecadv() {
        try {
            Recadv.MensajeRespuesta respuesta = recadvService.getAllRecadv();
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener todas las recepciones: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Recadv.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{wmsStorerkey}/{wmsExternreceiptkey}")
    public ResponseEntity<Recadv.MensajeRespuesta> getRecadvByStorerKeyAndExternReceiptKey(
            @PathVariable String wmsStorerkey,
            @PathVariable String wmsExternreceiptkey) {
        try {
            Recadv.MensajeRespuesta respuesta = recadvService.getRecadvByStorerKeyAndExternReceiptKey(wmsStorerkey, wmsExternreceiptkey);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener la recepción: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Recadv.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Recadv.MensajeRespuesta> insertRecadv(@Valid @RequestBody Recadv recadv) {
        try {
            Recadv.MensajeRespuesta respuesta = recadvService.insertRecadv(recadv);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al insertar la recepción: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Recadv.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Recadv.MensajeRespuesta> updateRecadv(@Valid @RequestBody Recadv recadv) {
        try {
            Recadv.MensajeRespuesta respuesta = recadvService.updateRecadv(recadv);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al actualizar la recepción: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Recadv.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{wmsStorerkey}/{wmsExternreceiptkey}")
    public ResponseEntity<Recadv.MensajeRespuesta> deleteRecadv(
            @PathVariable String wmsStorerkey,
            @PathVariable String wmsExternreceiptkey) {
        try {
            Recadv.MensajeRespuesta respuesta = recadvService.deleteRecadv(wmsStorerkey, wmsExternreceiptkey);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al eliminar la recepción: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Recadv.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/type/{wmsType}")
    public ResponseEntity<Recadv.MensajeRespuesta> getRecadvByType(@PathVariable String wmsType) {
        try {
            Recadv.MensajeRespuesta respuesta = recadvService.getRecadvByType(wmsType);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener recepciones por tipo: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Recadv.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<Recadv.MensajeRespuesta> getRecadvByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {
            Recadv.MensajeRespuesta respuesta = recadvService.getRecadvByDateRange(startDate, endDate);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener recepciones por rango de fechas: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Recadv.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/most-recent/{wmsStorerkey}")
    public ResponseEntity<Recadv.MensajeRespuesta> getMostRecentRecadvByStorerKey(@PathVariable String wmsStorerkey) {
        try {
            Recadv.MensajeRespuesta respuesta = recadvService.getMostRecentRecadvByStorerKey(wmsStorerkey);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener la recepción más reciente: {}", e.getMessage(), e);
            return new ResponseEntity<>(new Recadv.MensajeRespuesta(500L, "Error interno del servidor", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}