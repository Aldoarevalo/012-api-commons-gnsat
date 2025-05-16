// Repositorio (GrupoUsuarioRepository.java)
package py.com.nsa.api.commons.components.cfg.grupo_usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.cfg.grupo_usuario.model.GrupoUsuario;
import java.util.List;

public interface GrupoUsuarioRepository extends JpaRepository<GrupoUsuario, Integer> {
    List<GrupoUsuario> findByUsuCod(Integer usuCod);
    List<GrupoUsuario> findByGruCod(Integer gruCod);
    boolean existsByUsuCodAndGruCod(Integer usuCod, Integer gruCod);
}
