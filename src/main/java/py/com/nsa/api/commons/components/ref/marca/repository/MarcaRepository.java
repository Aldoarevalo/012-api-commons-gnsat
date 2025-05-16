package py.com.nsa.api.commons.components.ref.marca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.ref.marca.model.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    boolean existsBymarDescripcionIgnoreCase(String marDescripcion);
}
