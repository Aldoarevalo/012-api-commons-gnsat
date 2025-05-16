package py.com.nsa.api.commons.components.ref.estadocivil.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.estadocivil.model.EstadoCivil;
import py.com.nsa.api.commons.components.ref.estadocivil.service.EstadoCivilService;
import py.com.nsa.api.commons.components.ref.grupocargo.model.GrupoCargo;

@RestController
@RequestMapping("/estado-civil")
public class EstadoCivilController {

    private static final Logger logger = LoggerFactory.getLogger(EstadoCivilController.class);

    @Autowired
    private EstadoCivilService estadoCivilService;

    @GetMapping("/lista")
    public ResponseEntity<EstadoCivil.MensajeRespuesta> getEstadoCivilAll(
            @RequestParam(value = "keyword", required = false) String keyword) {
        try {
            EstadoCivil.MensajeRespuesta respuesta = estadoCivilService.getEstadosCivilesAll(keyword);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            }  else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }

        } catch (Exception e) {
            logger.error("<=== Error al obtener estados civiles: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EstadoCivil.MensajeRespuesta(500L, "Error al obtener estados civiles: " + e.getMessage(), null));
        }
    }


    @PostMapping("/insert")
    public ResponseEntity<EstadoCivil.MensajeRespuesta> insertarEstadoCivil(
            @Valid @RequestBody EstadoCivil estadoCivil) {
        try {
            EstadoCivil.MensajeRespuesta respuesta = estadoCivilService.insertarEstadoCivil(estadoCivil);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar el estado civile: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EstadoCivil.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<EstadoCivil.MensajeRespuesta> updateEstadoCivil(@Valid @RequestBody EstadoCivil estadoCivil) {
        try {
            EstadoCivil.MensajeRespuesta respuesta = estadoCivilService.updateEstadoCivil(estadoCivil);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar el estado civil con ID {}: {} ===>", estadoCivil.getEciCodigo(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EstadoCivil.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<EstadoCivil.MensajeRespuesta> deleteEstadoCivil(@RequestParam("eciCodigo") Long eciCodigo) {
        try {
            EstadoCivil.MensajeRespuesta respuesta = estadoCivilService.deleteEstadoCivil(eciCodigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar estado civil con código {}: {} ===>", eciCodigo, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EstadoCivil.MensajeRespuesta(500L, "Error al eliminar estado civil: " + e.getMessage(), null));
        }
    }
}

