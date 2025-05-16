package py.com.nsa.api.commons.components.cfg.usuario_rol.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.rol_permiso.model.RolPermiso;
import py.com.nsa.api.commons.components.cfg.usuario_rol.model.CfgUsuarioRol;

import java.util.List;

@Repository
public interface CfgUsuarioRolRepository extends JpaRepository<CfgUsuarioRol, Long> {
    boolean existsByUsuCodAndRolCodigo(Long usuCod, Long rolCodigo);

    List<CfgUsuarioRol> findByUsuCod(Long usuCod);

    @Query(value = "SELECT u.USU_COD, u.USU_NOMBRE, " +
            "LISTAGG(r.ROL_COD, ', ') WITHIN GROUP (ORDER BY r.ROL_COD) AS ROLES_CODIGOS, " +
            "LISTAGG(r.ROL_NOMBRE, ', ') WITHIN GROUP (ORDER BY r.ROL_NOMBRE) AS ROLES_NOMBRES " +
            "FROM GNSAT.CFG_USUARIO_ROL ur " +
            "JOIN GNSAT.CFG_ROL r ON ur.ROL_COD = r.ROL_COD " +
            "JOIN GNSAT.CFG_USUARIO u ON ur.USU_COD = u.USU_COD " +
            "WHERE r.ROL_ESTADO = 'A' AND u.USU_ESTADO = 'A' " +
            "GROUP BY u.USU_COD, u.USU_NOMBRE", nativeQuery = true)
    List<Object[]> findAllUsuariosConRoles();

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM GNSAT.CFG_USUARIO_ROL WHERE USU_COD = :usuCod", nativeQuery = true)
    int deleteByUsuCod(Long usuCod);

}