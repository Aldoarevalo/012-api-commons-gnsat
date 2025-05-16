package py.com.nsa.api.commons.components.nsa_web.contacto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.nsa_web.contacto.model.ContactoTelefonico;
import py.com.nsa.api.commons.components.nsa_web.contacto.repository.ContactoTelefonicoRepository;
import py.com.nsa.api.commons.components.cfg.pais.repository.PaisRepository;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class ContactoTelefonicoService {

    @Autowired
    private ContactoTelefonicoRepository repository;

    @Autowired
    private PaisRepository paisRepository;

    public List<ContactoTelefonico> getAll() {
        return repository.findAll();
    }

    public ContactoTelefonico save(ContactoTelefonico contactoTelefonico) {
        paisRepository.findById(contactoTelefonico.getPais().getPaCod())
                .orElseThrow(() -> new RuntimeException("País no encontrado"));
        return repository.save(contactoTelefonico);
    }

    public Optional<ContactoTelefonico> update(Long id, ContactoTelefonico contactoTelefonico) {
        return repository.findById(id)
                .map(existingContacto -> {
                    contactoTelefonico.setContCodigo(id);
                    paisRepository.findById(contactoTelefonico.getPais().getPaCod())
                            .orElseThrow(() -> new RuntimeException("País no encontrado"));
                    return repository.save(contactoTelefonico);
                });
    }

    public Optional<ContactoTelefonico> delete(Long id) {
        return repository.findById(id)
                .map(contacto -> {
                    contacto.setContEliminado(new Date());
                    return repository.save(contacto);
                });
    }
}