package py.com.nsa.api.commons.components.cfg.app_servicio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.app_servicio.model.AppServicio;
import py.com.nsa.api.commons.components.cfg.app_servicio.repository.AppServicioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AppServicioService {
    @Autowired
    private AppServicioRepository repository;

    public List<AppServicio> getList() {
        return (List<AppServicio>) repository.findAll();
    }

    public AppServicio save(AppServicio appservicio) {
        return repository.save(appservicio);
    }

    public AppServicio update(AppServicio appservicio) {
        return repository.save(appservicio);
    }

    //TODO. habilitar clave compartida entre estos dos campos: cod_app, cod_servicio
    /*
    public void deleteById(Long cod_app, Long cod_servicio) {
        repository.deleteById(cod_app, cod_servicio);
    }
     */

    // lista servicios por cod perfil
    public ArrayList<AppServicio> findServiciosByCodApp(Long cod_app) {

        ArrayList<AppServicio> listaMatch = new ArrayList<AppServicio>();

        //recorrer todos los servicios, hacer una lista de aquellos donde coincide el codigo de perfil
        ArrayList<AppServicio> listaServicios = (ArrayList<AppServicio>) repository.findAll();
        for(AppServicio servicioItem : listaServicios){

            if (Objects.equals(servicioItem.getAppCodigo(), cod_app)){
                listaMatch.add(servicioItem);
            }
        }
        return listaMatch;
    }


}