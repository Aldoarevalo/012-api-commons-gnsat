package py.com.nsa.api.commons.components.ref.tipotarifa.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.tipotarifa.model.TipoTarifa;
import py.com.nsa.api.commons.components.ref.tipotarifa.service.TipoTarifaService;

@RestController
@RequestMapping("/tipotarifa")
public class TipoTarifaController {

    @Autowired
    private TipoTarifaService tipotarifaService;

    @GetMapping("/lista")
    public ResponseEntity<TipoTarifa.MensajeRespuesta> getTipoTarifaAll() {
        TipoTarifa.MensajeRespuesta respuesta = tipotarifaService.getTipoTarifaAll();
        switch (respuesta.getStatus()) {
            case "ok":
                return ResponseEntity.ok(respuesta);
            case "info":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta); // No content (204) si no hay tipos
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<TipoTarifa.MensajeRespuesta> insertTipoTarifa(@Valid @RequestBody TipoTarifa tipotarifa) {
        TipoTarifa.MensajeRespuesta respuesta = tipotarifaService.insertTipoTarifa(tipotarifa);
        switch (respuesta.getStatus()) {
            case "ok":
                return ResponseEntity.ok(respuesta);
            case "error":
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new TipoTarifa.MensajeRespuesta("error", "Error desconocido", null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TipoTarifa.MensajeRespuesta> updateTipoTarifa(@Valid @RequestBody TipoTarifa tipotarifa) {
        TipoTarifa.MensajeRespuesta respuesta = tipotarifaService.updateTipoTarifa(tipotarifa);
        switch (respuesta.getStatus()) {
            case "ok":
                return ResponseEntity.ok(respuesta);
            case "error":
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new TipoTarifa.MensajeRespuesta("error", "Error desconocido", null));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<TipoTarifa.MensajeRespuesta> deleteTipoTarifa(@PathVariable("codigo") Long codigo) {
        TipoTarifa.MensajeRespuesta respuesta = tipotarifaService.deleteTipoTarifa(codigo);
        if ("ok".equals(respuesta.getStatus())) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}
