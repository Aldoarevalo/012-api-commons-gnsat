package py.com.nsa.api.commons.components.cfg.usuario.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.cfg.usuario.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/lista")
    public ResponseEntity<Usuario.MensajeRespuesta> getUsuariosAll(@RequestParam(required = false) Long usuCod) {
        try {
            Usuario.MensajeRespuesta respuesta = usuarioService.getUsuariosAll(usuCod);
            if (200L == respuesta.getStatus()) {
                // Imprimir los parámetros recibidos
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener usuarios: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Usuario.MensajeRespuesta(500L, "Error al obtener usuarios: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<Usuario.MensajeRespuesta> getUsuarioFiltered(@Valid @RequestBody Usuario filtro) {
        try {
            Usuario.MensajeRespuesta respuesta = usuarioService.getUsuarioFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener usuarios: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Usuario.MensajeRespuesta(500L, "Error al obtener usuarios: " + e.getMessage(), null));
        }
    }

    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuario.MensajeRespuesta> insertarUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario.MensajeRespuesta respuesta = usuarioService.insertarUsuario(usuario);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar el usuario: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Usuario.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Usuario.MensajeRespuesta> updateUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            Usuario.MensajeRespuesta respuesta = usuarioService.updateUsuario(usuario);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar el usuario con código {}: {} ===>", usuario.getUsuCod(),
                    e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Usuario.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping(value = "/acciones")
    public ResponseEntity<Usuario.MensajeRespuesta> acciones(@Valid @RequestBody Usuario usuario) {
        // logger.info("Datos recibidos: {}", usuario); // Verifica los datos
        // deserializados
        try {
            Usuario.MensajeRespuesta respuesta = usuarioService.acciones(usuario);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar el usuario con código {}: {} ===>", usuario.getUsuCod(),
                    e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Usuario.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    /*
     * @DeleteMapping("/delete")
     * public ResponseEntity<Usuario.MensajeRespuesta>
     * deleteUsuario(@RequestParam("usuCod") Long usuCod) {
     * try {
     * Usuario.MensajeRespuesta respuesta = usuarioService.deleteUsuario(usuCod);
     * if (200L == respuesta.getStatus()) {
     * return ResponseEntity.ok(respuesta);
     * } else {
     * return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
     * }
     * } catch (Exception e) {
     * logger.error("<=== Error al eliminar usuario con código {}: {} ===>", usuCod,
     * e.getMessage(), e);
     * return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
     * .body(new Usuario.MensajeRespuesta(500L, "Error al eliminar usuario: " +
     * e.getMessage(), null));
     * }
     * }
     */
}
