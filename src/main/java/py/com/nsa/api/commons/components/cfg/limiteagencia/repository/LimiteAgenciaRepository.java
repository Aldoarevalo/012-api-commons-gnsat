package py.com.nsa.api.commons.components.cfg.limiteagencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.cfg.limiteagencia.model.LimiteAgencia;
import py.com.nsa.api.commons.components.cfg.limiteagencia.model.pk.LimiteAgenciaPK;

import java.math.BigDecimal;
import java.util.Date;

public interface LimiteAgenciaRepository extends JpaRepository<LimiteAgencia, LimiteAgenciaPK> {

    @Modifying
    @Transactional
    @Query("UPDATE LimiteAgencia l SET l.laMonto = :laMonto, l.laCantidad = :laCantidad, " +
            "l.agCod = :agCod, l.tlCodLimite = :tlCodLimite, " +
            "l.usuCodMod = :usuCodMod, l.laFechaMod = :laFechaMod " +
            "WHERE l.agCod = :agCodOld AND l.tlCodLimite = :tlCodLimiteOld")
    int actualizarLimiteAgencia(
            @Param("laMonto") BigDecimal laMonto,
            @Param("laCantidad") Long laCantidad,
            @Param("agCod") Long agCod,
            @Param("tlCodLimite") Long tlCodLimite,
            @Param("usuCodMod") Long usuCodMod,
            @Param("laFechaMod") Date laFechaMod,
            @Param("agCodOld") Long agCodOld,
            @Param("tlCodLimiteOld") Long tlCodLimiteOld
    );


}
