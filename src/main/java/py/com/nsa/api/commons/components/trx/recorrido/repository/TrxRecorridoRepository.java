package py.com.nsa.api.commons.components.trx.recorrido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.trx.recorrido.model.TrxRecorrido;
import py.com.nsa.api.commons.components.trx.recorrido.model.TrxRecorridoId;

@Repository
public interface TrxRecorridoRepository extends JpaRepository<TrxRecorrido, TrxRecorridoId> {
}