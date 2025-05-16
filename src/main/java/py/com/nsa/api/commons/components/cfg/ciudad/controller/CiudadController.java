package py.com.nsa.api.commons.components.cfg.ciudad.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.ciudad.model.Ciudad;
import py.com.nsa.api.commons.components.cfg.ciudad.service.CiudadService;

@RestController
@RequestMapping("/ciudad")
public class CiudadController {
    private static final Logger logger = LoggerFactory.getLogger(CiudadController.class);

    @Autowired
    private CiudadService ciudadService;

    @GetMapping("/lista")
    public ResponseEntity<Ciudad.MensajeRespuesta> getCiudadesAll() {
        try {
            Ciudad.MensajeRespuesta respuesta = ciudadService.getCiudadesAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }

        } catch (Exception e) {
            logger.error("<=== Error al obtener las ciudades: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Ciudad.MensajeRespuesta(500L, "Error al obtener las ciudades: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Ciudad.MensajeRespuesta> insertarCiudad(
            @Valid @RequestBody Ciudad ciudad) {
        try {
            Ciudad.MensajeRespuesta respuesta = ciudadService.insertarCiudad(ciudad);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar la ciudad: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Ciudad.MensajeRespuesta(500L, "Error al insertar la ciudad: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Ciudad.MensajeRespuesta> updateCiudad(
            @Valid @RequestBody Ciudad ciudad) {
        try {
            Ciudad.MensajeRespuesta respuesta = ciudadService.updateCiudad(ciudad);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar la ciudad con ID {}: {} ===>", ciudad.getCiuCod(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Ciudad.MensajeRespuesta(500L, "Error al actualizar la ciudad: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Ciudad.MensajeRespuesta> deleteCiudad(
            @RequestParam("ciuCod") Long ciuCod) {
        try {
            Ciudad.MensajeRespuesta respuesta = ciudadService.deleteCiudad(ciuCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar la ciudad con código {}: {} ===>", ciuCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Ciudad.MensajeRespuesta(500L, "Error al eliminar la ciudad: " + e.getMessage(), null));
        }
    }
}
