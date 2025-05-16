package py.com.nsa.api.commons.components.cfg.equipo.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.equipo.model.Equipo;
import py.com.nsa.api.commons.components.cfg.equipo.service.EquipoService;

@RestController
@RequestMapping("/equipo")
public class EquipoController {
    private static final Logger logger = LoggerFactory.getLogger(EquipoController.class);
    @Autowired
    private EquipoService equipoService;

    @GetMapping("/lista")
    public ResponseEntity<Equipo.MensajeRespuesta> getEquiposAll(
            @RequestParam(value = "ageCodigo", required = false) String ageCodigo) {
        try {
            Equipo.MensajeRespuesta respuesta = equipoService.getEquiposAll(ageCodigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            }  else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los equipos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Equipo.MensajeRespuesta(500L, "Error al obtener equipos: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Equipo.MensajeRespuesta> insertarEquipo(
            @Valid @RequestBody Equipo equipo) {
        try {
            Equipo.MensajeRespuesta respuesta = equipoService.insertarEquipo(equipo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el equipo: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Equipo.MensajeRespuesta(500L, "Error al insertar el equipo: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Equipo.MensajeRespuesta> updateEquipo(
            @Valid @RequestBody Equipo equipo) {
        try {
            Equipo.MensajeRespuesta respuesta = equipoService.updateEquipo(equipo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el equipo con código {}: {} ===>", equipo.getEquCodigo(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Equipo.MensajeRespuesta(500L, "Error al actualizar el equipo: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Equipo.MensajeRespuesta> deleteEquipo(
            @RequestParam("equCodigo") Long equCodigo) {
        try {
            Equipo.MensajeRespuesta respuesta = equipoService.deleteEquipo(equCodigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el equipo con código {}: {} ===>", equCodigo, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Equipo.MensajeRespuesta(500L, "Error al eliminar el equipo: " + e.getMessage(), null));
        }
    }
}
