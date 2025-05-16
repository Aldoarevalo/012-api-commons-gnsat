package py.com.nsa.api.commons.components.ref.marca.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.marca.model.Marca;
import py.com.nsa.api.commons.components.ref.marca.service.MarcaService;

@RestController
@RequestMapping("/marca")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping("/lista")
    public ResponseEntity<Marca.MensajeRespuesta> getMarcasall() {
        Marca.MensajeRespuesta respuesta = marcaService.getMarcasall();
        switch (respuesta.getStatus()) {
            case "ok":
                return ResponseEntity.ok(respuesta);
            case "info":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta); // No content (204) si no hay
                                                                                     // marcas
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Marca.MensajeRespuesta> insertMarca(@Valid @RequestBody Marca marca) {
        try {
            Marca.MensajeRespuesta respuesta = marcaService.insertMarca(marca);
            if ("ok".equals(respuesta.getStatus())) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Marca.MensajeRespuesta("error", e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Marca.MensajeRespuesta> updateMarca(@Valid @RequestBody Marca marca) {
        try {
            Marca.MensajeRespuesta respuesta = marcaService.updateMarca(marca);
            if ("ok".equals(respuesta.getStatus())) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Marca.MensajeRespuesta("error", e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<Marca.MensajeRespuesta> deleteMarca(@PathVariable("codigo") Long codigo) {
        Marca.MensajeRespuesta response = marcaService.deleteMarca(codigo);
        if ("ok".equals(response.getStatus())) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
