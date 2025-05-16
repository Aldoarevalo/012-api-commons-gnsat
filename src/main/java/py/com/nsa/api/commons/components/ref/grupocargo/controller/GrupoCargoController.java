package py.com.nsa.api.commons.components.ref.grupocargo.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.grupocargo.model.GrupoCargo;
import py.com.nsa.api.commons.components.ref.grupocargo.service.GrupoCargoService;

@RestController
@RequestMapping("/grupo-cargo")
public class GrupoCargoController {
    private static final Logger logger = LoggerFactory.getLogger(GrupoCargoController.class);
    @Autowired
    private GrupoCargoService grupoCargoService;

    @GetMapping("/lista")
    public ResponseEntity<GrupoCargo.MensajeRespuesta> getGruposCargosAll(
            @RequestParam(value = "keyword", required = false) String keyword) {
        try {
            GrupoCargo.MensajeRespuesta respuesta = grupoCargoService.getGruposCargosAll(keyword);

            // Si la respuesta es 200 (datos o mensaje indicando que no hay grupos)
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            }  else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los grupos cargos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GrupoCargo.MensajeRespuesta(500L, "Error al obtener grupos de cargos: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<GrupoCargo.MensajeRespuesta> insertarGrupoCargo(
            @Valid @RequestBody GrupoCargo grupoCargo) {
        try {
            GrupoCargo.MensajeRespuesta respuesta = grupoCargoService.insertarGrupoCargo(grupoCargo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al insertar los grupos cargos: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GrupoCargo.MensajeRespuesta(500L, "Error al insertar el grupo de cargo: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<GrupoCargo.MensajeRespuesta> updateGrupoCargo(
            @Valid @RequestBody GrupoCargo grupoCargo) {
        try {
            GrupoCargo.MensajeRespuesta respuesta = grupoCargoService.updateGrupoCargo(grupoCargo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al actualizar el grupo cargo con ID {}: {} ===>", grupoCargo.getGcaCodigo(), e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GrupoCargo.MensajeRespuesta(500L, "Error al actualizar el grupo de cargo: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<GrupoCargo.MensajeRespuesta> deleteGrupoCargo(
            @RequestParam("gcaCodigo") Integer gcaCodigo) {
        try {
            GrupoCargo.MensajeRespuesta respuesta = grupoCargoService.deleteGrupoCargo(gcaCodigo);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (Exception e) {
            logger.error("<=== Error al eliminar el grupo cargo con código {}: {} ===>", gcaCodigo, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GrupoCargo.MensajeRespuesta(500L, "Error al eliminar el grupo de cargo: " + e.getMessage(), null));
        }
    }
}
