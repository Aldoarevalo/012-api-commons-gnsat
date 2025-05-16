package py.com.nsa.api.commons.components.cfg.cajausuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.cajausuario.model.CajaUsuario;

import java.util.List;

@Repository
public interface CajaUsuarioRepository extends JpaRepository<CajaUsuario, Long> {
    
    @Query(value = "SELECT c FROM CajaUsuario c " +
           "WHERE (:usuario is null OR c.usuario = :usuario) " +
           "AND (:agencia is null OR c.agencia = :agencia)")
    List<CajaUsuario> findByUsuarioAndAgencia(
            @Param("usuario") Long usuario,
            @Param("agencia") Long agencia);


@Query("SELECT c FROM CajaUsuario c WHERE c.usuario IN :usuariosIds")
List<CajaUsuario> findByUsuarioIn(@Param("usuariosIds") List<Long> usuariosIds);

}