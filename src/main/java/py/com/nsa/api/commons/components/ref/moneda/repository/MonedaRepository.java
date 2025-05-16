package py.com.nsa.api.commons.components.ref.moneda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.ref.moneda.model.Moneda;

public interface MonedaRepository extends JpaRepository<Moneda, Long> {
    boolean existsByMonAbreviacionIgnoreCase(String monAbreviacion);

    boolean existsByMonNombreIgnoreCase(String monNombre);
}
