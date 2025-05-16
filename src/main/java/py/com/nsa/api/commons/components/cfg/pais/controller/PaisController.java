package py.com.nsa.api.commons.components.cfg.pais.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;
import py.com.nsa.api.commons.components.cfg.pais.service.PaisService;

@RestController
@RequestMapping("/pais")
public class PaisController {
    private static final Logger logger = LoggerFactory.getLogger(PaisController.class);

    @Autowired
    private PaisService paisService;

    @GetMapping("/lista")
    public ResponseEntity<Pais.MensajeRespuesta> getPaisesAll() {
        try {
            Pais.MensajeRespuesta respuesta = paisService.getPaisesAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }

        } catch (Exception e) {
            logger.error("<=== Error al obtener los países: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Pais.MensajeRespuesta(500L, "Error al obtener países: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Pais.MensajeRespuesta> insertarPais(
            @Valid @RequestBody Pais pais) {
        try {
            Pais.MensajeRespuesta respuesta = paisService.insertarPais(pais);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el país: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Pais.MensajeRespuesta(500L, "Error al insertar el país: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Pais.MensajeRespuesta> updatePais(
            @Valid @RequestBody Pais pais) {
        try {
            Pais.MensajeRespuesta respuesta = paisService.updatePais(pais);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el país con ID {}: {} ===>", pais.getPaCod(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Pais.MensajeRespuesta(500L, "Error al actualizar el país: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Pais.MensajeRespuesta> deletePais(
            @RequestParam("paCod") Long paCod) {
        try {
            Pais.MensajeRespuesta respuesta = paisService.deletePais(paCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el país con código {}: {} ===>", paCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Pais.MensajeRespuesta(500L, "Error al eliminar el país: " + e.getMessage(), null));
        }
    }
}
