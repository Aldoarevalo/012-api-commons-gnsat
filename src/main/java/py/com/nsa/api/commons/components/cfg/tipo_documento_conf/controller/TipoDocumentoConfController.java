package py.com.nsa.api.commons.components.cfg.tipo_documento_conf.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.grupo_usuario.model.GrupoUsuario;
import py.com.nsa.api.commons.components.cfg.tipo_documento_conf.model.TipoDocumentoConf;
import py.com.nsa.api.commons.components.cfg.tipo_documento_conf.service.TipoDocumentoConfService;

@RestController
@RequestMapping("/tipo-documento-conf")
public class TipoDocumentoConfController {
    private static final Logger logger = LoggerFactory.getLogger(TipoDocumentoConfController.class);

    @Autowired
    private TipoDocumentoConfService tipoDocumentoConfService;

    @GetMapping("/lista")
    public ResponseEntity<TipoDocumentoConf.MensajeRespuesta> getTiposDocumentoAll() {
        try {
            TipoDocumentoConf.MensajeRespuesta respuesta = tipoDocumentoConfService.getTiposDocumentoAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los tipos de documento: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoDocumentoConf.MensajeRespuesta(500L, "Error al obtener tipos de documento: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<TipoDocumentoConf.MensajeRespuesta> insertarTipoDocumento(
            @Valid @RequestBody TipoDocumentoConf tipoDocumento) {
        try {
            TipoDocumentoConf.MensajeRespuesta respuesta = tipoDocumentoConfService.insertarTipoDocumento(tipoDocumento);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el tipo de documento: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoDocumentoConf.MensajeRespuesta(500L, "Error al insertar el tipo de documento: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TipoDocumentoConf.MensajeRespuesta> updateTipoDocumento(
            @Valid @RequestBody TipoDocumentoConf tipoDocumento) {
        try {
            TipoDocumentoConf.MensajeRespuesta respuesta = tipoDocumentoConfService.updateTipoDocumento(tipoDocumento);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el tipo de documento con ID {}: {} ===>", tipoDocumento.getDocCod(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoDocumentoConf.MensajeRespuesta(500L, "Error al actualizar el tipo de documento: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<TipoDocumentoConf.MensajeRespuesta> delete(@PathVariable("codigo") Integer codigo) {
        try {
            // Convierte el código a Long si es necesario
            TipoDocumentoConf.MensajeRespuesta respuesta = tipoDocumentoConfService.deleteTipoDocumento(codigo.longValue());

            // Evalúa el estado de la respuesta con if-else
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
            logger.error("<=== Error al eliminar la asignación con código {}: {} ===>", codigo, e.getMessage(), e);

            // Crea una nueva instancia de MensajeRespuesta para devolver en caso de error
            TipoDocumentoConf.MensajeRespuesta errorRespuesta = new TipoDocumentoConf.MensajeRespuesta(
                    500L,
                    "Error al eliminar la asignación: " + e.getMessage(),
                    null
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorRespuesta);
        }
    }
}