package py.com.nsa.api.commons.components.cfg.detostrptseries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.detostrptseries.model.DetostrptSeries;
import py.com.nsa.api.commons.components.cfg.detostrptseries.model.DetostrptSeriesId;

import java.util.List;

@Repository
public interface DetostrptSeriesRepository extends JpaRepository<DetostrptSeries, DetostrptSeriesId> {

    @Query("SELECT d FROM DetostrptSeries d WHERE d.wmsStorerkey = :wmsStorerkey AND d.wmsExternorderkey = :wmsExternorderkey AND d.wmsIddet = :wmsIddet")
    List<DetostrptSeries> findByOrderAndDetail(
            @Param("wmsStorerkey") String wmsStorerkey,
            @Param("wmsExternorderkey") String wmsExternorderkey,
            @Param("wmsIddet") Long wmsIddet
    );

    @Query("SELECT COALESCE(MAX(d.wmsIdserie), 0) + 1 FROM DetostrptSeries d WHERE d.wmsStorerkey = :wmsStorerkey AND d.wmsExternorderkey = :wmsExternorderkey AND d.wmsIddet = :wmsIddet")
    Long getNextSerieId(
            @Param("wmsStorerkey") String wmsStorerkey,
            @Param("wmsExternorderkey") String wmsExternorderkey,
            @Param("wmsIddet") Long wmsIddet
    );

    List<DetostrptSeries> findByWmsLpn(String wmsLpn);

    List<DetostrptSeries> findByWmsSerialNumber(String wmsSerialNumber);
}