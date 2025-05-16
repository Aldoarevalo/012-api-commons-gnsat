package py.com.nsa.api.commons.components.cfg.servicio_agencia.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.servicio_agencia.model.ServicioAgencia;
import py.com.nsa.api.commons.components.cfg.servicio_agencia.model.pk.ServicioAgenciaPK;

import java.util.List;


@Repository
public interface ServicioAgenciaRepository extends JpaRepository<ServicioAgencia, ServicioAgenciaPK> {
    List<ServicioAgencia> findByAgCod(Long agCod);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM GNSAT.CFG_SERVICIO_AGENCIA sa WHERE sa.PAR_SERVICIO = :parServicio AND sa.AG_COD = :agCod", nativeQuery = true)
    void deleteById(@Param("parServicio") String parServicio, @Param("agCod") Long agCod);
}