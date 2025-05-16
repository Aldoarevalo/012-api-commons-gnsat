package py.com.nsa.api.commons.components.ref.permiso.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.permiso.model.Permiso;
import py.com.nsa.api.commons.components.ref.permiso.service.PermisoService;

@RestController
@RequestMapping("/permiso")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @GetMapping("/lista")
    public ResponseEntity<Permiso.MensajeRespuesta> getPermisosall() {
        Permiso.MensajeRespuesta respuesta = permisoService.getPermisosall();
        switch (respuesta.getStatus()) {
            case "ok":
                return ResponseEntity.ok(respuesta);
            case "info":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta); // No content (204) si no hay
                                                                                     // permisos
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Permiso.MensajeRespuesta> insertPermiso(@Valid @RequestBody Permiso permiso) {
        try {
            Permiso.MensajeRespuesta respuesta = permisoService.insertPermiso(permiso);
            if ("ok".equals(respuesta.getStatus())) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Permiso.MensajeRespuesta("error", e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Permiso.MensajeRespuesta> updatePermiso(@Valid @RequestBody Permiso permiso) {
        try {
            Permiso.MensajeRespuesta respuesta = permisoService.updatePermiso(permiso);
            if ("ok".equals(respuesta.getStatus())) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Permiso.MensajeRespuesta("error", e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<Permiso.MensajeRespuesta> deletePermiso(@PathVariable("codigo") Long codigo) {
        Permiso.MensajeRespuesta respuesta = permisoService.deletePermiso(codigo);
        if ("ok".equals(respuesta.getStatus())) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}
