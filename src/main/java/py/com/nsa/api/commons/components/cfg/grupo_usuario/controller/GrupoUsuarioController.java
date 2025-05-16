// Controlador (GrupoUsuarioController.java)
package py.com.nsa.api.commons.components.cfg.grupo_usuario.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.grupo_usuario.model.GrupoUsuario;
import py.com.nsa.api.commons.components.cfg.grupo_usuario.service.GrupoUsuarioService;

@RestController
@RequestMapping("/grupo-usuario")
public class GrupoUsuarioController {
    private static final Logger logger = LoggerFactory.getLogger(GrupoUsuarioController.class);

    @Autowired
    private GrupoUsuarioService grupoUsuarioService;

    @GetMapping("/lista")
    public ResponseEntity<GrupoUsuario.MensajeRespuesta> getGruposUsuariosAll() {
        try {
            GrupoUsuario.MensajeRespuesta respuesta = grupoUsuarioService.getGruposUsuariosAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener las asignaciones: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GrupoUsuario.MensajeRespuesta(500L, "Error al obtener asignaciones: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<GrupoUsuario.MensajeRespuesta> insertarGrupoUsuario(
            @Valid @RequestBody GrupoUsuario grupoUsuario) {
        try {
            GrupoUsuario.MensajeRespuesta respuesta = grupoUsuarioService.insertarGrupoUsuario(grupoUsuario);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar la asignación: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GrupoUsuario.MensajeRespuesta(500L, "Error al insertar la asignación: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<GrupoUsuario.MensajeRespuesta> updateGrupoUsuario(
            @Valid @RequestBody GrupoUsuario grupoUsuario) {
        try {
            GrupoUsuario.MensajeRespuesta respuesta = grupoUsuarioService.updateGrupoUsuario(grupoUsuario);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar la asignación con ID {}: {} ===>", grupoUsuario.getUsuGruCod(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GrupoUsuario.MensajeRespuesta(500L, "Error al actualizar la asignación: " + e.getMessage(), null));
        }
    }


    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<GrupoUsuario.MensajeRespuesta> delete(@PathVariable("codigo") Integer codigo) {
        try {
            GrupoUsuario.MensajeRespuesta respuesta = grupoUsuarioService.deleteGrupoUsuario(codigo);

            switch (respuesta.getStatus().intValue()) {
                case 200:
                    return ResponseEntity.ok(respuesta);
                case 204:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(respuesta);
                default:
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(respuesta);
            }

        } catch (Exception e) {
            logger.error("<=== Error al eliminar la asignación con código {}: {} ===>", codigo, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GrupoUsuario.MensajeRespuesta(
                            500L,
                            "Error al eliminar la asignación: " + e.getMessage(),
                            null
                    ));
        }
    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<GrupoUsuario.MensajeRespuesta> deleteGrupoUsuario(
//            @RequestParam("usuGruCod") Integer usuGruCod) {
//        try {
//            GrupoUsuario.MensajeRespuesta respuesta = grupoUsuarioService.deleteGrupoUsuario(usuGruCod);
//            if (200L == respuesta.getStatus()) {
//                return ResponseEntity.ok(respuesta);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
//            }
//        } catch (Exception e) {
//            logger.error("<=== Error al eliminar la asignación con código {}: {} ===>", usuGruCod, e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new GrupoUsuario.MensajeRespuesta(500L, "Error al eliminar la asignación: " + e.getMessage(), null));
//        }
//    }
}
