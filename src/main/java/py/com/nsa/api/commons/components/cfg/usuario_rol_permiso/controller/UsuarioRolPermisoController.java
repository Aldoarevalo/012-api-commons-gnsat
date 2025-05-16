package py.com.nsa.api.commons.components.cfg.usuario_rol_permiso.controller;

import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.cfg.perfil_servicio.model.PerfilServicio;
import py.com.nsa.api.commons.components.cfg.permiso.model.CfgPermiso;
import py.com.nsa.api.commons.components.cfg.rol_permiso.model.RolPermiso;
import py.com.nsa.api.commons.components.cfg.rol_permiso.service.RolPermisoService;
import py.com.nsa.api.commons.components.cfg.usuario_rol_permiso.model.UsuarioRolPermiso;
import py.com.nsa.api.commons.components.cfg.usuario_rol_permiso.service.UsuarioRolPermisoService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("usuario_rol_permiso")
public class UsuarioRolPermisoController {
    @Autowired
    private UsuarioRolPermisoService service;

    @Autowired
    private RolPermisoService serviceRolPermiso;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioRolPermisoService.class);

    // Se le pasa como parámetro un código de usuario numérico, y devuelve lista de roles de ese usuario
    @GetMapping("/rolesByUsuario/{codUsuario}")
    public List<UsuarioRolPermiso> getRolesByUsuario(@PathVariable Long codUsuario) {
        try {
            //IDEA. hacer join de 3 tablas: cfg_usuario, cfg_usuario_rol, cfg_rol
            List<UsuarioRolPermiso> roles = service.getListaRolesByUsuario(codUsuario);
            return roles;
        } catch (RuntimeException e) {
            String err = "Error al obtener roles del usuario " + codUsuario;
            logger.error( err + " {} ===>", e.getMessage(), e);
            return null;
        }
    }

    // Se le pasa como parámetro un código de usuario numérico, devuelve lista de permisos
    @GetMapping("/permisosByUsuario/{codUsuario}")
    public List<CfgPermiso> getPermisosByUsuario(@PathVariable("codUsuario") Long codUsuario) {
        try {

            // Lista para almacenar todos los permisos del usuario
            List<CfgPermiso> listaPermisos = new ArrayList<CfgPermiso>();

            // Buscamos los roles del usuario
            List<UsuarioRolPermiso> listaRoles = service.getListaRolesByUsuario(codUsuario);

            // Recorremos los roles para obtener los permisos asociados a cada rol
            for (UsuarioRolPermiso rol : listaRoles) {

                // Obtenemos la lista de permisos de cada rol
                List<RolPermiso> listaRolPermiso = serviceRolPermiso.findByRol(rol.getRolCod());

                // Recorremos los permisos y extraemos el valor del atributo `permis`
                for (RolPermiso permiso : listaRolPermiso) {
                    listaPermisos.add(permiso.getPermiso());
                }
            }

            return listaPermisos;

        } catch (RuntimeException e) {
            String err = "Error al obtener roles del usuario " + codUsuario;
            logger.error( err + " {} ===>", e.getMessage(), e);
            return null;
        }
    }

}
