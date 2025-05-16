package py.com.nsa.api.commons.components.ref.pdoc.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.pdoc.model.PDoc;
import py.com.nsa.api.commons.components.ref.pdoc.service.PDocService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/p-doc")
public class PDocController {

    private static final Logger logger = LoggerFactory.getLogger(PDocController.class);

    @Autowired
    private PDocService PDocService;

    @GetMapping("/lista")
    public ResponseEntity<PDoc.MensajeRespuesta> getAllDocs(@RequestParam(required = false) Long pcod) {
        try {
            PDoc.MensajeRespuesta respuesta = PDocService.getAllDocs(pcod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los documentos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PDoc.MensajeRespuesta(500L, "Error al obtener los documentos: " + e.getMessage(), null));
        }
    }

    @GetMapping("/lista2")
    public ResponseEntity<Map<String, Object>> getAllDocs2(@RequestParam(required = false) Long pcod) {
        try {
            // Llamar al servicio que obtiene los datos
            Map<String, Object> response = PDocService.getAllDocs2(pcod);

            // Obtener el status del mapa de respuesta
            int status = (int) response.get("status");

            // Determinar la respuesta según el status
            if (status == 200) {
                return ResponseEntity.ok(response); // Respuesta exitosa
            } else if (status == 204) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // No se encontraron resultados
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // Error o estado no esperado
            }
        } catch (Exception e) {
            // Manejo de excepciones
            logger.error("<=== Error al obtener los PDocs con ConfTDocs: {} ===>", e.getMessage(), e);

            // Respuesta de error con estado 500
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("mensaje", "Error al obtener los documentos: " + e.getMessage());
            errorResponse.put("detalles", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<PDoc.MensajeRespuesta> insertDoc(@Valid @RequestBody PDoc doc) {
        try {
            PDoc.MensajeRespuesta respuesta = PDocService.insertDoc(doc);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el documento: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PDoc.MensajeRespuesta(500L, "Error al insertar el documento: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<PDoc.MensajeRespuesta> updateDoc(@Valid @RequestBody PDoc doc) {
        try {
            PDoc.MensajeRespuesta respuesta = PDocService.updateDocs(doc);
            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el documento con código {} ===>", doc.getDocCod(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PDoc.MensajeRespuesta(500L, "Error al actualizar el documento: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<PDoc.MensajeRespuesta> deleteDoc(@RequestParam("docCod") Long docCod,
            @RequestParam("pDocNroDoc") String pDocNroDoc, @RequestParam("pcod") Long pcod) {
        try {
            PDoc.MensajeRespuesta respuesta = PDocService.deleteDoc(docCod, pDocNroDoc, pcod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el documento con código {}: {} ===>", docCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PDoc.MensajeRespuesta(500L, "Error al eliminar el documento: " + e.getMessage(), null));
        }
    }
}
