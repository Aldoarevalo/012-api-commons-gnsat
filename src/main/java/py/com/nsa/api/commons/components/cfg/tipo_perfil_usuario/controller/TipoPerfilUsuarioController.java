package py.com.nsa.api.commons.components.cfg.tipo_perfil_usuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.tipo_perfil_usuario.model.TipoPerfilUsuario;
import py.com.nsa.api.commons.components.cfg.tipo_perfil_usuario.service.TipoPerfilUsuarioService;


@RestController
@RequestMapping("/tipo-perfil-usuario")
public class TipoPerfilUsuarioController {

    @Autowired
    private TipoPerfilUsuarioService tipoPerfilUsuarioService;

    @GetMapping("/lista")
    public ResponseEntity<TipoPerfilUsuario.MensajeRespuesta> getPerfilesAll(
            @RequestParam(value = "peuCodigo", required = false) String peuCodigo) {
        TipoPerfilUsuario.MensajeRespuesta respuesta = tipoPerfilUsuarioService.getPerfilesAll(peuCodigo);

        switch (respuesta.getStatus().intValue()) {
            case 200: // Status OK
                return ResponseEntity.ok(respuesta);
            case 204: // Status INFO (No content)
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta); // No content (204) si no hay perfiles
            default: // Status ERROR
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
}

