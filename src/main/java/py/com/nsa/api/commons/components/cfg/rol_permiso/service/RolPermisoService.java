package py.com.nsa.api.commons.components.cfg.rol_permiso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.rol_permiso.model.RolPermiso;
import py.com.nsa.api.commons.components.cfg.rol_permiso.repository.RolPermisoRepository;

import java.util.List;

@Service
public class RolPermisoService {

    @Autowired
    private RolPermisoRepository repository;

    public List<RolPermiso> getList() {
        return repository.findAll();
    }

    public RolPermiso save(RolPermiso rolPermiso) {
        return repository.save(rolPermiso);
    }

    public RolPermiso update(RolPermiso rolPermiso) {
        return repository.save(rolPermiso);
    }

    public boolean deleteById(Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            // El rol-permiso no existe
            return false;
        } catch (DataIntegrityViolationException e) {
            // El rol-permiso no se puede eliminar debido a restricciones de integridad
            return false;
        } catch (Exception e) {
            // Cualquier otra excepción
            return false;
        }
    }

    public List<RolPermiso> findByRol(Long rolCodigo) {
        return repository.findByRolRolCodigo(rolCodigo);
    }
}
