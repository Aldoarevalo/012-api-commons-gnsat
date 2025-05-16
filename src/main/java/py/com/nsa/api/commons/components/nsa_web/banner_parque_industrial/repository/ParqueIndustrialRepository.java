package py.com.nsa.api.commons.components.nsa_web.banner_parque_industrial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.nsa_web.banner_parque_industrial.model.ParqueIndustrial;

@Repository
public interface ParqueIndustrialRepository extends JpaRepository<ParqueIndustrial, Long> {
}