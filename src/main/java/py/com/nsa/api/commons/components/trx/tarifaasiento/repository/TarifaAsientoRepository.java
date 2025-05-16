package py.com.nsa.api.commons.components.trx.tarifaasiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.trx.tarifaasiento.model.TarifaAsiento;
import py.com.nsa.api.commons.components.trx.tarifaasiento.model.TarifaAsientoId;

import java.util.Optional;

@Repository
public interface TarifaAsientoRepository extends JpaRepository<TarifaAsiento, TarifaAsientoId> {

    boolean existsByParAsientoAndVehiculoCod(String parAsiento, Integer vehiculoCod);

    Optional<TarifaAsiento> findByParAsientoAndVehiculoCod(String parAsiento, Integer vehiculoCod);

    // Método ajustado para eliminación
    boolean existsByParAsientoAndVehiculoCodAndProCod(String parAsiento, Integer vehiculoCod, String proCod);
}