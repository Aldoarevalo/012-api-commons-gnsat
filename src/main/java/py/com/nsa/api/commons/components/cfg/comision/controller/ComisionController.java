package py.com.nsa.api.commons.components.cfg.comision.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.comision.model.Comision;
import py.com.nsa.api.commons.components.cfg.comision.service.ComisionService;

@RestController
@RequestMapping("/comision")
public class ComisionController {
    private static final Logger logger = LoggerFactory.getLogger(ComisionController.class);

    @Autowired
    private ComisionService comisionService;

    @GetMapping("/lista")
    public ResponseEntity<Comision.MensajeRespuesta> getComisionesAll() {
        try {
            Comision.MensajeRespuesta respuesta = comisionService.getComisionesAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener las comisiones: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Comision.MensajeRespuesta(500L, "Error al obtener comisiones: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Comision.MensajeRespuesta> getComisionById(@PathVariable("codigo") Integer codigo) {
        try {
            Comision.MensajeRespuesta respuesta = comisionService.getComisionById(codigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener la comisión con ID {}: {} ===>", codigo, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Comision.MensajeRespuesta(500L, "Error al obtener la comisión: " + e.getMessage(), null));
        }
    }

    @GetMapping("/filtrar")
    public ResponseEntity<Comision.MensajeRespuesta> getComisionesFiltradas(
            @RequestParam(required = false) String tipoTransac,
            @RequestParam(required = false) String moneda,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Integer codAg) {
        try {
            Comision.MensajeRespuesta respuesta = comisionService.getComisionesByFiltros(
                    tipoTransac, moneda, estado, codAg);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al filtrar comisiones: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Comision.MensajeRespuesta(500L, "Error al filtrar comisiones: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Comision.MensajeRespuesta> insertarComision(
            @Valid @RequestBody Comision comision) {
        try {
            Comision.MensajeRespuesta respuesta = comisionService.insertarComision(comision);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar la comisión: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Comision.MensajeRespuesta(500L, "Error al insertar la comisión: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Comision.MensajeRespuesta> updateComision(
            @Valid @RequestBody Comision comision) {
        try {
            Comision.MensajeRespuesta respuesta = comisionService.updateComision(comision);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar la comisión con ID {}: {} ===>", comision.getComCod(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Comision.MensajeRespuesta(500L, "Error al actualizar la comisión: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<Comision.MensajeRespuesta> delete(@PathVariable("codigo") Integer codigo) {
        try {
            Comision.MensajeRespuesta respuesta = comisionService.deleteComision(codigo);

            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 204L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar la comisión con código {}: {} ===>", codigo, e.getMessage(), e);

            Comision.MensajeRespuesta errorRespuesta = new Comision.MensajeRespuesta(
                    500L,
                    "Error al eliminar la comisión: " + e.getMessage(),
                    null
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorRespuesta);
        }
    }
}
