package py.com.nsa.api.commons.components.ref.asientos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.ref.asientos.model.Asiento;

import java.util.List;

@Repository
public interface AsientoRepository extends JpaRepository<Asiento, Long> {

    @Query("SELECT a FROM Asiento a WHERE a.vehiculo.veCod = :veCod")
    List<Asiento> findByVehiculo(@Param("veCod") Long veCod);

    @Query("SELECT a FROM Asiento a WHERE a.vasTasiento.parValor = :parValor")
    List<Asiento> findByTipoAsiento(@Param("parValor") String parValor);

    @Query("SELECT a FROM Asiento a WHERE a.vasTubicacion.parValor = :parValor")
    List<Asiento> findByTipoUbicacion(@Param("parValor") String parValor);

    @Query("SELECT a FROM Asiento a WHERE a.vehiculo.veCod IN :veCods")
    List<Asiento> findByVehiculoVeCodIn(@Param("veCods") List<Long> veCods);
}
