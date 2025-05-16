package py.com.nsa.api.commons.components.cfg.muelles.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.muelles.model.WmsMuelle;
import py.com.nsa.api.commons.components.cfg.muelles.service.WmsMuelleService;

@RestController
@RequestMapping("/muelle")
public class WmsMuelleController {
    private static final Logger logger = LoggerFactory.getLogger(WmsMuelleController.class);

    @Autowired
    private WmsMuelleService wmsMuelleService;

    /**
     * Endpoint para obtener el muelle más cercano a las coordenadas especificadas.
     *
     * @param latitud La latitud de la ubicación
     * @param longitud La longitud de la ubicación
     * @return ResponseEntity con el muelle más cercano
     */
    @GetMapping("/nearest")
    public ResponseEntity<WmsMuelle.MensajeRespuesta> getNearestMuelle(
            @RequestParam Double latitud,
            @RequestParam Double longitud) {
        try {
            WmsMuelle.MensajeRespuesta respuesta = wmsMuelleService.getNearestMuelle(latitud, longitud);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener el muelle más cercano: {}", e.getMessage(), e);
            return new ResponseEntity<>(new WmsMuelle.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint para obtener todos los muelles ordenados por distancia desde las coordenadas especificadas.
     *
     * @param latitud La latitud de la ubicación
     * @param longitud La longitud de la ubicación
     * @return ResponseEntity con todos los muelles ordenados por distancia
     */
    @GetMapping("/by-distance")
    public ResponseEntity<WmsMuelle.MensajeRespuesta> getAllMuellesByDistance(
            @RequestParam Double latitud,
            @RequestParam Double longitud) {
        try {
            WmsMuelle.MensajeRespuesta respuesta = wmsMuelleService.getAllMuellesByDistance(latitud, longitud);
            return new ResponseEntity<>(respuesta, HttpStatus.valueOf(respuesta.getStatus().intValue()));
        } catch (Exception e) {
            logger.error("Error al obtener muelles por distancia: {}", e.getMessage(), e);
            return new ResponseEntity<>(new WmsMuelle.MensajeRespuesta(500L, "Error interno del servidor", null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}