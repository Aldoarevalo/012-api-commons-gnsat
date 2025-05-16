package py.com.nsa.api.commons.components.cfg.perfil_servicio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.perfil_servicio.model.PerfilServicio;
import py.com.nsa.api.commons.components.cfg.perfil_servicio.repository.PerfilServicioRepository;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class PerfilServicioService {
    @Autowired
    private PerfilServicioRepository repository;

    public List<PerfilServicio> getList() {
        return (List<PerfilServicio>) repository.findAll();
    }

    public PerfilServicio save(PerfilServicio perfilServicio) {
        return repository.save(perfilServicio);
    }

    public PerfilServicio update(PerfilServicio perfilServicio) {
        return repository.save(perfilServicio);
    }

    //TODO. programar borrado de servicios por codperfil-usuario
    /*
    public void deleteById(Long cod_perfil, Long cod_servicio) {
        repository.deleteById(cod_perfil, cod_servicio);
    }
    */

    // lista servicios por cod perfil
    public ArrayList<PerfilServicio> findByPerfilId(Long cod_perfil) {

        ArrayList<PerfilServicio> listaMatch = new ArrayList<PerfilServicio>();

        //recorrer todos los servicios, hacer una lista de aquellos donde coincide el codigo de perfil
        ArrayList<PerfilServicio> listaServicios = (ArrayList<PerfilServicio>) repository.findAll();
        for(PerfilServicio servicioItem : listaServicios){

            if (Objects.equals(servicioItem.getCod_perfil(), cod_perfil)){
                listaMatch.add(servicioItem);
            }
        }
        return listaMatch;
    }

}