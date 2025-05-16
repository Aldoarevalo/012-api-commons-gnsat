package py.com.nsa.api.commons.components.cfg.tipo_perfil_usuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.grupo_negocio.model.GrupoNegocio;
import py.com.nsa.api.commons.components.cfg.tipo_perfil_usuario.model.TipoPerfilUsuario;
import py.com.nsa.api.commons.components.cfg.tipo_perfil_usuario.repository.TipoPerfilUsuarioRepository;

import java.util.List;

@Service
public class TipoPerfilUsuarioService {

    @Autowired
    private TipoPerfilUsuarioRepository perfilUsuarioRepository;

    public TipoPerfilUsuario.MensajeRespuesta getPerfilesAll(String peuCodigo) {
        try {
            List<TipoPerfilUsuario> perfiles;
            if (peuCodigo == null || peuCodigo.isEmpty()) {
                perfiles = perfilUsuarioRepository.findAll();
            } else {
                perfiles = perfilUsuarioRepository.getListaPerfiles(peuCodigo);
            }
            if (perfiles.isEmpty()) {
                return new TipoPerfilUsuario.MensajeRespuesta(204L, "No se encontraron datos.", null);
            }
            return new TipoPerfilUsuario.MensajeRespuesta(200L, "Perfiles obtenidos exitosamente.", perfiles);
        } catch (Exception e) {
            System.err.println("Error al obtener los Perfiles de Usuario: " + e.getMessage());
            e.printStackTrace();
            return new TipoPerfilUsuario.MensajeRespuesta(500L, "Error al obtener los perfiles: " + e.getMessage(), null);
        }
    }
}
