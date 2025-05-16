package py.com.nsa.api.commons.components.cfg.usuarioagencia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.cfg.usuarioagencia.model.UsuarioAgencia;
import py.com.nsa.api.commons.components.cfg.usuarioagencia.service.UsuarioAgenciaService;

@RestController
@RequestMapping("/usuarioagencia")
public class UsuarioAgenciaController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioAgenciaController.class);

    @Autowired
    private UsuarioAgenciaService usuarioAgenciaService;

    @GetMapping("/lista")
    public ResponseEntity<UsuarioAgencia.MensajeRespuesta> getAgenciasUsuariosAll() {
        try {
            UsuarioAgencia.MensajeRespuesta respuesta = usuarioAgenciaService.getUsuarioAgenciasAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los registros usuario-agencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UsuarioAgencia.MensajeRespuesta(500L, "Error al obtener registros: " + e.getMessage(),
                            null));
        }
    }

    @GetMapping("/lista-agencia-usuario")
    public ResponseEntity<Agencia.MensajeRespuesta> getUsuarioAgenciasAll(@RequestParam(required = false) Long usuCod) {
        try {
            Agencia.MensajeRespuesta respuesta = usuarioAgenciaService.getAgenciasUsuariosAll(usuCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los registros usuario-agencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Agencia.MensajeRespuesta(500L, "Error al obtener registros: " + e.getMessage(),
                            null));
        }
    }

    @GetMapping("/lista-filtro")
    public ResponseEntity<UsuarioAgencia.MensajeRespuesta> getAgenciaFiltered(@RequestBody UsuarioAgencia filtro) {
        try {
            UsuarioAgencia.MensajeRespuesta respuesta = usuarioAgenciaService.getUsuarioAgeFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener Usuario agencias: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UsuarioAgencia.MensajeRespuesta(500L,
                            "Error al obtener Usuario agencias: " + e.getMessage(), null));
        }
    }

    @PostMapping(value = "/insert", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioAgencia.MensajeRespuesta> insertarUsuarioAgencia(
            @RequestBody UsuarioAgencia usuarioAgencia) {
        try {
            UsuarioAgencia.MensajeRespuesta respuesta = usuarioAgenciaService.insertarUsuarioAgencia(usuarioAgencia);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el registro usuario-agencia: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UsuarioAgencia.MensajeRespuesta(500L, "Error al insertar registro: " + e.getMessage(),
                            null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<UsuarioAgencia.MensajeRespuesta> updateUsuarioAgencia(
            @RequestBody UsuarioAgencia usuarioAgencia) {
        try {
            UsuarioAgencia.MensajeRespuesta respuesta = usuarioAgenciaService.updateUsuarioAgencia(usuarioAgencia);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el registro usuario-agencia con ID {}: {} ===>",
                    usuarioAgencia.getUsuAgeCod(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UsuarioAgencia.MensajeRespuesta(500L,
                            "Error al actualizar el registro: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<UsuarioAgencia.MensajeRespuesta> deleteUsuarioAgencia(
            @RequestParam("usuAgeCod") Long usuAgeCod) {
        try {
            UsuarioAgencia.MensajeRespuesta respuesta = usuarioAgenciaService.deleteUsuarioAgencia(usuAgeCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el registro usuario-agencia con código {}: {} ===>", usuAgeCod,
                    e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UsuarioAgencia.MensajeRespuesta(500L, "Error al eliminar el registro: " + e.getMessage(),
                            null));
        }
    }
}
