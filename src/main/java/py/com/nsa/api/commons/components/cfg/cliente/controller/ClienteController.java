package py.com.nsa.api.commons.components.cfg.cliente.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.cliente.model.Cliente;
import py.com.nsa.api.commons.components.cfg.cliente.service.ClienteService;
import py.com.nsa.api.commons.components.ref.pdoc.repository.PDocRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Autowired
    private PDocRepository pDocRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @GetMapping("/lista")
    public ResponseEntity<Cliente.MensajeRespuesta> getList() {
        try {
            Cliente.MensajeRespuesta respuesta = service.getList();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener la lista de clientes: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Cliente.MensajeRespuesta(500L, "Error al obtener la lista de clientes: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<Map<String, Object>> getClientesFiltrados(@RequestBody Cliente filtro) {
        try {
            Cliente.MensajeRespuesta respuesta = service.getClientesFiltrados(filtro);
            Map<String, Object> responseMap = new HashMap<>();

            if (200L == respuesta.getStatus() && respuesta.getClientes() != null) {
                List<Map<String, Object>> clientesConDocumentos = new ArrayList<>();

                for (Cliente cliente : respuesta.getClientes()) {
                    Map<String, Object> clienteInfo = new HashMap<>();
                    clienteInfo.put("cliente", cliente);

                    // Obtener documentos para el cliente usando pcod
                    List<Object[]> documentos = pDocRepository.findConfTDocsWithPDoc(cliente.getPCod());
                    List<Map<String, Object>> docsFormateados = new ArrayList<>();

                    for (Object[] doc : documentos) {
                        Map<String, Object> docInfo = new HashMap<>();
                        docInfo.put("pdoc_codigo", doc[0]);
                        docInfo.put("pdoc_nro_doc", doc[1]);
                        docInfo.put("p_cod", doc[2]);
                        docInfo.put("p_vencimiento", doc[3]);
                        docInfo.put("conftdoc_codigo", doc[4]);
                        docInfo.put("conftdoc_nombre", doc[5]);
                        docInfo.put("conftdoc_descripcion", doc[6]);
                        docInfo.put("conftdoc_pa_cod", doc[7]);
                        docInfo.put("pais_padescripcion", doc[8]);
                        docsFormateados.add(docInfo);
                    }

                    clienteInfo.put("documentos", docsFormateados);
                    clientesConDocumentos.add(clienteInfo);
                }

                responseMap.put("status", respuesta.getStatus());
                responseMap.put("mensaje", respuesta.getMensaje());
                responseMap.put("clientes", clientesConDocumentos);
                return ResponseEntity.ok(responseMap);
            } else {
                responseMap.put("status", respuesta.getStatus());
                responseMap.put("mensaje", respuesta.getMensaje());
                responseMap.put("data", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener clientes filtrados: {} ===>", e.getMessage(), e);
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("status", 500L);
            errorMap.put("mensaje", "Error al obtener clientes filtrados: " + e.getMessage());
            errorMap.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Cliente.MensajeRespuesta> insert(@Valid @RequestBody Cliente cliente) {
        logger.info("Cliente recibido: " + cliente);
        try {
            Cliente.MensajeRespuesta respuesta = service.save(cliente);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar cliente: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Cliente.MensajeRespuesta(500L, "Error al insertar cliente: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Cliente.MensajeRespuesta> update(@Valid @RequestBody Cliente cliente) {
        try {
            Cliente.MensajeRespuesta respuesta = service.update(cliente);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar cliente: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Cliente.MensajeRespuesta(500L, "Error al actualizar cliente: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Cliente.MensajeRespuesta> deleteCliente(@RequestParam("ccod") Long ccod) {
        try {
            Cliente.MensajeRespuesta respuesta = service.deleteById(ccod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al eliminar el cliente con ID {}: {} ===>", ccod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Cliente.MensajeRespuesta(500L, "Error al eliminar el cliente: " + e.getMessage(), null));
        }
    }
}