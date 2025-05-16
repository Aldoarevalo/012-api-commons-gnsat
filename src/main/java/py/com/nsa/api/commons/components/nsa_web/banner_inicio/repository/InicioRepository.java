package py.com.nsa.api.commons.components.nsa_web.banner_inicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.nsa_web.banner_inicio.model.Inicio;

@Repository
public interface InicioRepository extends JpaRepository<Inicio, Long> {
}
