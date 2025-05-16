package py.com.nsa.api.commons.components.nsa_web.banner_logistica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.nsa_web.banner_logistica.model.Logistica;
import py.com.nsa.api.commons.components.nsa_web.banner_logistica.repository.LogisticaRepository;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class LogisticaService {

    @Autowired
    private LogisticaRepository repository;

    public List<Logistica> getList() {
        return repository.findAll();
    }



    public Logistica save(Logistica logistica) {
        return repository.save(logistica);
    }

    public Optional<Logistica> update(Long id, Logistica logistica) {
        return repository.findById(id)
                .map(existingLogistica -> {
                    logistica.setBalCodigo(id);
                    return repository.save(logistica);
                });
    }

    public Optional<Logistica> delete(Long id) {
        return repository.findById(id)
                .map(logistica -> {
                    logistica.setBaiEliminacion(new Date());
                    return repository.save(logistica);
                });
    }
}