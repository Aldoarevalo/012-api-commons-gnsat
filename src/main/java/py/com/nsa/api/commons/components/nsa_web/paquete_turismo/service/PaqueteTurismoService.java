package py.com.nsa.api.commons.components.nsa_web.paquete_turismo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.nsa_web.paquete_turismo.model.PaqueteTurismo;
import py.com.nsa.api.commons.components.nsa_web.paquete_turismo.repository.PaqueteTurismoRepository;
import py.com.nsa.api.commons.components.cfg.pais.repository.PaisRepository;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class PaqueteTurismoService {

    @Autowired
    private PaqueteTurismoRepository repository;

    @Autowired
    private PaisRepository paisRepository;

    public List<PaqueteTurismo> getAll() {
        return repository.findAll();
    }

    public PaqueteTurismo save(PaqueteTurismo paqueteTurismo) {
        paisRepository.findById(paqueteTurismo.getPais().getPaCod())
                .orElseThrow(() -> new RuntimeException("País no encontrado"));
        return repository.save(paqueteTurismo);
    }

    public Optional<PaqueteTurismo> update(Long id, PaqueteTurismo paqueteTurismo) {
        return repository.findById(id)
                .map(existingPaquete -> {
                    paqueteTurismo.setPatCodigo(id);
                    paisRepository.findById(paqueteTurismo.getPais().getPaCod())
                            .orElseThrow(() -> new RuntimeException("País no encontrado"));
                    return repository.save(paqueteTurismo);
                });
    }

    public Optional<PaqueteTurismo> delete(Long id) {
        return repository.findById(id)
                .map(paquete -> {
                    paquete.setPatEliminacion(new Date());
                    return repository.save(paquete);
                });
    }
}