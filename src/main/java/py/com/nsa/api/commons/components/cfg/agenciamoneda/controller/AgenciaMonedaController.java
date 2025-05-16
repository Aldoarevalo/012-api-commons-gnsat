package py.com.nsa.api.commons.components.cfg.agenciamoneda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.agenciamoneda.model.AgenciaMoneda;
import py.com.nsa.api.commons.components.cfg.agenciamoneda.service.AgenciaMonedaService;

@RestController
@RequestMapping("/agenciamoneda")
public class AgenciaMonedaController {

    private static final Logger logger = LoggerFactory.getLogger(AgenciaMonedaController.class);

    @Autowired
    private AgenciaMonedaService agenciaMonedaService;

    @GetMapping("/lista")
    public ResponseEntity<AgenciaMoneda.MensajeRespuesta> getAgenciaMonedasAll() {
        try {
            AgenciaMoneda.MensajeRespuesta respuesta = agenciaMonedaService.getAgenciaMonedasAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al obtener monedas de agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AgenciaMoneda.MensajeRespuesta(500L, "Error al obtener monedas de agencia: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<AgenciaMoneda.MensajeRespuesta> insertarAgenciaMoneda(@RequestBody AgenciaMoneda agenciaMoneda) {
        try {
            AgenciaMoneda.MensajeRespuesta respuesta = agenciaMonedaService.insertarAgenciaMoneda(agenciaMoneda);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al insertar moneda de agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AgenciaMoneda.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

   /* @PutMapping("/update")
    public ResponseEntity<AgenciaMoneda.MensajeRespuesta> updateAgenciaMoneda(@RequestBody AgenciaMoneda agenciaMoneda) {
        try {
            AgenciaMoneda.MensajeRespuesta respuesta = agenciaMonedaService.updateAgenciaMoneda(agenciaMoneda);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al actualizar moneda de agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AgenciaMoneda.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }*/

    @DeleteMapping("/delete")
    public ResponseEntity<AgenciaMoneda.MensajeRespuesta> deleteAgenciaMoneda(@RequestParam Long agCod, @RequestParam String parMoneda) {
        try {
            AgenciaMoneda.MensajeRespuesta respuesta = agenciaMonedaService.deleteAgenciaMoneda(agCod, parMoneda);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al eliminar moneda de agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AgenciaMoneda.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }
}