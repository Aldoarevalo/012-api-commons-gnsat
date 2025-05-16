package py.com.nsa.api.commons.components.nsa_web.banner_encomienda.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import py.com.nsa.api.commons.components.nsa_web.banner_encomienda.model.Encomienda;
import py.com.nsa.api.commons.components.nsa_web.banner_encomienda.service.EncomiendaService;
import java.util.List;

@RestController
@RequestMapping("encomienda")
public class EncomiendaController {

    @Autowired
    private EncomiendaService service;

    @GetMapping("/lista")
    public List<Encomienda> getAll() {
        return service.getAll();
    }

    @PostMapping("/inserta")
    public ResponseEntity<Encomienda> save(@Valid @RequestBody Encomienda encomienda) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(encomienda));
    }

    @PutMapping("/actualiza/{id}")
    public ResponseEntity<Encomienda> update(@PathVariable Long id, @Valid @RequestBody Encomienda encomienda) {
        return service.update(id, encomienda)
                .map(updatedEncomienda -> ResponseEntity.ok(updatedEncomienda))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<Encomienda> delete(@PathVariable Long id) {
        return service.delete(id)
                .map(deletedEncomienda -> ResponseEntity.ok(deletedEncomienda))
                .orElse(ResponseEntity.notFound().build());
    }
}