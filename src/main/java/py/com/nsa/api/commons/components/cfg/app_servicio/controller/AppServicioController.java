package py.com.nsa.api.commons.components.cfg.app_servicio.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.app_servicio.model.AppServicio;
import py.com.nsa.api.commons.components.cfg.app_servicio.service.AppServicioService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("appservicio")
public class AppServicioController {

    @Autowired
    private AppServicioService service;

    @GetMapping("/lista")
    public List<AppServicio> getAppServicioList() {
        return service.getList();
    }

    @PostMapping("/insert")
    public AppServicio save(@Valid @RequestBody AppServicio appservicio) {
        return service.save(appservicio);
    }

    @PutMapping("/update")
    public AppServicio update(@Valid @RequestBody AppServicio appservicio) {
        return service.update(appservicio);
    }

    /*
    @DeleteMapping("/{app}/{servicio}")
    public String delete(@PathVariable("cod_app") Long cod_app, @PathVariable("cod_servicio") Long cod_servicio) {
        service.deleteById(cod_app, cod_servicio);
        return "Borrado con éxito";
    }
    */

    //devuelve lista de servicios de una app
    @GetMapping("/findByCodApp")
    public ArrayList<AppServicio> getServiciosByCodPerfil(@RequestParam("codApp") Long codApp){
        return service.findServiciosByCodApp(codApp);
    }

}
