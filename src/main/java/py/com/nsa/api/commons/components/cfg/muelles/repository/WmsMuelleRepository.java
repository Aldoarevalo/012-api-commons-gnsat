package py.com.nsa.api.commons.components.cfg.muelles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.muelles.model.WmsMuelle;

import java.util.List;

@Repository
public interface WmsMuelleRepository extends JpaRepository<WmsMuelle, Long> {

    /**
     * Consulta que devuelve todos los muelles ordenados por distancia desde las coordenadas especificadas.
     * La distancia se calcula utilizando la fórmula de Haversine.
     */
    /**
     * En Oracle, necesitamos convertir grados a radianes manualmente usando la fórmula:
     * radianes = grados * PI() / 180
     *
     * La fórmula de Haversine adaptada para Oracle sería:
     */
    @Query(value =
            "SELECT m.*, " +
                    "6371 * 2 * ASIN(SQRT(" +
                    "    POWER(SIN((((?1) * 3.1415926535 / 180) - (m.WMS_LATITUD * 3.1415926535 / 180)) / 2), 2) + " +
                    "    COS(m.WMS_LATITUD * 3.1415926535 / 180) * COS((?1) * 3.1415926535 / 180) * " +
                    "    POWER(SIN((((?2) * 3.1415926535 / 180) - (m.WMS_LONGITUD * 3.1415926535 / 180)) / 2), 2)" +
                    ")) AS distancia " +
                    "FROM GNSAT.WMS_MUELLES m " +
                    "ORDER BY distancia",
            nativeQuery = true)
    List<Object[]> findAllOrderByDistance(Double latitud, Double longitud);
}