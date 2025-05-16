package py.com.nsa.api.commons.components.nsa_web.banner_parque_industrial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.nsa_web.banner_parque_industrial.model.ParqueIndustrial;
import py.com.nsa.api.commons.components.nsa_web.banner_parque_industrial.repository.ParqueIndustrialRepository;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class ParqueIndustrialService {

    @Autowired
    private ParqueIndustrialRepository repository;

    public List<ParqueIndustrial> getAll() {
        return repository.findAll();
    }

    public ParqueIndustrial save(ParqueIndustrial parqueIndustrial) {
        return repository.save(parqueIndustrial);
    }

    public Optional<ParqueIndustrial> update(Long id, ParqueIndustrial parqueIndustrial) {
        return repository.findById(id)
                .map(existingParqueIndustrial -> {
                    parqueIndustrial.setBpiCodigo(id);
                    return repository.save(parqueIndustrial);
                });
    }

    public Optional<ParqueIndustrial> delete(Long id) {
        return repository.findById(id)
                .map(parqueIndustrial -> {
                    parqueIndustrial.setBpiEliminacion(new Date());
                    return repository.save(parqueIndustrial);
                });
    }
}