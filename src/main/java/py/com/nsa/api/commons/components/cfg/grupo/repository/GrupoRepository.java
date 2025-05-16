// Repositorio (GrupoRepository.java)
package py.com.nsa.api.commons.components.cfg.grupo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.grupo.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer> {
}
