package py.com.nsa.api.commons.components.nsa_web.banner_pasajero.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import py.com.nsa.api.commons.components.nsa_web.banner_pasajero.model.Pasajero;
import py.com.nsa.api.commons.components.nsa_web.banner_pasajero.service.PasajeroService;
import java.util.List;

@RestController
@RequestMapping("pasajero")
public class PasajeroController {

    @Autowired
    private PasajeroService service;

    @GetMapping("/lista")
    public List<Pasajero> getAll() {
        return service.getAll();
    }

    @PostMapping("/inserta")
    public ResponseEntity<Pasajero> save(@Valid @RequestBody Pasajero pasajero) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(pasajero));
    }

    @PutMapping("/actualiza/{id}")
    public ResponseEntity<Pasajero> update(@PathVariable Long id, @Valid @RequestBody Pasajero pasajero) {
        return service.update(id, pasajero)
                .map(updatedPasajero -> ResponseEntity.ok(updatedPasajero))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<Pasajero> delete(@PathVariable Long id) {
        return service.delete(id)
                .map(deletedPasajero -> ResponseEntity.ok(deletedPasajero))
                .orElse(ResponseEntity.notFound().build());
    }
}