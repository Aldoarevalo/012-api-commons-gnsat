package py.com.nsa.api.commons.components.ref.tiposervicio.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.tiposervicio.model.TipoServicio;
import py.com.nsa.api.commons.components.ref.tiposervicio.service.TipoServicioService;

@RestController
@RequestMapping("/tiposervicio")
public class TipoServicioController {

    @Autowired
    private TipoServicioService tiposervicioService;

    @GetMapping("/lista")
    public ResponseEntity<TipoServicio.MensajeRespuesta> getTipoServiciosAll() {
        TipoServicio.MensajeRespuesta respuesta = tiposervicioService.getTipoServiciosAll();
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
    public ResponseEntity<TipoServicio.MensajeRespuesta> insertTisServicio(
            @Valid @RequestBody TipoServicio tiposervicio) {
        TipoServicio.MensajeRespuesta respuesta = tiposervicioService.insertTisServicio(tiposervicio);
        if ("ok".equals(respuesta.getStatus())) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TipoServicio.MensajeRespuesta> updateTisServicio(
            @Valid @RequestBody TipoServicio tiposervicio) {
        TipoServicio.MensajeRespuesta respuesta = tiposervicioService.updateTisServicio(tiposervicio);
        if ("ok".equals(respuesta.getStatus())) {
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<TipoServicio.MensajeRespuesta> deleteTipoServicio(@PathVariable("codigo") Long codigo) {
        TipoServicio.MensajeRespuesta response = tiposervicioService.deleteTipoServicio(codigo);
        if ("ok".equals(response.getStatus())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
