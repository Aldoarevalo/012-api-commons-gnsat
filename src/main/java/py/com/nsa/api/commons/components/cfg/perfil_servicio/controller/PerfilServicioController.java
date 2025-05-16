package py.com.nsa.api.commons.components.cfg.perfil_servicio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.perfil_servicio.model.PerfilServicio;
import py.com.nsa.api.commons.components.cfg.perfil_servicio.service.PerfilServicioService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("perfilservicio")
public class PerfilServicioController {
    @Autowired
    private PerfilServicioService service;

    @GetMapping("/lista")
    public List<PerfilServicio> getList() {
        return service.getList();
    }

    //devuelve lista de servicios por cod_perfil del usuario
    @GetMapping("/findByPerfilId")
    public ArrayList<PerfilServicio> getServiciosByCodPerfil(@RequestParam("codPerfil") Long codPerfil){
        return service.findByPerfilId(codPerfil);
    }

}
