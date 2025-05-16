package py.com.nsa.api.commons.components.cfg.tipo_obj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.tipo_obj.model.TipObj;

@Repository
public interface TipObjRepository extends JpaRepository<TipObj, Long> {

}
