package py.com.nsa.api.commons.components.cfg.cobrador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.cobrador.model.Cobrador;
import py.com.nsa.api.commons.components.cfg.cobrador.model.pk.CobradorPK;

@Repository
public interface CobradorRepository extends JpaRepository<Cobrador, CobradorPK> {
    //agregar métodos personalizados aquí si es necesario
    @Query("SELECT COALESCE(MAX(c.cobrCodigo), 0) + 1 FROM Cobrador c WHERE c.carCodigo = :carCodigo")
    Long getNextCobrCodigoByCarCodigo(@Param("carCodigo") Long carCodigo);
}
