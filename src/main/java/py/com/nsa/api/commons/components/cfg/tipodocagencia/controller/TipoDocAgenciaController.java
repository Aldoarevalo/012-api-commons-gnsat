package py.com.nsa.api.commons.components.cfg.tipodocagencia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.tipodocagencia.model.TipoDocAgencia;
import py.com.nsa.api.commons.components.cfg.tipodocagencia.service.TipoDocAgenciaService;

@RestController
@RequestMapping("/tipodocagencia")
public class TipoDocAgenciaController {

    private static final Logger logger = LoggerFactory.getLogger(TipoDocAgenciaController.class);

    @Autowired
    private TipoDocAgenciaService tipoDocAgenciaService;

    @GetMapping("/lista")
    public ResponseEntity<TipoDocAgencia.MensajeRespuesta> getTipoDocAgenciasAll() {
        try {
            TipoDocAgencia.MensajeRespuesta respuesta = tipoDocAgenciaService.getTipoDocAgenciasAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al obtener tipos de documentos de agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoDocAgencia.MensajeRespuesta(500L, "Error al obtener tipos de documentos de agencia: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<TipoDocAgencia.MensajeRespuesta> insertarTipoDocAgencia(@RequestBody TipoDocAgencia tipoDocAgencia) {
        try {
            TipoDocAgencia.MensajeRespuesta respuesta = tipoDocAgenciaService.insertarTipoDocAgencia(tipoDocAgencia);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al insertar tipo de documento de agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoDocAgencia.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TipoDocAgencia.MensajeRespuesta> updateTipoDocAgencia(@RequestBody TipoDocAgencia tipoDocAgencia) {
        logger.error("updateTipoDocAgencia. getTcdCod(): " + tipoDocAgencia.getTcdCod());
        try {
            TipoDocAgencia.MensajeRespuesta respuesta = tipoDocAgenciaService.updateTipoDocAgencia(tipoDocAgencia);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al actualizar tipo de documento de agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoDocAgencia.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<TipoDocAgencia.MensajeRespuesta> deleteTipoDocAgencia(@RequestParam Long tcdCod) {
        try {
            TipoDocAgencia.MensajeRespuesta respuesta = tipoDocAgenciaService.deleteTipoDocAgencia(tcdCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al eliminar tipo de documento de agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoDocAgencia.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }
}
