package py.com.nsa.api.commons.components.ref.estadotarea.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.estadotarea.model.EstadoTarea;
import py.com.nsa.api.commons.components.ref.estadotarea.service.EstadoTareaService;

@RestController
@RequestMapping("/estadotarea")
public class EstadoTareaController {

    @Autowired
    private EstadoTareaService estadotareaService;

    @GetMapping("/lista")
    public ResponseEntity<EstadoTarea.MensajeRespuesta> getTareaAll() {
        EstadoTarea.MensajeRespuesta respuesta = estadotareaService.getTareaAll();
        switch (respuesta.getStatus()) {
            case "ok":
                return ResponseEntity.ok(respuesta);
            case "info":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta); // No content (204) si no hay
                                                                                     // estados
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<EstadoTarea.MensajeRespuesta> insertEstadoTarea(@Valid @RequestBody EstadoTarea estadotarea) {
        try {
            EstadoTarea.MensajeRespuesta respuesta = estadotareaService.insertEstadoTarea(estadotarea);
            if ("ok".equals(respuesta.getStatus())) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EstadoTarea.MensajeRespuesta("error", e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<EstadoTarea.MensajeRespuesta> updateEstadoTarea(@Valid @RequestBody EstadoTarea estadotarea) {
        try {
            EstadoTarea.MensajeRespuesta respuesta = estadotareaService.updateEstadoTarea(estadotarea);
            if ("ok".equals(respuesta.getStatus())) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EstadoTarea.MensajeRespuesta("error", e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<EstadoTarea.MensajeRespuesta> deleteEstadoTarea(@PathVariable("codigo") Long codigo) {
        EstadoTarea.MensajeRespuesta respuesta = estadotareaService.deleteEstadoTarea(codigo);
        if ("ok".equals(respuesta.getStatus())) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }
}
