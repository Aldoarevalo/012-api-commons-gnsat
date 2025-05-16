package py.com.nsa.api.commons.components.cfg.cobrador.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.cobrador.model.Cobrador;
import py.com.nsa.api.commons.components.cfg.cobrador.service.CobradorService;

@RestController
@RequestMapping("/cobrador")
public class CobradorController {

    private static final Logger logger = LoggerFactory.getLogger(CobradorController.class);

    @Autowired
    private CobradorService cobradorService;

    @GetMapping("/lista")
    public ResponseEntity<Cobrador.MensajeRespuesta> getCobradoresAll() {
        try {
            Cobrador.MensajeRespuesta respuesta = cobradorService.getCobradoresAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al obtener cobradores: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cobrador.MensajeRespuesta(500L, "Error al obtener cobradores: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Cobrador.MensajeRespuesta> insertarCobrador(@RequestBody Cobrador cobrador) {
        try {
            Cobrador.MensajeRespuesta respuesta = cobradorService.insertarCobrador(cobrador);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al insertar el cobrador: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cobrador.MensajeRespuesta(500L, "Error al insertar el cobrador: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Cobrador.MensajeRespuesta> updateCobrador(@RequestBody Cobrador cobrador) {
        try {
            Cobrador.MensajeRespuesta respuesta = cobradorService.updateCobrador(cobrador);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al actualizar el cobrador con código {}: ", cobrador.getCobrCodigo(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cobrador.MensajeRespuesta(500L, "Error al actualizar el cobrador: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Cobrador.MensajeRespuesta> deleteCobrador(
            @RequestParam("cobrCodigo") Long cobrCodigo,
            @RequestParam("carCodigo") Long carCodigo) {
        try {
            Cobrador.MensajeRespuesta respuesta = cobradorService.deleteCobrador(cobrCodigo, carCodigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al eliminar el cobrador con código {} y cargo {}: ", cobrCodigo, carCodigo, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cobrador.MensajeRespuesta(500L, "Error al eliminar el cobrador: " + e.getMessage(), null));
        }
    }
}
