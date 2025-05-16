package py.com.nsa.api.commons.components.cfg.detrecadvseries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.detrecadvseries.model.DetrecadvSeries;
import py.com.nsa.api.commons.components.cfg.detrecadvseries.model.DetrecadvSeriesId;

import java.util.List;

@Repository
public interface DetrecadvSeriesRepository extends JpaRepository<DetrecadvSeries, DetrecadvSeriesId> {

    @Query("SELECT d FROM DetrecadvSeries d WHERE d.wmsStorerkey = :wmsStorerkey AND d.wmsExternreceiptkey = :wmsExternreceiptkey AND d.wmsId = :wmsId")
    List<DetrecadvSeries> findByDetrecadvId(
            @Param("wmsStorerkey") String wmsStorerkey,
            @Param("wmsExternreceiptkey") String wmsExternreceiptkey,
            @Param("wmsId") Long wmsId
    );

    @Query("SELECT d FROM DetrecadvSeries d WHERE d.wmsLpn = :wmsLpn")
    List<DetrecadvSeries> findByWmsLpn(@Param("wmsLpn") String wmsLpn);

    @Query("SELECT d FROM DetrecadvSeries d WHERE d.wmsSerialNumber = :wmsSerialNumber")
    List<DetrecadvSeries> findByWmsSerialNumber(@Param("wmsSerialNumber") String wmsSerialNumber);
}