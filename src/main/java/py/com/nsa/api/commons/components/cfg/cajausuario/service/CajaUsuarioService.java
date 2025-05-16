package py.com.nsa.api.commons.components.cfg.cajausuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import py.com.nsa.api.commons.components.cfg.cajausuario.model.CajaUsuario;
import py.com.nsa.api.commons.components.cfg.cajausuario.model.UpdateOperacionRequest;
import py.com.nsa.api.commons.components.cfg.cajausuario.repository.CajaUsuarioRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class CajaUsuarioService {
    private static final Logger logger = LoggerFactory.getLogger(CajaUsuarioService.class);

    @Autowired
    private CajaUsuarioRepository repository;

    public CajaUsuario.MensajeRespuesta getAllCajaUsuarios() {
        try {
            List<CajaUsuario> cajaUsuarios = repository.findAll();
            if (cajaUsuarios.isEmpty()) {
                return new CajaUsuario.MensajeRespuesta(204L, "No se encontraron caja-usuario.", null);
            }
            return new CajaUsuario.MensajeRespuesta(200L, "Caja-usuario obtenidos exitosamente.", cajaUsuarios);
        } catch (Exception e) {
            return new CajaUsuario.MensajeRespuesta(500L, "Error al obtener caja-usuario: " + e.getMessage(), null);
        }
    }

    public CajaUsuario.MensajeRespuesta insert(CajaUsuario cajaUsuario) {
        try {
            CajaUsuario insertedCajaUsuario = repository.save(cajaUsuario);
            return new CajaUsuario.MensajeRespuesta(200L, "Caja-usuario insertado exitosamente.", List.of(insertedCajaUsuario));
        } catch (Exception e) {
            return new CajaUsuario.MensajeRespuesta(500L, "Error al insertar caja-usuario: " + e.getMessage(), null);
        }
    }

    public CajaUsuario.MensajeRespuesta update(CajaUsuario cajaUsuario) {
        try {
            CajaUsuario updatedCajaUsuario = repository.save(cajaUsuario);
            return new CajaUsuario.MensajeRespuesta(200L, "Caja-usuario actualizado exitosamente.", List.of(updatedCajaUsuario));
        } catch (Exception e) {
            return new CajaUsuario.MensajeRespuesta(500L, "Error al actualizar caja-usuario: " + e.getMessage(), null);
        }
    }

    public boolean deleteById(Long codigo) {
        try {
            repository.deleteById(codigo);
            return true;
        } catch (EmptyResultDataAccessException | DataIntegrityViolationException e) {
            return false;
        }
    }

    public CajaUsuario.MensajeRespuesta getCajaUsuariosByFilter(Long usuario, Long agencia) {
        try {
            List<CajaUsuario> cajaUsuarios = repository.findByUsuarioAndAgencia(usuario, agencia);
            return new CajaUsuario.MensajeRespuesta(
                !cajaUsuarios.isEmpty() ? 200L : 204L,
                !cajaUsuarios.isEmpty() ? "Cajas usuario encontradas" : "No se encontraron cajas usuario",
                cajaUsuarios
            );
        } catch (Exception e) {
            return new CajaUsuario.MensajeRespuesta(500L, "Error al obtener cajas usuario: " + e.getMessage(), null);
        }
    }



    @Transactional
    public CajaUsuario.MensajeRespuesta updateOperacionUsuario(UpdateOperacionRequest request) {
        try {
            if (request.getUsuariosIds() == null || request.getUsuariosIds().isEmpty()) {
                return new CajaUsuario.MensajeRespuesta(400L, 
                    "La lista de IDs de caja-usuario no puede estar vacía", 
                    null);
            }

            List<CajaUsuario> cajaUsuarios = repository.findAllById(request.getUsuariosIds());
            
            if (cajaUsuarios.isEmpty()) {
                return new CajaUsuario.MensajeRespuesta(404L, 
                    "No se encontraron registros para los IDs de caja-usuario especificados", 
                    null);
            }

            cajaUsuarios.forEach(caja -> caja.setOperacion(request.getOperacion()));
            List<CajaUsuario> cajasActualizadas = repository.saveAll(cajaUsuarios);
            
            return new CajaUsuario.MensajeRespuesta(200L, 
                String.format("Se actualizó la operación de %d registros exitosamente", cajasActualizadas.size()), 
                cajasActualizadas);
            
        } catch (Exception e) {
            logger.error("Error al actualizar operación de cajas usuario: {}", e.getMessage(), e);
            return new CajaUsuario.MensajeRespuesta(500L, 
                "Error al actualizar operación de cajas usuario: " + e.getMessage(), 
                null);
        }
    }


}

