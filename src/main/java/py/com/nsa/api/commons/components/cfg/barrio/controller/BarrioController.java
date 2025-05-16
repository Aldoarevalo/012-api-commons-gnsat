package py.com.nsa.api.commons.components.cfg.barrio.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.barrio.model.Barrio;
import py.com.nsa.api.commons.components.cfg.barrio.service.BarrioService;

@RestController
@RequestMapping("/barrio")
public class BarrioController {
    private static final Logger logger = LoggerFactory.getLogger(BarrioController.class);

    @Autowired
    private BarrioService barrioService;

    @GetMapping("/lista")
    public ResponseEntity<Barrio.MensajeRespuesta> getBarriosAll() {
        try {
            Barrio.MensajeRespuesta respuesta = barrioService.getBarriosAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }

        } catch (Exception e) {
            logger.error("<=== Error al obtener los barrios: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Barrio.MensajeRespuesta(500L, "Error al obtener los barrios: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Barrio.MensajeRespuesta> insertarBarrio(
            @Valid @RequestBody Barrio barrio) {
        try {
            Barrio.MensajeRespuesta respuesta = barrioService.insertarBarrio(barrio);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el barrio: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Barrio.MensajeRespuesta(500L, "Error al insertar el barrio: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Barrio.MensajeRespuesta> updateBarrio(
            @Valid @RequestBody Barrio barrio) {
        try {
            Barrio.MensajeRespuesta respuesta = barrioService.updateBarrio(barrio);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el barrio con ID {}: {} ===>", barrio.getBCod(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Barrio.MensajeRespuesta(500L, "Error al actualizar el barrio: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Barrio.MensajeRespuesta> deleteBarrio(
            @RequestParam("bCod") Long bCod) {
        try {
            Barrio.MensajeRespuesta respuesta = barrioService.deleteBarrio(bCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el barrio con código {}: {} ===>", bCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Barrio.MensajeRespuesta(500L, "Error al eliminar el barrio: " + e.getMessage(), null));
        }
    }

}
