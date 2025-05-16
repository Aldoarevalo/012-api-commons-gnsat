package py.com.nsa.api.commons.components.ref.tramo.controller;


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.tramo.model.Tramo;
import py.com.nsa.api.commons.components.ref.tramo.service.TramoService;

@RestController
@RequestMapping("tramo")
public class TramoController {

    @Autowired
    private TramoService service;

    private static final Logger logger = LoggerFactory.getLogger(TramoController.class);

    @GetMapping("/lista")
    public ResponseEntity<Tramo.MensajeRespuesta> getTramoList() {
        try {
            Tramo.MensajeRespuesta respuesta = service.getAllTramos();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener tramos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Tramo.MensajeRespuesta(500L, "Error al obtener tramos: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Tramo.MensajeRespuesta> insertTramo(@Valid @RequestBody Tramo tramo) {
        try {
            Tramo.MensajeRespuesta respuesta = service.insert(tramo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar tramo: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Tramo.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Tramo.MensajeRespuesta> updateTramo(@Valid @RequestBody Tramo tramo) {
        try {
            Tramo.MensajeRespuesta respuesta = service.update(tramo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar tramo: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Tramo.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Tramo.MensajeRespuesta> deleteTramo(@RequestParam("trCod") Long trCod) {
        try {
            Tramo.MensajeRespuesta respuesta = service.deleteTramo(trCod);

            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta); // 200 OK
            } else if (respuesta.getStatus() == 404L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta); // 404 NOT FOUND
            } else if (respuesta.getStatus() == 409L) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta); // 409 CONFLICT - Tramo referenciado
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta); // 500 ERROR
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al eliminar el tramo con ID {}: {} ===>", trCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Tramo.MensajeRespuesta(500L, "Error al eliminar el tramo: " + e.getMessage(), null));
        }
    }

}
