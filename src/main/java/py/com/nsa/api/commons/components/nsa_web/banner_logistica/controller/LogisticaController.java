package py.com.nsa.api.commons.components.nsa_web.banner_logistica.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import py.com.nsa.api.commons.components.nsa_web.banner_logistica.model.Logistica;
import py.com.nsa.api.commons.components.nsa_web.banner_logistica.service.LogisticaService;
import java.util.List;

@RestController
@RequestMapping("logistica")
public class LogisticaController {

    @Autowired
    private LogisticaService service;

    @GetMapping("/lista")
    public List<Logistica> getLogisticaList() {
        return service.getList();
    }

    @PostMapping("/inserta")
    public ResponseEntity<Logistica> save(@Valid @RequestBody Logistica logistica) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(logistica));
    }

    @PutMapping("/actualiza/{id}")
    public ResponseEntity<Logistica> update(@PathVariable Long id, @Valid @RequestBody Logistica logistica) {
        return service.update(id, logistica)
                .map(updatedLogistica -> ResponseEntity.ok(updatedLogistica))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<Logistica> delete(@PathVariable Long id) {
        return service.delete(id)
                .map(deletedLogistica -> ResponseEntity.ok(deletedLogistica))
                .orElse(ResponseEntity.notFound().build());
    }
}