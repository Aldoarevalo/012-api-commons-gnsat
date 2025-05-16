package py.com.nsa.api.commons.components.cfg.comisioncobrador.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.comisioncobrador.modelo.ComisionCobrador;
import py.com.nsa.api.commons.components.cfg.comisioncobrador.service.ComisionCobradorService;

@RestController
@RequestMapping("/comision-cobrador")
public class ComisionCobradorController {

    @Autowired
    private ComisionCobradorService comisionCobradorService;

    private static final Logger logger = LoggerFactory.getLogger(ComisionCobradorController.class);

    @GetMapping("/lista")
    public ResponseEntity<ComisionCobrador.MensajeRespuesta> getComisionesCobrador() {
        try {
            ComisionCobrador.MensajeRespuesta respuesta = comisionCobradorService.getComisionesCobrador();

            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener las comisiones de cobrador: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ComisionCobrador.MensajeRespuesta(500L, "Error al obtener comisiones de cobrador: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<ComisionCobrador.MensajeRespuesta> insertarComisionCobrador(@RequestBody ComisionCobrador comisionCobrador) {
        try {
            ComisionCobrador.MensajeRespuesta respuesta = comisionCobradorService.insertarComisionCobrador(comisionCobrador);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar comisión de cobrador: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ComisionCobrador.MensajeRespuesta(500L, "Error al insertar comisión de cobrador: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ComisionCobrador.MensajeRespuesta> updateComisionCobrador(@RequestBody ComisionCobrador comisionCobrador) {
        try {
            ComisionCobrador.MensajeRespuesta respuesta = comisionCobradorService.updateComisionCobrador(comisionCobrador);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else  {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar comisión de cobrador: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ComisionCobrador.MensajeRespuesta(500L, "Error al actualizar comisión de cobrador: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ComisionCobrador.MensajeRespuesta> deleteComisionCobrador(@RequestParam("comCodigo") Long comCodigo) {
        try {
            ComisionCobrador.MensajeRespuesta respuesta = comisionCobradorService.deleteComisionCobrador(comCodigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar comisión de cobrador: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ComisionCobrador.MensajeRespuesta(500L, "Error al eliminar comisión de cobrador: " + e.getMessage(), null));
        }
    }
}