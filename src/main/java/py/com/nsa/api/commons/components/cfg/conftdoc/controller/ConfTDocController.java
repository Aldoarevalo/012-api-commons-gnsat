package py.com.nsa.api.commons.components.cfg.conftdoc.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.conftdoc.model.ConfTDoc;
import py.com.nsa.api.commons.components.cfg.conftdoc.service.ConfTDocService;

@RestController
@RequestMapping("/conf-doc")
public class ConfTDocController {
    private static final Logger logger = LoggerFactory.getLogger(ConfTDocController.class);

    @Autowired
    private ConfTDocService confTDocService;

    @GetMapping("/lista")
    public ResponseEntity<ConfTDoc.MensajeRespuesta> getAllDocs() {
        try {
            ConfTDoc.MensajeRespuesta respuesta = confTDocService.getAllDocs();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los documentos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ConfTDoc.MensajeRespuesta(500L, "Error al obtener los documentos: " + e.getMessage(),
                            null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<ConfTDoc.MensajeRespuesta> insertDoc(
            @Valid @RequestBody ConfTDoc doc) {
        try {
            ConfTDoc.MensajeRespuesta respuesta = confTDocService.insertDoc(doc);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el documento: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ConfTDoc.MensajeRespuesta(500L, "Error al insertar el documento: " + e.getMessage(),
                            null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ConfTDoc.MensajeRespuesta> updateDoc(
            @Valid @RequestBody ConfTDoc doc) {
        try {
            ConfTDoc.MensajeRespuesta respuesta = confTDocService.updateDoc(doc);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el documento con ID {}: {} ===>", doc.getDocCod(), e.getMessage(),
                    e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ConfTDoc.MensajeRespuesta(500L, "Error al actualizar el documento: " + e.getMessage(),
                            null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ConfTDoc.MensajeRespuesta> deleteDoc(
            @RequestParam("docCod") Long docCod) {
        try {
            ConfTDoc.MensajeRespuesta respuesta = confTDocService.deleteDoc(docCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el documento con código {}: {} ===>", docCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ConfTDoc.MensajeRespuesta(500L, "Error al eliminar el documento: " + e.getMessage(),
                            null));
        }
    }
}
