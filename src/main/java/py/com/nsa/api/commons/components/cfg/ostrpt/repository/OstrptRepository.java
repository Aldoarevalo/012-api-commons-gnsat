package py.com.nsa.api.commons.components.cfg.ostrpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.ostrpt.model.Ostrpt;
import py.com.nsa.api.commons.components.cfg.ostrpt.model.OstrptId;

import java.util.List;
import java.util.Date;

@Repository
public interface OstrptRepository extends JpaRepository<Ostrpt, OstrptId> {

    List<Ostrpt> findByWmsStorerkey(String wmsStorerkey);

    List<Ostrpt> findByWmsType(String wmsType);

    @Query("SELECT o FROM Ostrpt o WHERE o.wmsExtUdfDate1 BETWEEN :startDate AND :endDate")
    List<Ostrpt> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT o FROM Ostrpt o WHERE o.wmsStorerkey = :wmsStorerkey ORDER BY o.wmsExtUdfDate1 DESC")
    List<Ostrpt> findMostRecentByWmsStorerkey(@Param("wmsStorerkey") String wmsStorerkey, org.springframework.data.domain.Pageable pageable);

    @Query("SELECT o FROM Ostrpt o WHERE o.wmsStorerkey = :wmsStorerkey AND o.wmsExternorderkey = :wmsExternorderkey")
    List<Ostrpt> getListaOstrpt(@Param("wmsStorerkey") String wmsStorerkey, @Param("wmsExternorderkey") String wmsExternorderkey);
}