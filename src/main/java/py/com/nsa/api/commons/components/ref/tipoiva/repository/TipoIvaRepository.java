package py.com.nsa.api.commons.components.ref.tipoiva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.ref.tipoiva.modelo.TipoIva;

public interface TipoIvaRepository extends JpaRepository<TipoIva, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
