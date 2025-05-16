package py.com.nsa.api.commons.components.trx.viaje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.trx.viaje.model.Viaje;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Integer> {

    @Query("SELECT v FROM Viaje v WHERE v.vCod = :vCod")
    Optional<Viaje> findByVCod(@Param("vCod") Integer vCod);

    @Query("SELECT v FROM Viaje v WHERE v.vFecha = :vFecha")
    List<Viaje> findByVFecha(@Param("vFecha") Date vFecha);

    @Query("SELECT v FROM Viaje v WHERE v.rucCod = :rucCod")
    List<Viaje> findByRucCod(@Param("rucCod") Long rucCod); // Ajustado a Long

    @Query("SELECT v FROM Viaje v WHERE v.proCod = :proCod")
    List<Viaje> findByProCod(@Param("proCod") String proCod);

    @Query("SELECT v FROM Viaje v WHERE v.vEmpresa = :vEmpresa")
    List<Viaje> findByVEmpresa(@Param("vEmpresa") Long vEmpresa); // Ajustado a Long

    @Query("SELECT v FROM Viaje v WHERE v.vEstado = :vEstado")
    List<Viaje> findByVEstado(@Param("vEstado") String vEstado);

    // Método corregido para coincidir con el nombre del campo vCod
    boolean existsByvCod(Integer vCod); // Cambiado de existsByVCod a existsByvCod
}