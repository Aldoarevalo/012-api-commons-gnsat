package py.com.nsa.api.commons.components.cfg.detrecadv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.detrecadv.model.Detrecadv;
import py.com.nsa.api.commons.components.cfg.detrecadv.model.DetrecadvId;

import java.util.List;

@Repository
public interface DetrecadvRepository extends JpaRepository<Detrecadv, DetrecadvId> {

    @Query("SELECT d FROM Detrecadv d WHERE d.wmsStorerkey = :wmsStorerkey AND d.wmsExternreceiptkey = :wmsExternreceiptkey ORDER BY d.wmsId")
    List<Detrecadv> getListaDetrecadv(@Param("wmsStorerkey") String wmsStorerkey, @Param("wmsExternreceiptkey") String wmsExternreceiptkey);


    @Query("SELECT d FROM Detrecadv d WHERE d.wmsStorerkey = :wmsStorerkey AND d.wmsExternreceiptkey = :wmsExternreceiptkey AND d.wmsSku = :wmsSku")
    List<Detrecadv> findByWmsStorerkeyAndWmsExternreceiptkeyAndWmsSku(
            @Param("wmsStorerkey") String wmsStorerkey,
            @Param("wmsExternreceiptkey") String wmsExternreceiptkey,
            @Param("wmsSku") String wmsSku
    );

    @Query("SELECT COALESCE(MAX(d.wmsId), 0) + 1 FROM Detrecadv d WHERE d.wmsStorerkey = :wmsStorerkey AND d.wmsExternreceiptkey = :wmsExternreceiptkey")
    Long getNextWmsId(@Param("wmsStorerkey") String wmsStorerkey, @Param("wmsExternreceiptkey") String wmsExternreceiptkey);



}