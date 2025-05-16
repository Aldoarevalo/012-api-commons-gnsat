package py.com.nsa.api.commons.components.cfg.detostrptseries.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.detostrptseries.model.DetostrptSeries;
import py.com.nsa.api.commons.components.cfg.detostrptseries.service.DetostrptSeriesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/detostrpt-series")
public class DetostrptSeriesController {
    private static final Logger logger = LoggerFactory.getLogger(DetostrptSeriesController.class);

    @Autowired
    private DetostrptSeriesService detostrptSeriesService;

    @GetMapping("/lista")
    public ResponseEntity<DetostrptSeries.MensajeRespuesta> getAllDetostrptSeries() {
        try {
            DetostrptSeries.MensajeRespuesta respuesta = detostrptSeriesService.getAllDetostrptSeries();
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener todas las series: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                    new DetostrptSeries.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<DetostrptSeries.MensajeRespuesta> getSeriesByOrderAndDetail(
            @RequestParam String wmsStorerkey,
            @RequestParam String wmsExternorderkey,
            @RequestParam Long wmsIddet) {
        try {
            DetostrptSeries.MensajeRespuesta respuesta = detostrptSeriesService.getSeriesByOrderAndDetail(
                    wmsStorerkey, wmsExternorderkey, wmsIddet);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener series por pedido y detalle: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                    new DetostrptSeries.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<DetostrptSeries.MensajeRespuesta> insertDetostrptSeries(
            @Valid @RequestBody DetostrptSeries detostrptSeries) {
        try {
            DetostrptSeries.MensajeRespuesta respuesta = detostrptSeriesService.insertDetostrptSeries(detostrptSeries);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al insertar serie: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                    new DetostrptSeries.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PutMapping("/update")
    public ResponseEntity<DetostrptSeries.MensajeRespuesta> updateDetostrptSeries(
            @Valid @RequestBody DetostrptSeries detostrptSeries) {
        try {
            DetostrptSeries.MensajeRespuesta respuesta = detostrptSeriesService.updateDetostrptSeries(detostrptSeries);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al actualizar serie: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                    new DetostrptSeries.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DetostrptSeries.MensajeRespuesta> deleteDetostrptSeries(
            @RequestParam String wmsStorerkey,
            @RequestParam String wmsExternorderkey,
            @RequestParam Long wmsIddet,
            @RequestParam Long wmsIdserie) {
        try {
            DetostrptSeries.MensajeRespuesta respuesta = detostrptSeriesService.deleteDetostrptSeries(
                    wmsStorerkey, wmsExternorderkey, wmsIddet, wmsIdserie);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al eliminar serie: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                    new DetostrptSeries.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/lpn/{wmsLpn}")
    public ResponseEntity<DetostrptSeries.MensajeRespuesta> getSeriesByLpn(@PathVariable String wmsLpn) {
        try {
            DetostrptSeries.MensajeRespuesta respuesta = detostrptSeriesService.getSeriesByLpn(wmsLpn);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener series por LPN: {}", e.getMessage(), e);
            return new ResponseEntity<>(
                    new DetostrptSeries.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}