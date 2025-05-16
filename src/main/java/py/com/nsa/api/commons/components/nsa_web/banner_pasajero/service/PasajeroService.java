package py.com.nsa.api.commons.components.nsa_web.banner_pasajero.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.nsa_web.banner_pasajero.model.Pasajero;
import py.com.nsa.api.commons.components.nsa_web.banner_pasajero.repository.PasajeroRepository;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class PasajeroService {

    @Autowired
    private PasajeroRepository repository;

    public List<Pasajero> getAll() {
        return repository.findAll();
    }

    public Pasajero save(Pasajero pasajero) {
        return repository.save(pasajero);
    }

    public Optional<Pasajero> update(Long id, Pasajero pasajero) {
        return repository.findById(id)
                .map(existingPasajero -> {
                    pasajero.setBapCodigo(id);
                    return repository.save(pasajero);
                });
    }

    public Optional<Pasajero> delete(Long id) {
        return repository.findById(id)
                .map(pasajero -> {
                    pasajero.setBapEliminacion(new Date());
                    return repository.save(pasajero);
                });
    }
}