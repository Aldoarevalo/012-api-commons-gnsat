package py.com.nsa.api.commons.components.cfg.perfil_usuario.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.equipo.controller.EquipoController;
import py.com.nsa.api.commons.components.cfg.equipo.model.Equipo;
import py.com.nsa.api.commons.components.cfg.perfil_usuario.model.PerfilUsuario;
import py.com.nsa.api.commons.components.cfg.perfil_usuario.service.PerfilUsuarioService;

import java.util.List;

@RestController
@RequestMapping("perfilusuario")
public class PerfilUsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(PerfilUsuarioController.class);

    @Autowired
    private PerfilUsuarioService service;

    @GetMapping("/lista")
    public List<PerfilUsuario> getList() {
        return service.getList();
    }

    //devuelve cod_perfil
    @GetMapping("/findPerfilByUsuarioId")
    public Long getPerfilByCodUsuario(@RequestParam("codUsuario") Long codUsuario){
        return service.findPerfilByUsuarioId(codUsuario);
    }


    @PostMapping("/insert")
    public ResponseEntity<PerfilUsuario.MensajeRespuesta> insertPerfilUsuario(
            @Valid @RequestBody PerfilUsuario perfilUsuario) {
        try {
            PerfilUsuario.MensajeRespuesta respuesta = service.insertarPerfilUsuario(perfilUsuario);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar el Perfil de Acceso de usuario: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PerfilUsuario.MensajeRespuesta(500L, "Error al insertar el perfil de acceso para el usaurio: " + e.getMessage(), null));
        }
    }
    @PutMapping("/update")
    public ResponseEntity<PerfilUsuario.MensajeRespuesta> updatePerfilUsuario(
            @Valid @RequestBody PerfilUsuario perfilUsuario) {
        try {
            PerfilUsuario.MensajeRespuesta respuesta = service.updatePerfilUsuario(perfilUsuario);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el Perfil de Acceso de Usuario con código {}: {} ===>", perfilUsuario.getPauCodigo(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PerfilUsuario.MensajeRespuesta(500L, "Error al actualizar el Perfil de Acceso de Usuario: " + e.getMessage(), null));
        }
    }

}
