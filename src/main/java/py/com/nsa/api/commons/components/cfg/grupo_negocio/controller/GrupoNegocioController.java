package py.com.nsa.api.commons.components.cfg.grupo_negocio.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.grupo_negocio.model.GrupoNegocio;
import py.com.nsa.api.commons.components.cfg.grupo_negocio.service.GrupoNegocioService;

@RestController
@RequestMapping("/grupo-negocio")
public class GrupoNegocioController {

    private static final Logger logger = LoggerFactory.getLogger(GrupoNegocioController.class);

    @Autowired
    private GrupoNegocioService grupoNegocioService;

    @GetMapping("/lista")
    public ResponseEntity<GrupoNegocio.MensajeRespuesta> getGrupoNegocioAll(
            @RequestParam(value = "keyword", required = false) String keyword) {
        try {
            GrupoNegocio.MensajeRespuesta respuesta = grupoNegocioService.getGruposNegocioAll(keyword);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            }  else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener grupos de negocio: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GrupoNegocio.MensajeRespuesta(500L, "Error al obtener grupos de negocio: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<GrupoNegocio.MensajeRespuesta> insertarGrupoNegocio(
            @Valid @RequestBody GrupoNegocio grupoNegocio) {
        try {
            GrupoNegocio.MensajeRespuesta respuesta = grupoNegocioService.insertarGrupoNegocio(grupoNegocio);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al insertar el grupo de negocio: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GrupoNegocio.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<GrupoNegocio.MensajeRespuesta> updateGrupoNegocio(
            @Valid @RequestBody GrupoNegocio grupoNegocio) {
        try {
            GrupoNegocio.MensajeRespuesta respuesta = grupoNegocioService.updateGrupoNegocio(grupoNegocio);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al actualizar el grupo de negocio con ID {}: {} ===>", grupoNegocio.getGrnCodigo(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GrupoNegocio.MensajeRespuesta(500L, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<GrupoNegocio.MensajeRespuesta> deleteGrupoNegocio(@RequestParam("grnCodigo") Long grnCodigo) {
        try {
            GrupoNegocio.MensajeRespuesta respuesta = grupoNegocioService.deleteGrupoNegocio(grnCodigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar grupo de negocio con código {}: {} ===>", grnCodigo, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GrupoNegocio.MensajeRespuesta(500L, "Error al eliminar grupo de negocio: " + e.getMessage(), null));
        }
    }
}
