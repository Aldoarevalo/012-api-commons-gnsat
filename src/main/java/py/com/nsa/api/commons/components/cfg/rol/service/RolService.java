package py.com.nsa.api.commons.components.cfg.rol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.rol.model.Rol;
import py.com.nsa.api.commons.components.cfg.rol.repository.RolRepository;

import java.util.List;

@Service
public class RolService {
    @Autowired
    private RolRepository repository;

    public Rol.MensajeRespuesta getRolesAll() {
        try {
            List<Rol> roles = repository.findAll();

            if (roles.isEmpty()) {
                return new Rol.MensajeRespuesta(204L, "No se encontraron Roles.", null);
            }
            return new Rol.MensajeRespuesta(200L, "Roles obtenidas exitosamente.", roles);
        } catch (Exception e) {
            System.err.println("Error al obtener los Roles: " + e.getMessage());
            e.printStackTrace();
            return new Rol.MensajeRespuesta(500L, "Error al obtener los Roles: " + e.getMessage(), null);
        }
    }

    public Rol.MensajeRespuesta insert(Rol rol) {
        try {
            Rol insertedRol = repository.save(rol);
            return new Rol.MensajeRespuesta(200L, "Rol insertado exitosamente.", List.of(insertedRol));
        } catch (Exception e) {
            System.err.println("Error al insertar la agencia: " + e.getMessage());
            e.printStackTrace();
            return new Rol.MensajeRespuesta(500L, "Error al insertar el rol: " + e.getMessage(), null);
        }
    }

    public Rol.MensajeRespuesta update(Rol rol) {
        try {
            Rol updatedAgencia = repository.save(rol);
            return new Rol.MensajeRespuesta(200L, "Rol actualizado exitosamente. ", List.of(updatedAgencia));
        } catch (Exception e) {
            System.err.println("Error al actualizar el rol: " + e.getMessage());
            e.printStackTrace();
            return new Rol.MensajeRespuesta(500L, "Error al actualizar rol: " + e.getMessage(), null);
        }
    }

    public boolean deleteById(Long codigo) {
        try {
            repository.deleteById(codigo);
            return true;
        } catch (EmptyResultDataAccessException e) {
            // El rol no existe
            return false;
        } catch (DataIntegrityViolationException e) {
            // El rol no se puede eliminar debido a restricciones de integridad
            return false;
        } catch (Exception e) {
            // Cualquier otra excepción
            return false;
        }
    }



}