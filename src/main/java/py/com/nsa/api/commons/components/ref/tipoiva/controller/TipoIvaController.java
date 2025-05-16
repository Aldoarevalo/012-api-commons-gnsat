package py.com.nsa.api.commons.components.ref.tipoiva.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.tipoiva.modelo.TipoIva;
import py.com.nsa.api.commons.components.ref.tipoiva.service.TipoIvaService;

@RestController
@RequestMapping("/tipoiva")
public class TipoIvaController {

    private static final Logger logger = LoggerFactory.getLogger(TipoIvaController.class);

    @Autowired
    private TipoIvaService tipoIvaService;

    @GetMapping("/lista")
    public ResponseEntity<TipoIva.MensajeRespuesta> getTipoIvaAll() {
        try {
            TipoIva.MensajeRespuesta respuesta = tipoIvaService.getTiposIvaAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener tipos de IVA: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoIva.MensajeRespuesta(500L, "Error al obtener tipos de IVA: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<TipoIva.MensajeRespuesta> insertarTipoIva(@Valid @RequestBody TipoIva tipoIva) {
        try {
            TipoIva.MensajeRespuesta respuesta = tipoIvaService.insertarTipoIva(tipoIva);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar tipo de IVA: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoIva.MensajeRespuesta(500L, "Error al insertar tipo de IVA: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TipoIva.MensajeRespuesta> updateTipoIva(@Valid @RequestBody TipoIva tipoIva) {
        try {
            TipoIva.MensajeRespuesta respuesta = tipoIvaService.updateTipoIva(tipoIva);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar tipo de IVA con ID {}: {} ===>", tipoIva.getIvaCodigo(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoIva.MensajeRespuesta(500L, "Error al actualizar tipo de IVA: " + e.getMessage(), null));
        }
    }

    /*
    @DeleteMapping("/delete")
    public ResponseEntity<TipoIva.MensajeRespuesta> deleteTipoIva(@RequestParam("ivaCodigo") Long ivaCodigo) {
        try {
            TipoIva.MensajeRespuesta respuesta = tipoIvaService.deleteTipoIva(ivaCodigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al eliminar tipo de IVA con ID {}: {} ===>", ivaCodigo, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoIva.MensajeRespuesta(500L, "Error al eliminar tipo de IVA: " + e.getMessage(), null));
        }
    }
    */

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(
            @RequestParam("ivaCodigo") Long ivaCodigo) {
        boolean result = tipoIvaService.deleteById(ivaCodigo);
        return ResponseEntity.ok(result);
    }

}
