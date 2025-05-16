package py.com.nsa.api.commons.components.cfg.nsakey_equipo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.nsakey_equipo.model.NsakeyEquipo;
import java.util.List;

@Repository
public interface NsakeyEquipoRepository extends JpaRepository<NsakeyEquipo, Long> {

    @Query("SELECT e FROM NsakeyEquipo e WHERE e.cpuModelo = :cpuModelo " +
            "AND (e.macAddress1 = :macAddress OR e.macAddress2 = :macAddress OR " +
            "e.macAddress3 = :macAddress OR e.macAddress4 = :macAddress OR " +
            "e.macAddress5 = :macAddress)")
    List<NsakeyEquipo> findByModeloAndMacAddress(
            @Param("cpuModelo") String cpuModelo,
            @Param("macAddress") String macAddress
    );
}
