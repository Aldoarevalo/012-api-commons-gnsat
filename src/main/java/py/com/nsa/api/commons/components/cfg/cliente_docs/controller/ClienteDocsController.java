package py.com.nsa.api.commons.components.cfg.cliente_docs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.cliente_docs.model.ClienteDocs;
import py.com.nsa.api.commons.components.cfg.cliente_docs.service.ClienteDocsService;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("clientedocs")
public class ClienteDocsController {

    @Autowired
    private ClienteDocsService service;

    private static final Logger logger = LoggerFactory.getLogger(ClienteDocsController.class);

    @GetMapping("/lista")
    public ResponseEntity<ClienteDocs.MensajeRespuesta> getList() {
        try {
            ClienteDocs.MensajeRespuesta respuesta = service.getList();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener la lista de documentos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ClienteDocs.MensajeRespuesta(500L, "Error al obtener la lista de documentos: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<ClienteDocs.MensajeRespuesta> getClienteDocsFiltered(@RequestBody ClienteDocs filtro) {
        try {
            ClienteDocs.MensajeRespuesta respuesta = service.getClienteDocsFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener documentos del cliente filtrados: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ClienteDocs.MensajeRespuesta(500L, "Error al obtener documentos del cliente filtrados: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<ClienteDocs.MensajeRespuesta> insertClienteDocs(
            @RequestBody Map<String, Object> payload) {
        try {
            // Extraer los datos del payload
            Map<String, Object> body = (Map<String, Object>) payload.get("body");
            List<Map<String, String>> multipart = (List<Map<String, String>>) body.get("multipart");

            // Mapear los campos
            String agCod = findValueByName(multipart, "agCod");
            String cdCodCliente = findValueByName(multipart, "cdCodCliente");
            String cdTipoDoc = findValueByName(multipart, "cdTipoDoc");
            String cdDescripcion = findValueByName(multipart, "cdDescripcion");
            String cdFechaVto = findValueByName(multipart, "cdFechaVto");
            String adjuntoBase64 = findValueByName(multipart, "adjunto");

            // Validar campos obligatorios
            if (agCod == null || cdCodCliente == null || cdTipoDoc == null || cdDescripcion == null || cdFechaVto == null || adjuntoBase64 == null) {
                return ResponseEntity.badRequest()
                        .body(new ClienteDocs.MensajeRespuesta(204L, "Faltan parámetros obligatorios.", null));
            }

            // Convertir los datos al formato esperado
            ClienteDocs clienteDocs = new ClienteDocs();
            clienteDocs.setAgCod(Long.parseLong(agCod));
            clienteDocs.setCdCodCliente(Long.parseLong(cdCodCliente));
            clienteDocs.setCdTipoDoc(cdTipoDoc);
            clienteDocs.setCdDescripcion(cdDescripcion);
            clienteDocs.setCdFechaVto(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(cdFechaVto).getTime()));

            // Llamar al servicio para insertar
            ClienteDocs.MensajeRespuesta respuesta = service.insert(clienteDocs, adjuntoBase64);

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ClienteDocs.MensajeRespuesta(500L,
                    "Error al procesar la solicitud: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ClienteDocs.MensajeRespuesta> updateClienteDocs(
            @RequestBody Map<String, Object> payload) {
        try {
            // Extraer los datos del payload
            Map<String, Object> body = (Map<String, Object>) payload.get("body");
            List<Map<String, String>> multipart = (List<Map<String, String>>) body.get("multipart");

            // Mapear los campos
            String cdCod = findValueByName(multipart, "cdCod");
            String agCod = findValueByName(multipart, "agCod");
            String cdCodCliente = findValueByName(multipart, "cdCodCliente");
            String cdTipoDoc = findValueByName(multipart, "cdTipoDoc");
            String cdDescripcion = findValueByName(multipart, "cdDescripcion");
            String cdFechaVto = findValueByName(multipart, "cdFechaVto");
            String adjuntoBase64 = findValueByName(multipart, "adjunto");

            // Validar campos obligatorios
            if (cdCod == null) {
                return ResponseEntity.badRequest()
                        .body(new ClienteDocs.MensajeRespuesta(204L, "El ID del documento (cdCod) es obligatorio.", null));
            }

            // Validar campos obligatorios
            if (agCod == null || cdCodCliente == null || cdTipoDoc == null || cdDescripcion == null || cdFechaVto == null || adjuntoBase64 == null) {
                return ResponseEntity.badRequest()
                        .body(new ClienteDocs.MensajeRespuesta(204L, "Faltan parámetros obligatorios.", null));
            }

            // Convertir los datos al formato esperado
            ClienteDocs clienteDocs = new ClienteDocs();
            clienteDocs.setCdCod(Long.parseLong(cdCod));
            clienteDocs.setAgCod(Long.parseLong(agCod));
            clienteDocs.setCdCodCliente(Long.parseLong(cdCodCliente));
            clienteDocs.setCdTipoDoc(cdTipoDoc);
            clienteDocs.setCdDescripcion(cdDescripcion);
            clienteDocs.setCdFechaVto(new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(cdFechaVto).getTime()));

            // Llamar al servicio para actualizar
            ClienteDocs.MensajeRespuesta respuesta = service.update(clienteDocs, adjuntoBase64);

            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ClienteDocs.MensajeRespuesta(500L,
                    "Error al procesar la solicitud: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ClienteDocs.MensajeRespuesta> deleteClienteDocs(
            @RequestParam("cdCod") Long cdCod) {
        try {
            ClienteDocs.MensajeRespuesta respuesta = service.deleteclientedocs(cdCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el Documento de Cliente con código {}: {} ===>", cdCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ClienteDocs.MensajeRespuesta(500L, "Error al eliminar el Documento de Cliente: " + e.getMessage(), null));
        }
    }

    // Método auxiliar para buscar valores en el multipart
    private String findValueByName(List<Map<String, String>> multipart, String name) {
        return multipart.stream().filter(map -> name.equals(map.get("name"))).map(map -> map.get("contents"))
                .findFirst().orElse(null);
    }
}