package py.com.nsa.api.commons.components.ref.tipodocumento.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import py.com.nsa.api.commons.components.ref.tipodocumento.model.TipoDocumento;
import py.com.nsa.api.commons.components.ref.tipodocumento.service.TipoDocumentoService;

@RestController
@RequestMapping("/tipodocumento")
public class TipoDocumentoController {

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    private static final Logger logger = LoggerFactory.getLogger(TipoDocumentoController.class);

    @GetMapping("/lista")
    public ResponseEntity<TipoDocumento.MensajeRespuesta> getTiposDocumentoAll(
            @RequestParam(value = "tdoDocumentoWu", required = false) String tdoDocumentoWu) {
        try {
            TipoDocumento.MensajeRespuesta respuesta = tipoDocumentoService.getTipoDocumentosAll(tdoDocumentoWu);

            // Si la respuesta es 200 (datos o mensaje indicando que no hay tipos de documentos)
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            }  else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }

        } catch (Exception e) {
            logger.error("<=== Error al obtener los tipos de documento: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoDocumento.MensajeRespuesta(500L, "Error al obtener tipos de documento: " + e.getMessage(), null));
        }
    }


    @PostMapping("/insert")
    public ResponseEntity<TipoDocumento.MensajeRespuesta> insertTipoDocumento(@Valid @RequestBody TipoDocumento tipoDocumento) {
        try {
            TipoDocumento.MensajeRespuesta respuesta = tipoDocumentoService.insertTipoDocumento(tipoDocumento);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar tipo de documento: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoDocumento.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TipoDocumento.MensajeRespuesta> updateTipoDocumento(@Valid @RequestBody TipoDocumento tipoDocumento) {
        try {
            TipoDocumento.MensajeRespuesta respuesta = tipoDocumentoService.updateTipoDocumento(tipoDocumento);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar tipo de documento: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoDocumento.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteTipoDocumento(
            @RequestParam("tdoCodigo") Long tdoCodigo) {
            boolean result = tipoDocumentoService.deleteById(tdoCodigo);
            return ResponseEntity.ok(result);
    }

}
