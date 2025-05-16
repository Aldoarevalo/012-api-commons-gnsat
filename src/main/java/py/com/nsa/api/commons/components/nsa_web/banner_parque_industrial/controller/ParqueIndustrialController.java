package py.com.nsa.api.commons.components.nsa_web.banner_parque_industrial.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import py.com.nsa.api.commons.components.nsa_web.banner_parque_industrial.model.ParqueIndustrial;
import py.com.nsa.api.commons.components.nsa_web.banner_parque_industrial.service.ParqueIndustrialService;
import java.util.List;

@RestController
@RequestMapping("parque-industrial")
public class ParqueIndustrialController {

    @Autowired
    private ParqueIndustrialService service;

    @GetMapping("/lista")
    public List<ParqueIndustrial> getAll() {
        return service.getAll();
    }

    @PostMapping("/inserta")
    public ResponseEntity<ParqueIndustrial> save(@Valid @RequestBody ParqueIndustrial parqueIndustrial) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(parqueIndustrial));
    }

    @PutMapping("/actualiza/{id}")
    public ResponseEntity<ParqueIndustrial> update(@PathVariable Long id, @Valid @RequestBody ParqueIndustrial parqueIndustrial) {
        return service.update(id, parqueIndustrial)
                .map(updatedParqueIndustrial -> ResponseEntity.ok(updatedParqueIndustrial))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<ParqueIndustrial> delete(@PathVariable Long id) {
        return service.delete(id)
                .map(deletedParqueIndustrial -> ResponseEntity.ok(deletedParqueIndustrial))
                .orElse(ResponseEntity.notFound().build());
    }
}