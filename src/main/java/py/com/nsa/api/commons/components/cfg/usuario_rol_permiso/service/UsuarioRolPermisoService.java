package py.com.nsa.api.commons.components.cfg.usuario_rol_permiso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.rol.model.Rol;
import py.com.nsa.api.commons.components.cfg.usuario_rol_permiso.model.UsuarioRolPermiso;
import py.com.nsa.api.commons.components.cfg.usuario_rol_permiso.repository.UsuarioRolPermisoRepository;
import java.util.List;

@Service
public class UsuarioRolPermisoService {

    @Autowired
    private UsuarioRolPermisoRepository repository;

    public List<UsuarioRolPermiso> getListaRolesByUsuario(Long codUsuario) {
        return repository.getRolesByUsuario(codUsuario);
    }

}