package py.com.nsa.api.commons.components.cfg.shipmentsDet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.shipmentsDet.model.ShipmentDet;
import py.com.nsa.api.commons.components.cfg.shipmentsDet.model.ShipmentDetId;

import java.util.List;

@Repository
public interface ShipmentDetRepository extends JpaRepository<ShipmentDet, ShipmentDetId> {

    @Query("SELECT s FROM ShipmentDet s WHERE s.tmsWhseid = :tmsWhseid " +
            "AND s.tmsStorerkey = :tmsStorerkey " +
            "AND s.tmsExternorderkey = :tmsExternorderkey")
    List<ShipmentDet> getListaShipmentDet(
            @Param("tmsWhseid") String tmsWhseid,
            @Param("tmsStorerkey") String tmsStorerkey,
            @Param("tmsExternorderkey") String tmsExternorderkey
    );

    List<ShipmentDet> findByTmsStorerkeyAndTmsExternorderkey(String tmsStorerkey, String tmsExternorderkey);

    List<ShipmentDet> findByTmsSku(String tmsSku);

    @Query("SELECT s FROM ShipmentDet s WHERE s.tmsStorerkey = :tmsStorerkey AND s.tmsSku = :tmsSku")
    List<ShipmentDet> findByStorerkeyAndSku(
            @Param("tmsStorerkey") String tmsStorerkey,
            @Param("tmsSku") String tmsSku
    );
}