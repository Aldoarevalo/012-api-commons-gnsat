package py.com.nsa.api.commons.components.cfg.cliente_limite.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.cliente_limite.model.ClienteLimite;
import py.com.nsa.api.commons.components.cfg.cliente_limite.service.ClienteLimiteService;
import py.com.nsa.api.commons.components.cfg.limiteagencia.model.LimiteAgencia;

@RestController
@RequestMapping("cliente-limite")
public class ClienteLimiteController {

    @Autowired
    private ClienteLimiteService service;

    private static final Logger logger = LoggerFactory.getLogger(ClienteLimiteController.class);

    @GetMapping("/lista")
    public ResponseEntity<ClienteLimite.MensajeRespuesta> getList() {
        try {
            ClienteLimite.MensajeRespuesta respuesta = service.getList();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener la lista de límites de cliente: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ClienteLimite.MensajeRespuesta(500L, "Error al obtener la lista de límites de cliente: " + e.getMessage(), null));
        }
    }

    // In ClienteLimiteController.java
    @PostMapping("/lista-filtro")
    public ResponseEntity<ClienteLimite.MensajeRespuesta> getClientesLimitesFiltrados(@RequestBody ClienteLimite filtro) {
        try {
            ClienteLimite.MensajeRespuesta respuesta = service.getClientesLimitesFiltrados(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener clientes límites filtrados: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ClienteLimite.MensajeRespuesta(500L, "Error al obtener clientes límites filtrados: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<ClienteLimite.MensajeRespuesta> insert(@Valid @RequestBody ClienteLimite clienteLimite) {
        try {
            ClienteLimite.MensajeRespuesta respuesta = service.save(clienteLimite);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar límite de cliente: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ClienteLimite.MensajeRespuesta(500L, "Error al insertar límite de cliente: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ClienteLimite.MensajeRespuesta> update(@Valid @RequestBody ClienteLimite clienteLimite) {
        try {
            ClienteLimite.MensajeRespuesta respuesta = service.update(clienteLimite);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar límite de cliente: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ClienteLimite.MensajeRespuesta(500L, "Error al actualizar límite de cliente: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ClienteLimite.MensajeRespuesta> deleteClientelimite(@RequestParam Long ccodCliente,
                                                                              @RequestParam Long tlCod) {
        try {
            ClienteLimite.MensajeRespuesta respuesta = service.delete(ccodCliente, tlCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al eliminar límite del Cliente: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ClienteLimite.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }
}