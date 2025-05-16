package py.com.nsa.api.commons.components.cfg.grupo.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.grupo.model.Grupo;
import py.com.nsa.api.commons.components.cfg.grupo.service.GrupoService;

@RestController
@RequestMapping("grupo")
public class GrupoController {
    @Autowired
    private GrupoService service;

    private static final Logger logger = LoggerFactory.getLogger(GrupoController.class);

    @GetMapping("/lista")
    public ResponseEntity<Grupo.MensajeRespuesta> getGruposList() {
        try {
            Grupo.MensajeRespuesta respuesta = service.getGruposAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener grupos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Grupo.MensajeRespuesta(500L, "Error al obtener grupos: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Grupo.MensajeRespuesta> insertGrupo(@Valid @RequestBody Grupo grupo) {
        try {
            Grupo.MensajeRespuesta respuesta = service.insert(grupo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== SQL INSERTAR: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Grupo.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Grupo.MensajeRespuesta> update(@Valid @RequestBody Grupo grupo) {
        try {
            Grupo.MensajeRespuesta respuesta = service.update(grupo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar grupo: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Grupo.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<Boolean> delete(@PathVariable("codigo") Integer codigo) {
        boolean result = service.deleteById(codigo);
        return ResponseEntity.ok(result);
    }
}
