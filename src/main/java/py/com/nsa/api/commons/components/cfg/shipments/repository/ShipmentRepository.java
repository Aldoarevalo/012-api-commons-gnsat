package py.com.nsa.api.commons.components.cfg.shipments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.shipments.model.Shipment;
import py.com.nsa.api.commons.components.cfg.shipments.model.ShipmentId;

import java.util.Date;
import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, ShipmentId> {

    @Query("SELECT s FROM Shipment s WHERE s.tmsWhseid = :tmsWhseid AND s.tmsStorerkey = :tmsStorerkey AND s.tmsExternorderkey = :tmsExternorderkey")
    List<Shipment> getListaShipment(
            @Param("tmsWhseid") String tmsWhseid,
            @Param("tmsStorerkey") String tmsStorerkey,
            @Param("tmsExternorderkey") String tmsExternorderkey
    );

    List<Shipment> findByTmsStorerkey(String tmsStorerkey);

    List<Shipment> findByTmsConsigneekey(String tmsConsigneekey);

    @Query("SELECT s FROM Shipment s WHERE s.tmsOrderDate BETWEEN :startDate AND :endDate")
    List<Shipment> findByOrderDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT s FROM Shipment s WHERE s.tmsStorerkey = :tmsStorerkey ORDER BY s.tmsOrderDate DESC")
    List<Shipment> findMostRecentByStorerkey(@Param("tmsStorerkey") String tmsStorerkey, org.springframework.data.domain.Pageable pageable);

    List<Shipment> findByTmsProcesado(String tmsProcesado);

    @Query("SELECT s FROM Shipment s WHERE s.tmsInvoiceDate BETWEEN :startDate AND :endDate")
    List<Shipment> findByInvoiceDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT s FROM Shipment s WHERE " +
            "(:tmsWhseid IS NULL OR s.tmsWhseid = :tmsWhseid) AND " +
            "(:tmsStorerkey IS NULL OR s.tmsStorerkey = :tmsStorerkey) AND " +
            "(:tmsExternorderkey IS NULL OR s.tmsExternorderkey = :tmsExternorderkey)")
    List<Shipment> getListaShipmentParcial(
            @Param("tmsWhseid") String tmsWhseid,
            @Param("tmsStorerkey") String tmsStorerkey,
            @Param("tmsExternorderkey") String tmsExternorderkey
    );
}