package py.com.nsa.api.commons.components.ref.parada.controller;


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.parada.model.Parada;
import py.com.nsa.api.commons.components.ref.parada.service.ParadaService;

import java.util.Map;

@RestController
@RequestMapping("parada")
public class ParadaController {

    @Autowired
    private ParadaService service;

    private static final Logger logger = LoggerFactory.getLogger(ParadaController.class);

    @GetMapping("/lista")
    public ResponseEntity<Parada.MensajeRespuesta> getParadaList() {
        try {
            Parada.MensajeRespuesta respuesta = service.getAllParadas();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener paradas: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Parada.MensajeRespuesta(500L, "Error al obtener paradas: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Parada.MensajeRespuesta> insertParada(@Valid @RequestBody Parada parada) {
        try {
            Parada.MensajeRespuesta respuesta = service.insert(parada);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar parada: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Parada.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Parada.MensajeRespuesta> updateParada(@Valid @RequestBody Parada parada) {
        try {
            Parada.MensajeRespuesta respuesta = service.update(parada);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar parada: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Parada.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Parada.MensajeRespuesta> deleteParada(@RequestParam("paraCod") Long paraCod) {
        try {
            Parada.MensajeRespuesta respuesta = service.deleteParada(paraCod);

            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta); // 200 OK
            } else if (respuesta.getStatus() == 204L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta); // Cambiamos a 404 NOT FOUND
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta); // 500 Internal Server Error
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al eliminar la parada con ID {}: {} ===>", paraCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Parada.MensajeRespuesta(500L, "Error al eliminar la parada: " + e.getMessage(), null));
        }
    }


}

