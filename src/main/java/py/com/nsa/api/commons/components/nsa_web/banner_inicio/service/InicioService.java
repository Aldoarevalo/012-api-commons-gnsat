package py.com.nsa.api.commons.components.nsa_web.banner_inicio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.nsa_web.banner_inicio.model.Inicio;
import py.com.nsa.api.commons.components.nsa_web.banner_inicio.repository.InicioRepository;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class InicioService {
    @Autowired
    private InicioRepository repository;

    public List<Inicio> getAll() {
        return repository.findAll();
    }

//    public Optional<Inicio> getById(Long id) {
//        return repository.findById(id);
//    }

    public Inicio save(Inicio inicio) {
        return repository.save(inicio);
    }

    public Optional<Inicio> update(Long id, Inicio inicio) {
    return repository.findById(id)
            .map(existingInicio ->{
                inicio.setInicioCodigo(id);
                return repository.save(inicio);
            });
    }

    public Optional<Inicio>delete(Long id){
    return repository.findById(id)
            .map(inicio ->{
                inicio.setBaiEliminacion(new Date());
                return repository.save(inicio);
            });
    }
}