package py.com.nsa.api.commons.components.cfg.detostrpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.detostrpt.model.DetOstrpt;
import py.com.nsa.api.commons.components.cfg.detostrpt.model.DetOstrptId;

import java.util.List;

@Repository
public interface DetOstrptRepository extends JpaRepository<DetOstrpt, DetOstrptId> {

    @Query("SELECT d FROM DetOstrpt d WHERE d.wmsStorerkey = :wmsStorerkey AND d.wmsExternorderkey = :wmsExternorderkey ORDER BY d.wmsIddet")
    List<DetOstrpt> getListaDetOstrpt(@Param("wmsStorerkey") String wmsStorerkey, @Param("wmsExternorderkey") String wmsExternorderkey);

    List<DetOstrpt> findByWmsStorerkeyAndWmsExternorderkeyAndWmsSku(String wmsStorerkey, String wmsExternorderkey, String wmsSku);

    @Query("SELECT COALESCE(MAX(d.wmsIddet), 0) + 1 FROM DetOstrpt d WHERE d.wmsStorerkey = :wmsStorerkey AND d.wmsExternorderkey = :wmsExternorderkey")
    Long getNextWmsIddet(@Param("wmsStorerkey") String wmsStorerkey, @Param("wmsExternorderkey") String wmsExternorderkey);
}