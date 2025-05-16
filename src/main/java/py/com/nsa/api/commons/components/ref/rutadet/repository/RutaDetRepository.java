package py.com.nsa.api.commons.components.ref.rutadet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import py.com.nsa.api.commons.components.ref.rutadet.model.RutaDet;
import py.com.nsa.api.commons.components.ref.rutadet.model.pk.RutaDetPK;

import java.util.List;

@Repository
public interface RutaDetRepository extends JpaRepository<RutaDet, RutaDetPK> {
    List<RutaDet> findByRucCod(Long rucCod);

        @Modifying
        @Query("DELETE FROM RutaDet rd WHERE rd.rucCod = :rucCod")
        void deleteByRucCod(@Param("rucCod") Long rucCod);


    @Modifying
    @Transactional
    @Query(value = "UPDATE GNSAT.REF_RUTA_DET SET " +
            "RUD_SECUENCIA = :newRudSecuencia, " +
            "PARA_COD = :paraCod, " +
            "TR_TIEMPO = :trTiempo " +
            "WHERE RUC_COD = :rucCodOld AND RUD_SECUENCIA = :rudSecuenciaOld",
            nativeQuery = true)
    int updateRutaDetNative(
            @Param("newRudSecuencia") Long newRudSecuencia,
            @Param("paraCod") Long paraCod,
            @Param("trTiempo") String trTiempo,
            @Param("rucCodOld") Long rucCodOld,
            @Param("rudSecuenciaOld") Long rudSecuenciaOld
    );

}