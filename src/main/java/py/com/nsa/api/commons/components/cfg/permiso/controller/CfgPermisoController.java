package py.com.nsa.api.commons.components.cfg.permiso.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.permiso.model.CfgPermiso;
import py.com.nsa.api.commons.components.cfg.permiso.service.CfgPermisoService;

@RestController
@RequestMapping("permisos")
public class CfgPermisoController {
    @Autowired
    private CfgPermisoService service;

    private static final Logger logger = LoggerFactory.getLogger(CfgPermisoController.class);

    @GetMapping("/lista")
    public ResponseEntity<CfgPermiso.MensajeRespuesta> getList() {
        try {
            CfgPermiso.MensajeRespuesta respuesta = service.getPermisosAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener permisos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CfgPermiso.MensajeRespuesta(500L, "Error al obtener permisos: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<CfgPermiso.MensajeRespuesta> insert(@Valid @RequestBody CfgPermiso permiso) {
        try {
            CfgPermiso.MensajeRespuesta respuesta = service.insert(permiso);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar permiso: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CfgPermiso.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CfgPermiso.MensajeRespuesta> update(@Valid @RequestBody CfgPermiso permiso) {
        try {
            CfgPermiso.MensajeRespuesta respuesta = service.update(permiso);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar permiso: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CfgPermiso.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CfgPermiso.MensajeRespuesta> deletePersona(@RequestParam("perCodigo") Long perCodigo) {
        try {
            CfgPermiso.MensajeRespuesta respuesta = service.deletePermiso(perCodigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al eliminar el permiso con ID {}: {} ===>", perCodigo, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CfgPermiso.MensajeRespuesta(500L, "Error al eliminar el permiso: " + e.getMessage(),
                            null));
        }
    }

}
