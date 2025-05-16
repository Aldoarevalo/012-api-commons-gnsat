package py.com.nsa.api.commons.components.cfg.rol.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.rol.model.Rol;
import py.com.nsa.api.commons.components.cfg.rol.service.RolService;

@RestController
@RequestMapping("rol")
public class RolController {
    @Autowired
    private RolService service;

    private static final Logger logger = LoggerFactory.getLogger(RolController.class);

    @GetMapping("/lista")
    public ResponseEntity<Rol.MensajeRespuesta> getRolesList() {
        try {
            Rol.MensajeRespuesta respuesta = service.getRolesAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener roles: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Rol.MensajeRespuesta(500L, "Error al obtener roles: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Rol.MensajeRespuesta> insertAgencia(@Valid @RequestBody Rol rol) {
        try {
            Rol.MensajeRespuesta respuesta = service.insert(rol);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== SQL INSERTAR: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Rol.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Rol.MensajeRespuesta> update(@Valid @RequestBody Rol agencia) {
        try {
            Rol.MensajeRespuesta respuesta = service.update(agencia);
            if (200L ==  respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar rol: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Rol.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<Boolean> delete(
            @PathVariable("codigo") Long codigo) {
        boolean result = service.deleteById(codigo);
        return ResponseEntity.ok(result);
    }

}
