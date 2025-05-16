package py.com.nsa.api.commons.components.ref.recorrido.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.recorrido.model.Recorrido;
import py.com.nsa.api.commons.components.ref.recorrido.service.RecorridoService;

import java.util.List;

@RestController
@RequestMapping("/recorrido")
public class RecorridoController {

    @Autowired
    private RecorridoService recorridoService;

    @GetMapping("/lista")
    public ResponseEntity<Recorrido.MensajeRespuesta> getRecorridosall() {
        Recorrido.MensajeRespuesta respuesta = recorridoService.getRecorridosAll();
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
    public ResponseEntity<Recorrido.MensajeRespuesta> insertRecorrido(@Valid @RequestBody Recorrido recorrido) {
        try {
            Recorrido.MensajeRespuesta respuesta = recorridoService.insertRecorrido(recorrido);
            if ("ok".equals(respuesta.getStatus())) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Recorrido.MensajeRespuesta("error", e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Recorrido.MensajeRespuesta> updateRecorrido(@Valid @RequestBody Recorrido recorrido) {
        try {
            Recorrido.MensajeRespuesta respuesta = recorridoService.updateRecorrido(recorrido);
            if ("ok".equals(respuesta.getStatus())) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Recorrido.MensajeRespuesta("error", e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<Recorrido.MensajeRespuesta> deleteRecorrido(@PathVariable("codigo") Long codigo) {
        Recorrido.MensajeRespuesta respuesta = recorridoService.deleteRecorrido(codigo);
        if ("ok".equals(respuesta.getStatus())) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}
