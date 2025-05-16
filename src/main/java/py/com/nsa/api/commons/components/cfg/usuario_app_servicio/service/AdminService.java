package py.com.nsa.api.commons.components.cfg.usuario_app_servicio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.app_servicio.service.AppServicioService;
import py.com.nsa.api.commons.components.cfg.perfil_usuario.service.PerfilUsuarioService;
import py.com.nsa.api.commons.components.cfg.perfil_servicio.service.PerfilServicioService;
import py.com.nsa.api.commons.components.cfg.perfil_servicio.model.PerfilServicio;
import py.com.nsa.api.commons.components.cfg.app_servicio.model.AppServicio;
import py.com.nsa.api.commons.components.cfg.usuario_app_servicio.repository.AdminRepository;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class AdminService {
    @Autowired
    private AdminRepository repository;

    @Autowired
    private PerfilUsuarioService perfilUsuarioService;

    @Autowired
    private AppServicioService appServicioService;

    @Autowired
    private PerfilServicioService perfilServicioService;

    /*
    public List<Admin> getList() {
        return (List<Admin>) repository.findAll();
    }

    //TODO. programar el agregar un permiso. parametros: codUsuario, codApp, codServicio
    public Admin save(Admin admin) {
        return repository.save(admin);
    }

    public Admin update(Admin admin) {
        return repository.save(admin);
    }
    */

    //TODO. programar el borrado de servicio por codApp y codUsuario
    /*
    public void deleteById(Long cod_app, Long cod_servicio) {
        repository.deleteById(cod_app, cod_servicio);
    }
     */

    // lista servicios por codUsuario y codApp
    public ArrayList<PerfilServicio> findServiciosByAppAndUser(Long codApp, Long codUsuario) {

        //lista de resultados a llenar
        ArrayList<PerfilServicio> listaMatch = new ArrayList<PerfilServicio>();

        //paso 1. obtenemos perfil del usuario
        Long codPerfil = 0L;
        codPerfil = perfilUsuarioService.findPerfilByUsuarioId(codUsuario);

        //paso 2. listamos servicios por app
        ArrayList<AppServicio> listaAppServicios = new ArrayList<AppServicio>();
        listaAppServicios = appServicioService.findServiciosByCodApp(codApp);

        //paso 3. listamos servicios por perfil
        ArrayList<PerfilServicio> listaPerfilServicios = new ArrayList<PerfilServicio>();
        listaPerfilServicios = perfilServicioService.findByPerfilId(codPerfil);

        //paso 4. hacemos match entre las dos listas
        //listamos aquellos servicios donde coincide el codigo de perfil
        for(AppServicio servicioPorApp : listaAppServicios){

            for(PerfilServicio servicioPorPerfil : listaPerfilServicios) {

                if (Objects.equals(servicioPorPerfil.getCod_servicio(), servicioPorApp.getSeaCodigo())) {
                    listaMatch.add(servicioPorPerfil);
                }
            }
        }

        return listaMatch;
    }

}