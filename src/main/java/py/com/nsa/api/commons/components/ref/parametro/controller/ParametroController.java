package py.com.nsa.api.commons.components.ref.parametro.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.parametro.model.Parametro;
import py.com.nsa.api.commons.components.ref.parametro.service.ParametroService;

@RestController
@RequestMapping("/parametro")
public class ParametroController {

    private static final Logger logger = LoggerFactory.getLogger(ParametroController.class);

    @Autowired
    private ParametroService parametroService;

    // Obtener todos los parámetros
    @GetMapping("/lista")
    public ResponseEntity<Parametro.MensajeRespuesta> getParametrosAll() {
        try {
            Parametro.MensajeRespuesta respuesta = parametroService.getParametrosAll();
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 204L) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al obtener parámetros: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Parametro.MensajeRespuesta(500L, "Error al obtener parámetros: " + e.getMessage(), null));
        }
    }

    // Insertar un nuevo parámetro
    @PostMapping("/insert")
    public ResponseEntity<Parametro.MensajeRespuesta> insertarParametro(@Valid @RequestBody Parametro parametro) {
        try {
            Parametro.MensajeRespuesta respuesta = parametroService.insertarParametro(parametro);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 204L) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al insertar el parámetro: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Parametro.MensajeRespuesta(500L, "Error al insertar el parámetro: " + e.getMessage(), null));
        }
    }

    // Actualizar un parámetro existente
    /*@PutMapping("/update")
    public ResponseEntity<Parametro.MensajeRespuesta> updateParametro(@Valid @RequestBody Parametro parametro) {
        try {
            Parametro.MensajeRespuesta respuesta = parametroService.updateParametro(parametro);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 204L) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al actualizar el parámetro con código {} y nombre {}: {}", parametro.getPmCod(), parametro.getPmNombre(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Parametro.MensajeRespuesta(500L, "Error al actualizar el parámetro: " + e.getMessage(), null));
        }
    }*/

    // Eliminar un parámetro
    @DeleteMapping("/delete")
    public ResponseEntity<Parametro.MensajeRespuesta> deleteParametro(@RequestParam("pmCod") Long pmCod, @RequestParam("pmNombre") String pmNombre) {
        try {
            Parametro.MensajeRespuesta respuesta = parametroService.deleteParametro(pmCod, pmNombre);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 204L) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el parámetro con código {} y nombre {}: {}", pmCod, pmNombre, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Parametro.MensajeRespuesta(500L, "Error al eliminar el parámetro: " + e.getMessage(), null));
        }
    }
}
