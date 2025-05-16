package py.com.nsa.api.commons.components.ref.parametro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import py.com.nsa.api.commons.components.ref.parametro.model.Parametro;
import py.com.nsa.api.commons.components.ref.parametro.model.pk.ParametroPK;

public interface ParametroRepository extends JpaRepository<Parametro, ParametroPK> {

    /*// Usar una consulta nativa para obtener el siguiente valor de la secuencia
    @Query(value = "SELECT GNSAT.SEQ_PAR_PARAMETRO.NEXTVAL FROM dual", nativeQuery = true)
    Long getNextPmCod();*/

    boolean existsByPmNombreIgnoreCase(String pmNombre);

}
