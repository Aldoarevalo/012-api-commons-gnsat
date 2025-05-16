package py.com.nsa.api.commons.components.cfg.usuario_app_servicio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.perfil_servicio.model.PerfilServicio;
import py.com.nsa.api.commons.components.cfg.usuario_app_servicio.service.AdminService;

import java.util.ArrayList;

@RestController
//usuario_servicio no es una tabla, sino que debe ser un join
@RequestMapping("usuarioservicio")
public class AdminController {

    @Autowired
    private AdminService service;

    /*
    @GetMapping("/lista")
    public List<Admin> getAppServicioList() {
        return service.getList();
    }

    //TODO. programar el agregar un permiso. parametros: codUsuario, codApp, codServicio
    @PostMapping("/insert")
    public Admin save(@Valid @RequestBody Admin admin) {
        return service.save(admin);
    }

    @PutMapping("/update")
    public Admin update(@Valid @RequestBody Admin admin) {
        return service.update(admin);
    }
    */

    /*TODO. programar el borrado de un permiso: parametros: codUsuario, codApp, codServicio
    @DeleteMapping("/{codUsuario}/{codApp}/{codServicio}")
    public String delete(@PathVariable("cod_app") Long cod_app, @PathVariable("cod_servicio") Long cod_servicio) {
        service.deleteById(cod_app, cod_servicio);
        return "Borrado con éxito";
    }
    */

    //devuelve lista de servicios de una app para un usuario específico
    @GetMapping("/findServiciosByAppAndUser")
    public ArrayList<PerfilServicio> getServiciosByAppAndUser(
            @RequestParam("codApp") Long codApp ,
            @RequestParam("codUsuario") Long codUsuario){
        return service.findServiciosByAppAndUser(codApp, codUsuario);
    }


}
