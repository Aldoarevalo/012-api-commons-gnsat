package py.com.nsa.api.commons.components.cfg.ciudad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.cfg.ciudad.model.Ciudad;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

    boolean existsByCiuDescripcionIgnoreCaseAndDpCod(String ciuDescripcion, Long dpCod);
}
