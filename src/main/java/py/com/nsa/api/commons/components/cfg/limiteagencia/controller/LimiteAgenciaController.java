package py.com.nsa.api.commons.components.cfg.limiteagencia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.limiteagencia.model.LimiteAgencia;
import py.com.nsa.api.commons.components.cfg.limiteagencia.service.LimiteAgenciaService;

@RestController
@RequestMapping("/limiteagencia")
public class LimiteAgenciaController {

    private static final Logger logger = LoggerFactory.getLogger(LimiteAgenciaController.class);

    @Autowired
    private LimiteAgenciaService limiteAgenciaService;

    @GetMapping("/lista")
    public ResponseEntity<LimiteAgencia.MensajeRespuesta> getLimitesAgenciaAll() {
        try {
            LimiteAgencia.MensajeRespuesta respuesta = limiteAgenciaService.getLimitesAgenciaAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al obtener límites de agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LimiteAgencia.MensajeRespuesta(500L,
                            "Error al obtener límites de agencia: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<LimiteAgencia.MensajeRespuesta> getLimitesAgenciaFiltered(@RequestBody LimiteAgencia filtro) {
        try {
            LimiteAgencia.MensajeRespuesta respuesta = limiteAgenciaService.getLimitesAgenciaFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al obtener límites de agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LimiteAgencia.MensajeRespuesta(500L,
                            "Error al obtener límites de agencia: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<LimiteAgencia.MensajeRespuesta> insertarLimiteAgencia(
            @RequestBody LimiteAgencia limiteAgencia) {
        try {
            LimiteAgencia.MensajeRespuesta respuesta = limiteAgenciaService.insertarLimiteAgencia(limiteAgencia);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al insertar límite de agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LimiteAgencia.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<LimiteAgencia.MensajeRespuesta> updateLimiteAgencia(
            @RequestBody LimiteAgencia limiteAgencia) {
        try {
            LimiteAgencia.MensajeRespuesta respuesta = limiteAgenciaService.updateLimiteAgencia(limiteAgencia);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al actualizar límite de agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LimiteAgencia.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<LimiteAgencia.MensajeRespuesta> deleteLimiteAgencia(@RequestParam Long agCod,
            @RequestParam Long tlCod) {
        try {
            LimiteAgencia.MensajeRespuesta respuesta = limiteAgenciaService.deleteLimiteAgencia(agCod, tlCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al eliminar límite de agencia: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LimiteAgencia.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }
}
