package py.com.nsa.api.commons.components.cfg.trx_cumplimiento.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.trx_cumplimiento.model.Cumplimiento;
import py.com.nsa.api.commons.components.cfg.trx_cumplimiento.service.CumplimientoService;
import py.com.nsa.api.commons.components.ref.pdoc.model.PDoc;


@RestController
@RequestMapping("cumplimiento")
public class CumplimientoController {

    @Autowired
    private CumplimientoService service;

    private static final Logger logger = LoggerFactory.getLogger(CumplimientoController.class);

    @GetMapping("/lista")
    public ResponseEntity<Cumplimiento.MensajeRespuesta> getCumplimientosList() {
        try {
            Cumplimiento.MensajeRespuesta respuesta = service.getCumplimientosAll();
            if (200L == respuesta.getStatus()) {
                logger.info("<=== cumplimientos obtenido: {} ===>", respuesta);
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener cumplimientos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cumplimiento.MensajeRespuesta(500L, "Error al obtener cumplimientos: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<Cumplimiento.MensajeRespuesta> getCumplimientosFiltered(@RequestBody Cumplimiento filtro) {
        try {
            Cumplimiento.MensajeRespuesta respuesta = service.getCumplimientosFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener cumplimientos filtrados: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cumplimiento.MensajeRespuesta(500L, "Error al obtener cumplimientos filtrados: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Cumplimiento.MensajeRespuesta> insertCumplimiento(@Valid @RequestBody Cumplimiento cumplimiento) {
        try {
            Cumplimiento.MensajeRespuesta respuesta = service.insert(cumplimiento);
            if (200L == respuesta.getStatus()) {
                logger.info("<=== cumplimientos Insertado: {} ===>", respuesta);
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== SQL INSERTAR: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cumplimiento.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Cumplimiento.MensajeRespuesta> update(@Valid @RequestBody Cumplimiento cumplimiento) {
        try {
            Cumplimiento.MensajeRespuesta respuesta = service.update(cumplimiento);
            if (200L == respuesta.getStatus()) {
                logger.info("<=== cumplimientos actualizado: {} ===>", respuesta);
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar cumplimiento: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cumplimiento.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Cumplimiento.MensajeRespuesta> deletecumplimiento(@RequestParam("cuCod") Long cuCod) {
        try {
            Cumplimiento.MensajeRespuesta respuesta = service.deletecumplimiento(cuCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el Cumplimiento con código {}: {} ===>", cuCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Cumplimiento.MensajeRespuesta(500L, "Error al eliminar el Cumplimiento: " + e.getMessage(), null));
        }
    }
}
