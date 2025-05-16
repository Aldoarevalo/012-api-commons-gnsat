package py.com.nsa.api.commons.components.ref.moneda.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.moneda.model.Moneda;
import py.com.nsa.api.commons.components.ref.moneda.service.MonedaService;

@RestController
@RequestMapping("/moneda")
public class MonedaController {

    @Autowired
    private MonedaService monedaService;

    @GetMapping("/lista")
    public ResponseEntity<Moneda.MensajeRespuesta> getMonedasall() {
        Moneda.MensajeRespuesta respuesta = monedaService.getMonedasall();
        switch (respuesta.getStatus()) {
            case "ok":
                return ResponseEntity.ok(respuesta);
            case "info":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Moneda.MensajeRespuesta> insertMoneda(@Valid @RequestBody Moneda moneda) {
        try {
            Moneda.MensajeRespuesta respuesta = monedaService.insertMoneda(moneda);
            if ("ok".equals(respuesta.getStatus())) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Moneda.MensajeRespuesta("error", e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Moneda.MensajeRespuesta> updateMoneda(@Valid @RequestBody Moneda moneda) {
        try {
            Moneda.MensajeRespuesta respuesta = monedaService.updateMoneda(moneda);
            if ("ok".equals(respuesta.getStatus())) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Moneda.MensajeRespuesta("error", e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<Moneda.MensajeRespuesta> deleteMoneda(@PathVariable("codigo") Long codigo) {
        Moneda.MensajeRespuesta response = monedaService.deleteMoneda(codigo);
        if ("ok".equals(response.getStatus())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
