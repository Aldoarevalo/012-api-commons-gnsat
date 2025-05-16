package py.com.nsa.api.commons.components.cfg.rol_permiso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import py.com.nsa.api.commons.components.cfg.rol_permiso.model.RolPermiso;
import py.com.nsa.api.commons.components.cfg.rol_permiso.service.RolPermisoService;

import java.util.List;

@RestController
@RequestMapping("rolpermiso")
public class RolPermisoController {

    @Autowired
    private RolPermisoService service;

    @GetMapping("/lista")
    public List<RolPermiso> getRolPermisoList() {
        return service.getList();
    }

    @PostMapping("/insert")
    public RolPermiso save(@RequestBody RolPermiso rolPermiso) {
        return service.save(rolPermiso);
    }

    @PutMapping("/update")
    public RolPermiso update(@RequestBody RolPermiso rolPermiso) {
        return service.update(rolPermiso);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestParam("codigo") Long codigo) {
        boolean result = service.deleteById(codigo);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/byrol/{rolCodigo}")
    public List<RolPermiso> getPermisosByRol(@PathVariable("rolCodigo") Long rolCodigo) {
        return service.findByRol(rolCodigo);
    }
}
