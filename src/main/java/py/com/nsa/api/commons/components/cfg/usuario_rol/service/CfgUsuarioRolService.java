package py.com.nsa.api.commons.components.cfg.usuario_rol.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.cfg.usuario_rol.model.CfgUsuarioRol;
import py.com.nsa.api.commons.components.cfg.usuario_rol.repository.CfgUsuarioRolRepository;

import java.util.List;

@Service
public class CfgUsuarioRolService {

    @Autowired
    private CfgUsuarioRolRepository usuarioRolRepository;

    private static final Logger logger = LoggerFactory.getLogger(CfgUsuarioRolService.class);

    public CfgUsuarioRol.MensajeRespuesta getUsuariosRolAll() {
        try {
            List<CfgUsuarioRol> usuariosRol = usuarioRolRepository.findAll();
            if (usuariosRol.isEmpty()) {
                return new CfgUsuarioRol.MensajeRespuesta(204L, "No se encontraron registros de usuario-rol.", null);
            }
            return new CfgUsuarioRol.MensajeRespuesta(200L, "Registros de usuario-rol obtenidos exitosamente.",
                    usuariosRol);
        } catch (Exception e) {
            e.printStackTrace();
            return new CfgUsuarioRol.MensajeRespuesta(500L,
                    "Error al obtener los registros de usuario-rol: " + e.getMessage(), null);
        }
    }

    public List<Object[]> findAllUsuariosConRoles() {
        try {
            List<Object[]> usuariosRol = usuarioRolRepository.findAllUsuariosConRoles();
            return usuariosRol;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public CfgUsuarioRol.MensajeRespuesta insertarUsuarioRol(CfgUsuarioRol usuarioRol) {
        try {
            if (usuarioRolRepository.existsByUsuCodAndRolCodigo(usuarioRol.getUsuCod(), usuarioRol.getRolCodigo())) {
                return new CfgUsuarioRol.MensajeRespuesta(204L,
                        "Ya existe un rol para el mismo usuario. Usuario:" + usuarioRol.getUsuCod() + ". Rol: "
                                + usuarioRol.getRolCodigo(),
                        null);
            }
            CfgUsuarioRol nuevoUsuarioRol = usuarioRolRepository.save(usuarioRol);
            return new CfgUsuarioRol.MensajeRespuesta(200L, "Registro de usuario-rol creado exitosamente.",
                    List.of(nuevoUsuarioRol));
        } catch (Exception e) {
            e.printStackTrace();
            return new CfgUsuarioRol.MensajeRespuesta(500L,
                    "Error al insertar el registro de usuario-rol: " + e.getMessage(), null);
        }
    }

    @Transactional
    public CfgUsuarioRol.MensajeRespuesta updateUsuarioRol(CfgUsuarioRol usuarioRol) {
        try {
            if (usuarioRolRepository.existsByUsuCodAndRolCodigo(usuarioRol.getUsuCod(), usuarioRol.getRolCodigo())) {
                // NO DEVUELVE ERROR. Se deja grabado el par rol-usuario tal cual está ya
                // return new CfgUsuarioRol.MensajeRespuesta(204L,
                // "Ya existe este rol para el mismo usuario.", null);
            }
            if (usuarioRol.getUsuRolCod() == null || !usuarioRolRepository.existsById(usuarioRol.getUsuRolCod())) {
                return new CfgUsuarioRol.MensajeRespuesta(204L, "Registro de usuario-rol no encontrado.", null);
            }
            CfgUsuarioRol usuarioRolActualizado = usuarioRolRepository.save(usuarioRol);
            return new CfgUsuarioRol.MensajeRespuesta(200L, "Registro de usuario-rol actualizado exitosamente.",
                    List.of(usuarioRolActualizado));
        } catch (Exception e) {
            e.printStackTrace();
            return new CfgUsuarioRol.MensajeRespuesta(500L,
                    "Error al actualizar el registro de usuario-rol: " + e.getMessage(), null);
        }
    }

    @Transactional
    public CfgUsuarioRol.MensajeRespuesta deleteUsuarioRol(Long usuRolCod) {
        try {
            if (!usuarioRolRepository.existsById(usuRolCod)) {
                return new CfgUsuarioRol.MensajeRespuesta(204L,
                        "Registro de Usuario-Rol con código " + usuRolCod + " no encontrado.", null);
            }

            usuarioRolRepository.deleteById(usuRolCod);
            return new CfgUsuarioRol.MensajeRespuesta(200L, "Registro de Usuario-Rol eliminado exitosamente.", null);
        } catch (JpaSystemException e) {
            // Personalizamos el mensaje de error
            String mensaje = "No se puede eliminar el rol del usuario porque está referenciada por otros registros"; // Mensaje
            // personalizado
            return new CfgUsuarioRol.MensajeRespuesta(204L,
                    "Error al eliminar el Usuario-Rol: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new CfgUsuarioRol.MensajeRespuesta(500L,
                    "Error al eliminar el Usuario-Rol con código " + usuRolCod + ": " + e.getMessage(), null);
        }
    }

    // borra todos los roles que un usuario específica tenga asociados
    @Transactional(rollbackFor = { Exception.class })
    public CfgUsuarioRol.MensajeRespuesta deleteByUsuCod(Long usuCod) {
        logger.info("Iniciando la eliminación de roles para el usuario con ID: {}", usuCod);
        try {
            int filas = usuarioRolRepository.deleteByUsuCod(usuCod);
            logger.info("Se han eliminado {} registros de roles para el usuario con ID: {}", filas, usuCod);
            if (filas > 0) {
                return new CfgUsuarioRol.MensajeRespuesta(200L, "Registros de Usuario-Rol eliminados exitosamente.",
                        null);
            } else {
                return new CfgUsuarioRol.MensajeRespuesta(200L, "No hay registros de Usuario-Rol para eliminar.", null);
            }
        } catch (DataIntegrityViolationException e) {
            logger.error("Error de integridad referencial al eliminar roles del usuario con ID: {}", usuCod, e);
            String mensaje = "No se puede eliminar el rol del usuario porque está referenciado por otros registros.";
            return new CfgUsuarioRol.MensajeRespuesta(204L, "Error al eliminar el Usuario-Rol: " + mensaje, null);
        } catch (Exception e) {
            logger.error("Error general al eliminar el usuario-rol con ID: {}", usuCod, e);
            return new CfgUsuarioRol.MensajeRespuesta(500L,
                    "Error al eliminar el Usuario-Rol con código " + usuCod + ": " + e.getMessage(), null);
        }
    }

    public List<CfgUsuarioRol> findByCodigoUsuario(Long usuarioCodigo) {
        return usuarioRolRepository.findByUsuCod(usuarioCodigo);
    }

    public List<CfgUsuarioRol> findByUser(Long userCodigo) {
        return usuarioRolRepository.findByUsuCod(userCodigo);
    }

}