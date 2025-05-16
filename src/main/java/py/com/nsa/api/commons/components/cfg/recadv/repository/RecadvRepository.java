package py.com.nsa.api.commons.components.cfg.recadv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.recadv.model.Recadv;
import py.com.nsa.api.commons.components.cfg.recadv.model.RecadvId;

import java.util.Date;
import java.util.List;

@Repository
public interface RecadvRepository extends JpaRepository<Recadv, RecadvId> {

    // Buscar por wmsStorerkey y wmsExternreceiptkey (clave primaria compuesta)
    Recadv findByWmsStorerkeyAndWmsExternreceiptkey(String wmsStorerkey, String wmsExternreceiptkey);

    // Listar todos los Recadv por wmsStorerkey
    List<Recadv> findByWmsStorerkey(String wmsStorerkey);

    // Buscar por wmsType
    List<Recadv> findByWmsType(String wmsType);

    // Buscar por wmsSuppliercode
    List<Recadv> findByWmsSuppliercode(String wmsSuppliercode);

    // Buscar por rango de fechas de wmsExpectedreceiptdate
    @Query("SELECT r FROM Recadv r WHERE r.wmsExpectedreceiptdate BETWEEN :startDate AND :endDate")
    List<Recadv> findByExpectedReceiptDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // Buscar por wmsStorerkey y wmsType
    List<Recadv> findByWmsStorerkeyAndWmsType(String wmsStorerkey, String wmsType);

    // Contar el número de Recadv por wmsStorerkey
    @Query("SELECT COUNT(r) FROM Recadv r WHERE r.wmsStorerkey = :wmsStorerkey")
    Long countByWmsStorerkey(@Param("wmsStorerkey") String wmsStorerkey);

    // Buscar Recadv con campos personalizados no nulos
    @Query("SELECT r FROM Recadv r WHERE r.wmsExtUdfStr1 IS NOT NULL OR r.wmsExtUdfStr2 IS NOT NULL OR r.wmsExtUdfStr3 IS NOT NULL")
    List<Recadv> findWithCustomFields();

    // Buscar el Recadv más reciente por wmsStorerkey
    @Query("SELECT r FROM Recadv r WHERE r.wmsStorerkey = :wmsStorerkey ORDER BY r.wmsExpectedreceiptdate DESC")
    List<Recadv> findMostRecentByWmsStorerkey(@Param("wmsStorerkey") String wmsStorerkey, org.springframework.data.domain.Pageable pageable);
}