package py.com.nsa.api.commons.components.ref.tarifaruta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.ref.tarifaruta.model.TarifaRuta;
import py.com.nsa.api.commons.components.ref.tarifaruta.model.pk.TarifaRutaPK;

@Repository
public interface TarifaRutaRepository extends JpaRepository<TarifaRuta, TarifaRutaPK> {
}