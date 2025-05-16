package py.com.nsa.api.commons.components.cfg.documento_agencia.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import py.com.nsa.api.commons.components.cfg.documento_agencia.model.DocumentoAgencia;
import py.com.nsa.api.commons.components.cfg.documento_agencia.service.DocumentoAgenciaService;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("doc-agencia")
public class DocumentoAgenciaController {
    @Autowired
    private DocumentoAgenciaService service;

    private static final Logger logger = LoggerFactory.getLogger(DocumentoAgenciaController.class);

    @GetMapping("/lista")
    public ResponseEntity<DocumentoAgencia.MensajeRespuesta> getAgenciaList() {
        try {
            DocumentoAgencia.MensajeRespuesta respuesta = service.getAgenciasAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener tipos de IVA: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DocumentoAgencia.MensajeRespuesta(
                    500L, "Error al obtener Documento agencias: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<DocumentoAgencia.MensajeRespuesta> getAgenciaFiltered(@RequestBody DocumentoAgencia filtro) {
        try {
            DocumentoAgencia.MensajeRespuesta respuesta = service.getAgenciasFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener Documento Agencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DocumentoAgencia.MensajeRespuesta(
                    500L, "Error al obtener Documento agencias: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<DocumentoAgencia.MensajeRespuesta> insertAgenciaDoc(
            @RequestBody Object payload) {
        try {
            // Manejar el payload tanto si es array como si es objeto
            Map<String, Object> bodyMap;
            if (payload instanceof List) {
                // Si es un array, tomar el primer elemento
                bodyMap = ((List<Map<String, Object>>) payload).get(0);
            } else {
                // Si es un objeto, usarlo directamente
                bodyMap = (Map<String, Object>) payload;
            }

            // Extraer el body y el multipart
            Map<String, Object> body = (Map<String, Object>) bodyMap.get("body");
            List<Map<String, String>> multipart = (List<Map<String, String>>) body.get("multipart");

            // Mapear los campos
            String agCod = findValueByName(multipart, "agCod");
            String tcdCod = findValueByName(multipart, "tcdCod");
            String dcDescripcion = findValueByName(multipart, "dcDescripcion");
            String dcVencimiento = findValueByName(multipart, "dcVencimiento");
            String adjuntoBase64 = findValueByName(multipart, "adjunto");

            // Validar campos obligatorios
            if (agCod == null || tcdCod == null || dcDescripcion == null ||
                    dcVencimiento == null || adjuntoBase64 == null) {
                return ResponseEntity.badRequest()
                        .body(new DocumentoAgencia.MensajeRespuesta(204L,
                                "Faltan parámetros obligatorios.", null));
            }

            // Convertir los datos al formato esperado
            DocumentoAgencia docAgencia = new DocumentoAgencia();
            docAgencia.setAgCod(Long.parseLong(agCod));
            docAgencia.setTcdCod(Long.parseLong(tcdCod));
            docAgencia.setDcDescripcion(dcDescripcion);
            docAgencia.setDcVencimiento(new SimpleDateFormat("yyyy-MM-dd").parse(dcVencimiento));

            // Llamar al servicio para insertar
            DocumentoAgencia.MensajeRespuesta respuesta = service.insert(docAgencia, adjuntoBase64);

            return ResponseEntity.ok(respuesta);

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                    .body(new DocumentoAgencia.MensajeRespuesta(400L,
                            "Error en el formato de los números: " + e.getMessage(), null));
        } catch (ParseException e) {
            return ResponseEntity.badRequest()
                    .body(new DocumentoAgencia.MensajeRespuesta(400L,
                            "Error en el formato de la fecha: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DocumentoAgencia.MensajeRespuesta(500L,
                            "Error al procesar la solicitud: " + e.getMessage(), null));
        }
    }


    @PutMapping("/update")
    public ResponseEntity<DocumentoAgencia.MensajeRespuesta> updateAgenciaDoc(
            @RequestBody Object payload) {
        try {
            // Manejar el payload tanto si es array como si es objeto
            Map<String, Object> bodyMap;
            if (payload instanceof List) {
                // Si es un array, tomar el primer elemento
                bodyMap = ((List<Map<String, Object>>) payload).get(0);
            } else {
                // Si es un objeto, usarlo directamente
                bodyMap = (Map<String, Object>) payload;
            }

            // Extraer el body y el multipart
            Map<String, Object> body = (Map<String, Object>) bodyMap.get("body");
            List<Map<String, String>> multipart = (List<Map<String, String>>) body.get("multipart");

            // Función auxiliar para encontrar valores en el multipart
            String dcDoc = findValueByName(multipart, "dcDoc");
            String agCod = findValueByName(multipart, "agCod");
            String tcdCod = findValueByName(multipart, "tcdCod");
            String dcDescripcion = findValueByName(multipart, "dcDescripcion");
            String dcVencimiento = findValueByName(multipart, "dcVencimiento");
            String adjuntoBase64 = findValueByName(multipart, "adjunto");

            // Validar campos obligatorios
            if (agCod == null || tcdCod == null || dcDescripcion == null ||
                    dcVencimiento == null || adjuntoBase64 == null) {
                return ResponseEntity.badRequest()
                        .body(new DocumentoAgencia.MensajeRespuesta(204L,
                                "Faltan parámetros obligatorios.", null));
            }

            // Convertir los datos al formato esperado
            DocumentoAgencia docAgencia = new DocumentoAgencia();
            docAgencia.setDcDoc(Long.parseLong(dcDoc));
            docAgencia.setAgCod(Long.parseLong(agCod));
            docAgencia.setTcdCod(Long.parseLong(tcdCod));
            docAgencia.setDcDescripcion(dcDescripcion);
            docAgencia.setDcVencimiento(new SimpleDateFormat("yyyy-MM-dd").parse(dcVencimiento));

            // Llamar al servicio para actualizar
            DocumentoAgencia.MensajeRespuesta respuesta = service.update(docAgencia, adjuntoBase64);

            return ResponseEntity.ok(respuesta);

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                    .body(new DocumentoAgencia.MensajeRespuesta(400L,
                            "Error en el formato de los números: " + e.getMessage(), null));
        } catch (ParseException e) {
            return ResponseEntity.badRequest()
                    .body(new DocumentoAgencia.MensajeRespuesta(400L,
                            "Error en el formato de la fecha: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DocumentoAgencia.MensajeRespuesta(500L,
                            "Error al procesar la solicitud: " + e.getMessage(), null));
        }
    }

    // Método auxiliar para buscar valores en el multipart
    private String findValueByName(List<Map<String, String>> multipart, String name) {
        return multipart.stream().filter(map -> name.equals(map.get("name"))).map(map -> map.get("contents"))
                .findFirst().orElse(null);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DocumentoAgencia.MensajeRespuesta> deleteDocumento(
            @RequestParam("dcDoc") Long dcDoc) {
        try {
            DocumentoAgencia.MensajeRespuesta respuesta = service.deleteDocumento(dcDoc);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el Documento de Agencia con código {}: {} ===>", dcDoc, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DocumentoAgencia.MensajeRespuesta(500L, "Error al eliminar el Documento de Agencia: " + e.getMessage(), null));
        }
    }

    /*
     * @PutMapping("/update")
     * public ResponseEntity<DocumentoAgencia.MensajeRespuesta>
     * update(@Valid @RequestBody DocumentoAgencia docagencia) {
     * try {
     * DocumentoAgencia.MensajeRespuesta respuesta = service.update(docagencia);
     * if (200L == respuesta.getStatus()) {
     * return ResponseEntity.ok(respuesta);
     * } else {
     * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
     * }
     * } catch (RuntimeException e) {
     * logger.error("<=== Error al actualizar agencia: {} ===>", e.getMessage(), e);
     * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new
     * DocumentoAgencia.MensajeRespuesta(500L, e.getMessage(), null));
     * }
     * }
     * 
     * @PutMapping("/update-file")
     * public ResponseEntity<DocumentoAgencia.MensajeRespuesta>
     * update(@RequestParam("dcDoc") Long dcDoc, // Este campo es obligatorio para
     * identificar el documento
     * 
     * @RequestParam(value = "tcdCod", required = false) Long
     * tcdCod, @RequestParam(value = "dcDescripcion", required = false) String
     * dcDescripcion, @RequestParam(value = "dcVencimiento", required = false) Date
     * dcVencimiento, @RequestPart(value = "adjunto", required = false)
     * MultipartFile file) {
     * 
     * DocumentoAgencia docAgencia = new DocumentoAgencia();
     * docAgencia.setDcDoc(dcDoc);
     * docAgencia.setTcdCod(tcdCod);
     * docAgencia.setDcDescripcion(dcDescripcion);
     * docAgencia.setDcVencimiento(dcVencimiento);
     * 
     * // Llamada al servicio para actualizar el documento
     * if (file != null && !file.isEmpty()) {
     * DocumentoAgencia.MensajeRespuesta respuesta = service.update(docAgencia,
     * file);
     * logger.error("<=== los Documento Agencia con File: {} ===>", docAgencia);
     * if (respuesta.getStatus() == 200L) {
     * return ResponseEntity.ok(respuesta);
     * } else {
     * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
     * }
     * }
     * return null;
     * }
     */
}
