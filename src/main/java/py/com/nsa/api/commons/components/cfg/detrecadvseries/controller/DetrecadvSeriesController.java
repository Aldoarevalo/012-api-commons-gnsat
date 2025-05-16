package py.com.nsa.api.commons.components.cfg.detrecadvseries.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.detrecadvseries.model.DetrecadvSeries;
import py.com.nsa.api.commons.components.cfg.detrecadvseries.service.DetrecadvSeriesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/detrecadv-series")
public class DetrecadvSeriesController {
    private static final Logger logger = LoggerFactory.getLogger(DetrecadvSeriesController.class);

    @Autowired
    private DetrecadvSeriesService detrecadvSeriesService;

    @GetMapping("/lista")
    public ResponseEntity<DetrecadvSeries.MensajeRespuesta> getAllDetrecadvSeries() {
        try {
            DetrecadvSeries.MensajeRespuesta respuesta = detrecadvSeriesService.getAllDetrecadvSeries();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.valueOf(respuesta.getStatus().intValue())).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener todas las series: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DetrecadvSeries.MensajeRespuesta(500L, "Error al obtener las series: " + e.getMessage(), null));
        }
    }

    @GetMapping("/detrecadv")
    public ResponseEntity<DetrecadvSeries.MensajeRespuesta> getSeriesByDetrecadvId(
            @RequestParam("wmsStorerkey") String wmsStorerkey,
            @RequestParam("wmsExternreceiptkey") String wmsExternreceiptkey,
            @RequestParam("wmsId") Long wmsId) {
        try {
            DetrecadvSeries.MensajeRespuesta respuesta = detrecadvSeriesService.getSeriesByDetrecadvId(wmsStorerkey, wmsExternreceiptkey, wmsId);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.valueOf(respuesta.getStatus().intValue())).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener series por detalle de recepción: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DetrecadvSeries.MensajeRespuesta(500L, "Error al obtener las series: " + e.getMessage(), null));
        }
    }

    @GetMapping("/lpn/{wmsLpn}")
    public ResponseEntity<DetrecadvSeries.MensajeRespuesta> getSeriesByLpn(@PathVariable String wmsLpn) {
        try {
            DetrecadvSeries.MensajeRespuesta respuesta = detrecadvSeriesService.getSeriesByLpn(wmsLpn);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.valueOf(respuesta.getStatus().intValue())).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener series por LPN: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DetrecadvSeries.MensajeRespuesta(500L, "Error al obtener las series por LPN: " + e.getMessage(), null));
        }
    }

    @GetMapping("/serial/{wmsSerialNumber}")
    public ResponseEntity<DetrecadvSeries.MensajeRespuesta> getSeriesBySerialNumber(@PathVariable String wmsSerialNumber) {
        try {
            DetrecadvSeries.MensajeRespuesta respuesta = detrecadvSeriesService.getSeriesBySerialNumber(wmsSerialNumber);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.valueOf(respuesta.getStatus().intValue())).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener series por número de serie: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DetrecadvSeries.MensajeRespuesta(500L, "Error al obtener las series por número de serie: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<DetrecadvSeries.MensajeRespuesta> insertDetrecadvSeries(
            @Valid @RequestBody DetrecadvSeries detrecadvSeries) {
        try {
            DetrecadvSeries.MensajeRespuesta respuesta = detrecadvSeriesService.insertDetrecadvSeries(detrecadvSeries);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar serie: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DetrecadvSeries.MensajeRespuesta(500L, "Error al insertar la serie: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<DetrecadvSeries.MensajeRespuesta> updateDetrecadvSeries(
            @Valid @RequestBody DetrecadvSeries detrecadvSeries) {
        try {
            DetrecadvSeries.MensajeRespuesta respuesta = detrecadvSeriesService.updateDetrecadvSeries(detrecadvSeries);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.valueOf(respuesta.getStatus().intValue())).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar serie: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DetrecadvSeries.MensajeRespuesta(500L, "Error al actualizar la serie: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DetrecadvSeries.MensajeRespuesta> deleteDetrecadvSeries(
            @RequestParam("wmsStorerkey") String wmsStorerkey,
            @RequestParam("wmsExternreceiptkey") String wmsExternreceiptkey,
            @RequestParam("wmsId") Long wmsId,
            @RequestParam("wmsIdseries") Long wmsIdseries) {
        try {
            DetrecadvSeries.MensajeRespuesta respuesta = detrecadvSeriesService.deleteDetrecadvSeries(wmsStorerkey, wmsExternreceiptkey, wmsId, wmsIdseries);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.valueOf(respuesta.getStatus().intValue())).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar serie: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DetrecadvSeries.MensajeRespuesta(500L, "Error al eliminar la serie: " + e.getMessage(), null));
        }
    }
}