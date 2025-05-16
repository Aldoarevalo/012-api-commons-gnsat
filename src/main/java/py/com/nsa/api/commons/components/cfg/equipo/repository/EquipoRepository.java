package py.com.nsa.api.commons.components.cfg.equipo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.equipo.model.Equipo;


import java.util.List;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    @Query("SELECT e FROM Equipo e WHERE e.agCod = :agCod ORDER BY e.equCodigo")
    List<Equipo> getListaEquipos(@Param("agCod") String ageCod);

    @Query("SELECT COALESCE(MAX(e.equCodigo), 0) + 1 FROM Equipo e")
    Long getNextCodEquipo();

}
