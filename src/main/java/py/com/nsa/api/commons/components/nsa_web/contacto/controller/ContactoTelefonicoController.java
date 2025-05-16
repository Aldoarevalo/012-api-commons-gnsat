package py.com.nsa.api.commons.components.nsa_web.contacto.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import py.com.nsa.api.commons.components.nsa_web.contacto.model.ContactoTelefonico;
import py.com.nsa.api.commons.components.nsa_web.contacto.service.ContactoTelefonicoService;

import java.util.List;

@RestController
@RequestMapping("contacto-telefonico")
public class ContactoTelefonicoController {

    @Autowired
    private ContactoTelefonicoService service;

    @GetMapping("/lista")
    public List<ContactoTelefonico> getAll() {
        return service.getAll();
    }

    @PostMapping("/inserta")
    public ResponseEntity<ContactoTelefonico> save(@Valid @RequestBody ContactoTelefonico contactoTelefonico) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(contactoTelefonico));
    }

    @PutMapping("/actualiza/{id}")
    public ResponseEntity<ContactoTelefonico> update(@PathVariable Long id, @Valid @RequestBody ContactoTelefonico contactoTelefonico) {
        return service.update(id, contactoTelefonico)
                .map(updatedContacto -> ResponseEntity.ok(updatedContacto))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<ContactoTelefonico> delete(@PathVariable Long id) {
        return service.delete(id)
                .map(deletedContacto -> ResponseEntity.ok(deletedContacto))
                .orElse(ResponseEntity.notFound().build());
    }
}