package py.com.nsa.api.commons.components.ref.recorrido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.ref.recorrido.model.Recorrido;

public interface RecorridoRepository extends JpaRepository<Recorrido, Long> {

    boolean existsByRecNombreIgnoreCase(String recNombre);
}
