package py.com.nsa.api.commons.components.cfg.perfil_usuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.perfil_usuario.model.PerfilUsuario;
import py.com.nsa.api.commons.components.cfg.perfil_usuario.repository.PerfilUsuarioRepository;

import java.util.List;
import java.util.Objects;

@Service
public class PerfilUsuarioService {
    @Autowired
    private PerfilUsuarioRepository repository;

    public List<PerfilUsuario> getList() {
        return repository.findAll();
    }

    public PerfilUsuario.MensajeRespuesta insertarPerfilUsuario(PerfilUsuario perfilUsuario) {
        try {
            // Crear el objeto Empleado y asignar los datos necesarios
            Long nextepauCodigo = repository.getNextPauCodigo();
            perfilUsuario.setPauCodigo(nextepauCodigo);
            PerfilUsuario nuevoPerfilAcceso = repository.save(perfilUsuario);
            return new PerfilUsuario.MensajeRespuesta(200L, "Perfil de Acceso de Usuario creado exitosamente.", List.of(nuevoPerfilAcceso));
        } catch (Exception e) {
            return new PerfilUsuario.MensajeRespuesta(500L, "Error al insertar el Perfil de Acceso de Usuario: " + e.getMessage(), null);
        }
    }

    public PerfilUsuario.MensajeRespuesta updatePerfilUsuario(PerfilUsuario perfilUsuario) {
        try {
            if (perfilUsuario.getPauCodigo() == null || !repository.existsById(perfilUsuario.getPauCodigo())) {
                return new PerfilUsuario.MensajeRespuesta(204L, "Perfil de Acceso de Usuario no Encontrado.", null);
            }
            PerfilUsuario updatedPerfilAcceso = repository.save(perfilUsuario);
            return new PerfilUsuario.MensajeRespuesta(200L, "Perfil de Acceso de Usuario actualizado exitosamente.", List.of(updatedPerfilAcceso));
        } catch (Exception e) {
            return new PerfilUsuario.MensajeRespuesta(500L, "Error al actualizar el Perfil de Acceso del Usuario: " + e.getMessage(), null);
        }
    }

    public void deleteById(Long cod_perfil) {
        repository.deleteById(cod_perfil);
    }

    public Long findPerfilByUsuarioId(Long codUsuario) {
        Long codPerfil = 0L;
        List<PerfilUsuario> listaUsuarios = repository.findAll();

        for(PerfilUsuario usuario : listaUsuarios){
            if (Objects.equals(usuario.getUsuCodigo(), codUsuario)){
                //usuario encontrado, devolvemos el codigo de perfil

                codPerfil = usuario.getPauCodigo();
                return codPerfil;
            }
        }

        return codPerfil;
    }

}