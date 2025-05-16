package py.com.nsa.api.commons.components.nsa_web.paquete_turismo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import py.com.nsa.api.commons.components.nsa_web.paquete_turismo.model.PaqueteTurismo;
import py.com.nsa.api.commons.components.nsa_web.paquete_turismo.service.PaqueteTurismoService;

import java.util.List;

@RestController
@RequestMapping("paquete-turismo")
public class PaqueteTurismoController {

    @Autowired
    private PaqueteTurismoService service;

    @GetMapping("/lista")
    public List<PaqueteTurismo> getAll() {
        return service.getAll();
    }

    @PostMapping("/inserta")
    public ResponseEntity<PaqueteTurismo> save(@Valid @RequestBody PaqueteTurismo paqueteTurismo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(paqueteTurismo));
    }

    @PutMapping("/actualiza/{id}")
    public ResponseEntity<PaqueteTurismo> update(@PathVariable Long id, @Valid @RequestBody PaqueteTurismo paqueteTurismo) {
        return service.update(id, paqueteTurismo)
                .map(updatedPaquete -> ResponseEntity.ok(updatedPaquete))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<PaqueteTurismo> delete(@PathVariable Long id) {
        return service.delete(id)
                .map(deletedPaquete -> ResponseEntity.ok(deletedPaquete))
                .orElse(ResponseEntity.notFound().build());
    }
}