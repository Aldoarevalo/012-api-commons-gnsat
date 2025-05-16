package py.com.nsa.api.commons.components.cfg.tipo_limite.controller;


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.tipo_limite.model.TipoLimite;
import py.com.nsa.api.commons.components.cfg.tipo_limite.service.TipoLimiteService;

import java.util.List;

@RestController
@RequestMapping("/tipo-limite")
public class TipoLimiteController {

    @Autowired
    private TipoLimiteService service;

    private static final Logger logger = LoggerFactory.getLogger(TipoLimiteController.class);

    @GetMapping("/lista")
    public ResponseEntity<TipoLimite.MensajeRespuesta> getAgenciaList() {
        try {
            TipoLimite.MensajeRespuesta respuesta = service.getAgenciasAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener tipos de IVA: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoLimite.MensajeRespuesta(500L, "Error al obtener Tipo Limite: " + e.getMessage(), null));
        }
    }
    @GetMapping("/lista-filtro")
    public ResponseEntity<TipoLimite.MensajeRespuesta> getTipoLimiteFiltered(@RequestBody TipoLimite filtro) {
        try {
            TipoLimite.MensajeRespuesta respuesta = service.getTipoLimitesFiltrados(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener Tipo Limite: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoLimite.MensajeRespuesta(500L, "Error al obtener Tipo Limite: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<TipoLimite.MensajeRespuesta> insertAgencia(@Valid @RequestBody TipoLimite limite) {
        try {
            TipoLimite.MensajeRespuesta respuesta = service.insert(limite);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar Tipo Limite: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoLimite.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TipoLimite.MensajeRespuesta> update(@Valid @RequestBody TipoLimite limite) {
        try {
            TipoLimite.MensajeRespuesta respuesta = service.update(limite);
            if (200L ==  respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar Tipo Limite: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoLimite.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<TipoLimite.MensajeRespuesta> deleteAgencia(
            @RequestParam Long tlCod) {
        try {
            TipoLimite.MensajeRespuesta respuesta = service.deleteById(tlCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el limite con código {}: {} ===>", tlCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TipoLimite.MensajeRespuesta(500L, "Error al eliminar el limite: " + e.getMessage(), null));
        }
    }

}
