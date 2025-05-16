package py.com.nsa.api.commons.components.cfg.asignacionserie.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.asignacionserie.model.AsignacionSerie;
import py.com.nsa.api.commons.components.cfg.asignacionserie.model.clavecompuesta.AsignacionSerieId;
import py.com.nsa.api.commons.components.cfg.asignacionserie.service.AsignacionSerieService;

@RestController
@RequestMapping("/asignacion-serie")
public class AsignacionSerieController {

    private static final Logger logger = LoggerFactory.getLogger(AsignacionSerieController.class);

    @Autowired
    private AsignacionSerieService service;

    @GetMapping("/lista")
    public ResponseEntity<AsignacionSerie.MensajeRespuesta> getAsignacionesAll() {
        try {
            AsignacionSerie.MensajeRespuesta respuesta = service.getAsignacionesAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener asignaciones: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AsignacionSerie.MensajeRespuesta(500L, "Error al obtener asignaciones: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<AsignacionSerie.MensajeRespuesta> getAsignacionesFiltered(
            @RequestBody AsignacionSerieId filtro) {
        try {
            AsignacionSerie.MensajeRespuesta respuesta = service.getAsignacionesFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener asignaciones filtradas: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AsignacionSerie.MensajeRespuesta(500L,
                            "Error al obtener asignaciones filtradas: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<AsignacionSerie.MensajeRespuesta> insertarAsignacion(
            @Valid @RequestBody AsignacionSerie asignacion) {
        try {
            AsignacionSerie.MensajeRespuesta respuesta = service.insertarAsignacion(asignacion);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar asignación: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AsignacionSerie.MensajeRespuesta(500L,
                            "Error al insertar asignación: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<AsignacionSerie.MensajeRespuesta> deleteAsignacion(
            @RequestParam("agCod") Long agCod,
            @RequestParam("sCod") String sCod,
            @RequestParam("asUsuario") Long asUsuario,
            @RequestParam("asTDoc") String asTDoc) {
        try {
            // Crear el ID compuesto con los parámetros recibidos
            AsignacionSerieId id = new AsignacionSerieId(agCod, sCod, asUsuario, asTDoc);

            AsignacionSerie.MensajeRespuesta respuesta = service.deleteAsignacion(id);

            if (respuesta.getStatus() == 200L) {
                return ResponseEntity.ok(respuesta);
            } else if (respuesta.getStatus() == 204L) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else if (respuesta.getStatus() == 409L) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar asignación: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AsignacionSerie.MensajeRespuesta(500L,
                            "Error al eliminar asignación: " + e.getMessage(), null));
        }
    }


}