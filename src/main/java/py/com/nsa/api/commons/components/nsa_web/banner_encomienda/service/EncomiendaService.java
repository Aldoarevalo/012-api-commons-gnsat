package py.com.nsa.api.commons.components.nsa_web.banner_encomienda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.nsa_web.banner_encomienda.model.Encomienda;
import py.com.nsa.api.commons.components.nsa_web.banner_encomienda.repository.EncomiendaRepository;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class EncomiendaService {

    @Autowired
    private EncomiendaRepository repository;

    public List<Encomienda> getAll() {
        return repository.findAll();
    }

    public Encomienda save(Encomienda encomienda) {
        return repository.save(encomienda);
    }

    public Optional<Encomienda> update(Long id, Encomienda encomienda) {
        return repository.findById(id)
                .map(existingEncomienda -> {
                    encomienda.setBaeCodigo(id);
                    return repository.save(encomienda);
                });
    }

    public Optional<Encomienda> delete(Long id) {
        return repository.findById(id)
                .map(encomienda -> {
                    encomienda.setBaeEliminacion(new Date());
                    return repository.save(encomienda);
                });
    }
}