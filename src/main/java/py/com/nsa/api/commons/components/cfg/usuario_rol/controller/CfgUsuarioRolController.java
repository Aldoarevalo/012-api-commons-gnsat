package py.com.nsa.api.commons.components.cfg.usuario_rol.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.rol_permiso.model.RolPermiso;
import py.com.nsa.api.commons.components.cfg.usuario_rol.model.CfgUsuarioRol;
import py.com.nsa.api.commons.components.cfg.usuario_rol.service.CfgUsuarioRolService;

import java.util.List;

@RestController
@RequestMapping("/usuario-rol")
public class CfgUsuarioRolController {

    private static final Logger logger = LoggerFactory.getLogger(CfgUsuarioRolController.class);

    @Autowired
    private CfgUsuarioRolService usuarioRolService;

    @GetMapping("/lista")
    public ResponseEntity<CfgUsuarioRol.MensajeRespuesta> getAllUsuariosRol() {
        try {
            CfgUsuarioRol.MensajeRespuesta respuesta = usuarioRolService.getUsuariosRolAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al obtener los registros de usuario-rol: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CfgUsuarioRol.MensajeRespuesta(500L, "Error al obtener los registros de usuario-rol: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<CfgUsuarioRol.MensajeRespuesta> insertUsuarioRol(@Valid @RequestBody CfgUsuarioRol usuarioRol) {
        try {
            CfgUsuarioRol.MensajeRespuesta respuesta = usuarioRolService.insertarUsuarioRol(usuarioRol);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al insertar el registro de usuario-rol: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CfgUsuarioRol.MensajeRespuesta(500L, "Error al insertar el registro de usuario-rol: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<CfgUsuarioRol.MensajeRespuesta> updateUsuarioRol(@Valid @RequestBody CfgUsuarioRol usuarioRol) {
        try {
            CfgUsuarioRol.MensajeRespuesta respuesta = usuarioRolService.updateUsuarioRol(usuarioRol);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al actualizar el registro de usuario-rol con ID {}: {}", usuarioRol.getUsuRolCod(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CfgUsuarioRol.MensajeRespuesta(500L, "Error al actualizar el registro de usuario-rol: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CfgUsuarioRol.MensajeRespuesta> deleteUsuarioRol(@RequestParam("usuRolCod") Long usuRolCod) {
        try {
            CfgUsuarioRol.MensajeRespuesta respuesta = usuarioRolService.deleteUsuarioRol(usuRolCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al eliminar el registro de usuario-rol con ID {}: {}", usuRolCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CfgUsuarioRol.MensajeRespuesta(500L, "Error al eliminar el registro de usuario-rol: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/deletebyusuario")
    public ResponseEntity<CfgUsuarioRol.MensajeRespuesta> deleteByUsuario(@RequestParam("usuCod") Long usuCod) {
        try {
            CfgUsuarioRol.MensajeRespuesta respuesta = usuarioRolService.deleteByUsuCod(usuCod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al eliminar el registro de usuario-rol con ID {}: {}", usuCod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CfgUsuarioRol.MensajeRespuesta(500L, "Error al eliminar el registro de usuario-rol: " + e.getMessage(), null));
        }
    }


    @GetMapping("/byUser/{usuarioCodigo}")
    public List<CfgUsuarioRol> getRolesByUsuariol(@PathVariable("usuarioCodigo") Long usuarioCodigo) {
        return usuarioRolService.findByCodigoUsuario(usuarioCodigo);
    }

    //TODO. listamos usuarios distintos, con lista de roles por cada uno
    @GetMapping("/lista-usuarios-roles")
    public List<Object[]> getUsuariosConRole() {
        try {
            List<Object[]> respuesta = usuarioRolService.findAllUsuariosConRoles();
            return respuesta;
        } catch (RuntimeException e) {
            logger.error("Error al obtener los registros de usuario-rol: {}", e.getMessage(), e);
            return null;
        }
    }

    //copia de RolPermisoController. function byRol
    @GetMapping("/byUsuario/{userCodigo}")
    public List<CfgUsuarioRol> getPermisosByRol(@PathVariable("userCodigo") Long userCodigo) {
        return usuarioRolService.findByUser(userCodigo);
    }
}