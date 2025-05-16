package py.com.nsa.api.commons.components.ref.profesion.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.profesion.model.Profesion;
import py.com.nsa.api.commons.components.ref.profesion.service.ProfesionService;

@RestController
@RequestMapping("/profesion")
public class ProfesionController {

    private static final Logger logger = LoggerFactory.getLogger(ProfesionController.class);

    @Autowired
    private ProfesionService profesionservice;

    @GetMapping("/lista")
    public ResponseEntity<Profesion.MensajeRespuesta> getProfesionAll(
            @RequestParam(value = "keyword", required = false) String keyword) {
        try {
            Profesion.MensajeRespuesta respuesta = profesionservice.getProfesionesAll(keyword);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener profesiones: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Profesion.MensajeRespuesta(500L, "Error al obtener profesiones: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Profesion.MensajeRespuesta> insertarProfesion(@Valid @RequestBody Profesion profesion) {
        try {
            Profesion.MensajeRespuesta respuesta = profesionservice.insertarProfesion(profesion);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar la profesión: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Profesion.MensajeRespuesta(500L, "Error al insertar la profesión: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Profesion.MensajeRespuesta> updateProfesion(@Valid @RequestBody Profesion profesion) {
        try {
            Profesion.MensajeRespuesta respuesta = profesionservice.updateProfesion(profesion);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar la profesión con ID {}: {} ===>", profesion.getProfCodigo(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Profesion.MensajeRespuesta(500L, "Error al actualizar la profesión: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Profesion.MensajeRespuesta> deleteProfesion(@RequestParam("profCodigo") Long profCodigo) {
        try {
            Profesion.MensajeRespuesta respuesta = profesionservice.deleteProfesion(profCodigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al eliminar la profesión con ID {}: {} ===>", profCodigo, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Profesion.MensajeRespuesta(500L, "Error al eliminar la profesión: " + e.getMessage(), null));
        }
    }
}
