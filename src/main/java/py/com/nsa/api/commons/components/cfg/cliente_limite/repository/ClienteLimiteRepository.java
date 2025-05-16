package py.com.nsa.api.commons.components.cfg.cliente_limite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.cfg.cliente_limite.model.ClienteLimite;
import py.com.nsa.api.commons.components.cfg.cliente_limite.model.pk.ClienteLimitePK;

import java.math.BigDecimal;
import java.util.Date;

@Repository
public interface ClienteLimiteRepository extends JpaRepository<ClienteLimite, ClienteLimitePK> {

    @Modifying
    @Transactional
    @Query("UPDATE ClienteLimite c SET " +
            "c.cEstado = :cEstado, " +
            "c.cliMonto = :cliMonto, " +
            "c.cliCantTransacc = :cliCantTransacc, " +
            "c.cliNombre = :cliNombre, " +
            "c.usuMod = :usuMod, " +
            "c.cliFechaMod = :cliFechaMod, " +
            "c.tlCod = :tlCod " +
            "WHERE c.cCodCliente = :cCodCliente AND c.tlCod = :tlCodold")
    int actualizarClienteLimite(
            @Param("cEstado") String cEstado,
            @Param("cliMonto") BigDecimal cliMonto,
            @Param("cliCantTransacc") Long cliCantTransacc,
            @Param("cliNombre") String cliNombre,
            @Param("usuMod") Long usuMod,
            @Param("cliFechaMod") Date cliFechaMod,
            @Param("cCodCliente") Long cCodCliente,
            @Param("tlCod") Long tlCod,
            @Param("tlCodold") Long tlCodold
    );
}