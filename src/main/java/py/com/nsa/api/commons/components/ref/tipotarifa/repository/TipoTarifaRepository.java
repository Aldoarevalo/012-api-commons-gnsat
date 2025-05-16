package py.com.nsa.api.commons.components.ref.tipotarifa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.ref.tipotarifa.model.TipoTarifa;

public interface TipoTarifaRepository extends JpaRepository<TipoTarifa, Long> {

    boolean existsByTitNombreAndTitDescripcionIgnoreCase(String titNombre, String titDescripcion);

}
