package py.com.nsa.api.commons.components.nsa_web.banner_inicio.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import py.com.nsa.api.commons.components.nsa_web.banner_inicio.model.Inicio;
import py.com.nsa.api.commons.components.nsa_web.banner_inicio.service.InicioService;
import java.util.List;
@RestController
@RequestMapping("inicio")
public class InicioController {
@Autowired
    private InicioService service;

    @GetMapping("/lista")
    public List<Inicio> getAll() {
        return service.getAll();
    }
    @PostMapping("/inserta")
    public ResponseEntity<Inicio> save(@Valid @RequestBody Inicio inicio) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(service.save(inicio));
    }

    @PutMapping("/actualiza/{id}")
    public ResponseEntity<Inicio> update(@PathVariable Long id, @Valid @RequestBody Inicio inicio) {
        return service.update(id, inicio)
                .map(updatedInicio -> ResponseEntity.ok(updatedInicio))
               .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<Inicio> delete(@PathVariable Long id){
        return service.delete(id)
               .map(deletedInicio -> ResponseEntity.ok(deletedInicio))
               .orElse(ResponseEntity.notFound().build());
    }

}