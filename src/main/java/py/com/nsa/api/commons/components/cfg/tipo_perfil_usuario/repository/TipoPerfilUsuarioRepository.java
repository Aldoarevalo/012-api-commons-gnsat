package py.com.nsa.api.commons.components.cfg.tipo_perfil_usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.tipo_perfil_usuario.model.TipoPerfilUsuario;

import java.util.List;

@Repository
public interface TipoPerfilUsuarioRepository extends JpaRepository<TipoPerfilUsuario, Long> {

    @Query("SELECT p FROM TipoPerfilUsuario p WHERE p.peuCodigo = :peuCodigo ORDER BY p.peuCodigo")
    List<TipoPerfilUsuario> getListaPerfiles(@Param("peuCodigo") String peuCodigo);
}
