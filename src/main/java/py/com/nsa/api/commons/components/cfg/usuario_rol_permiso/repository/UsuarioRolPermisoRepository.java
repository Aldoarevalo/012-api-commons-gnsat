package py.com.nsa.api.commons.components.cfg.usuario_rol_permiso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.usuario_rol_permiso.model.UsuarioRolPermiso;
import java.util.List;

@Repository
public interface UsuarioRolPermisoRepository extends JpaRepository<UsuarioRolPermiso, Long> {

    @Query("SELECT urp from UsuarioRolPermiso urp WHERE urp.usuCod = ?1")
    List<UsuarioRolPermiso> getRolesByUsuario(Long codUsuario);

    //POR SI ACASO
    //List<UsuarioRolPermiso> getPermisosByUsuario(String loginUsuario);
}
