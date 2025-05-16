package py.com.nsa.api.commons.components.trx.viajechofer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.trx.viajechofer.model.ViajeChofer;

import java.util.List;

public interface ViajeChoferRepository extends JpaRepository<ViajeChofer, ViajeChofer.IdClass> {

    @Query("SELECT vc FROM ViajeChofer vc WHERE vc.vCod = :vCod")
    List<ViajeChofer> findByVCod(@Param("vCod") Integer vCod);

    @Query("SELECT vc FROM ViajeChofer vc WHERE vc.eCod = :eCod")
    List<ViajeChofer> findByECod(@Param("eCod") Long eCod);

    @Query("SELECT vc FROM ViajeChofer vc WHERE vc.vCod = :vCod AND vc.eCod = :eCod")
    List<ViajeChofer> findByVCodAndECod(@Param("vCod") Integer vCod, @Param("eCod") Long eCod);

    @Query("SELECT CASE WHEN COUNT(vc) > 0 THEN true ELSE false END FROM ViajeChofer vc WHERE vc.vCod = :vCod AND vc.eCod = :eCod")
    boolean existsByVCodAndECod(@Param("vCod") Integer vCod, @Param("eCod") Long eCod);
}