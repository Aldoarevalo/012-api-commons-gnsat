package py.com.nsa.api.commons.components.cfg.cajausuario.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.cajausuario.model.CajaUsuario;
import py.com.nsa.api.commons.components.cfg.cajausuario.model.UpdateOperacionRequest;
import py.com.nsa.api.commons.components.cfg.cajausuario.service.CajaUsuarioService;

@RestController
@RequestMapping("cajausuario")
public class CajaUsuarioController {

    @Autowired
    private CajaUsuarioService service;

    private static final Logger logger = LoggerFactory.getLogger(CajaUsuarioController.class);

    @GetMapping("/lista")
    public ResponseEntity<CajaUsuario.MensajeRespuesta> getCajaUsuarioList(
            @RequestParam(required = false) Long usuario,
            @RequestParam(required = false) Long agencia) {
        try {
            CajaUsuario.MensajeRespuesta respuesta;
            if (usuario != null || agencia != null) {
                respuesta = service.getCajaUsuariosByFilter(usuario, agencia);
                if (respuesta.getCajaUsuarios() == null || respuesta.getCajaUsuarios().isEmpty()) {
                    String mensaje = String.format("No se encontraron registros para usuario: %s, agencia: %s", 
                        usuario != null ? usuario : "todos", 
                        agencia != null ? agencia : "todos");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new CajaUsuario.MensajeRespuesta(404L, mensaje, null));
                }
            } else {
                respuesta = service.getAllCajaUsuarios();
            }
            
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener caja-usuario: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CajaUsuario.MensajeRespuesta(500L, "Error al obtener caja-usuario: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<CajaUsuario.MensajeRespuesta> insertCajaUsuario(@Valid @RequestBody CajaUsuario cajaUsuario) {
        try {
            CajaUsuario.MensajeRespuesta respuesta = service.insert(cajaUsuario);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar caja-usuario: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CajaUsuario.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CajaUsuario.MensajeRespuesta> updateCajaUsuario(@Valid @RequestBody CajaUsuario cajaUsuario) {
        try {
            CajaUsuario.MensajeRespuesta respuesta = service.update(cajaUsuario);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar caja-usuario: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CajaUsuario.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<Boolean> deleteCajaUsuario(@PathVariable("codigo") Long codigo) {
        boolean result = service.deleteById(codigo);
        return ResponseEntity.ok(result);
    }



    @PutMapping("/operacion")
    public ResponseEntity<CajaUsuario.MensajeRespuesta> updateOperacion(@Valid @RequestBody UpdateOperacionRequest request) {
        try {
            CajaUsuario.MensajeRespuesta respuesta = service.updateOperacionUsuario(request);
            
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else if (404L == respuesta.getStatus()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("Error al actualizar operación de usuarios: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CajaUsuario.MensajeRespuesta(500L, 
                        "Error al actualizar operación de usuarios: " + e.getMessage(), null));
        }
    }



}
