package py.com.nsa.api.commons.components.nsa_web.banner_turismo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import py.com.nsa.api.commons.components.nsa_web.banner_turismo.model.Turismo;
import py.com.nsa.api.commons.components.nsa_web.banner_turismo.service.TurismoService;
import java.util.List;

@RestController
@RequestMapping("turismo")
public class TurismoController {

    @Autowired
    private TurismoService service;

    @GetMapping("/lista")
    public List<Turismo> getAll() {
        return service.getAll();
    }

    @PostMapping("/inserta")
    public ResponseEntity<Turismo> save(@Valid @RequestBody Turismo turismo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(turismo));
    }

    @PutMapping("/actualiza/{id}")
    public ResponseEntity<Turismo> update(@PathVariable Long id, @Valid @RequestBody Turismo turismo) {
        return service.update(id, turismo)
                .map(updatedTurismo -> ResponseEntity.ok(updatedTurismo))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<Turismo> delete(@PathVariable Long id) {
        return service.delete(id)
                .map(deletedTurismo -> ResponseEntity.ok(deletedTurismo))
                .orElse(ResponseEntity.notFound().build());
    }
}