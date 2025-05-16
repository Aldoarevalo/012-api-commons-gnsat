package py.com.nsa.api.commons.components.nsa_web.banner_turismo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.nsa_web.banner_turismo.model.Turismo;
import py.com.nsa.api.commons.components.nsa_web.banner_turismo.repository.TurismoRepository;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class TurismoService {

    @Autowired
    private TurismoRepository repository;

    public List<Turismo> getAll() {
        return repository.findAll();
    }

    public Turismo save(Turismo turismo) {
        return repository.save(turismo);
    }

    public Optional<Turismo> update(Long id, Turismo turismo) {
        return repository.findById(id)
                .map(existingTurismo -> {
                    turismo.setBatCodigo(id);
                    return repository.save(turismo);
                });
    }

    public Optional<Turismo> delete(Long id) {
        return repository.findById(id)
                .map(turismo -> {
                    turismo.setBatEliminacion(new Date());
                    return repository.save(turismo);
                });
    }
}