package py.com.nsa.api.commons.components.cfg.perfil_usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import py.com.nsa.api.commons.components.cfg.perfil_usuario.model.PerfilUsuario;

public interface PerfilUsuarioRepository extends JpaRepository<PerfilUsuario, Long> {

    @Query("SELECT COALESCE(MAX(p.pauCodigo), 0) + 1 FROM PerfilUsuario p")
    Long getNextPauCodigo();

}
