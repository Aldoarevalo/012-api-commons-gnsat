package py.com.nsa.api.commons.components.ref.estadotarea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.ref.estadotarea.model.EstadoTarea;

public interface EstadoTareaRepository extends JpaRepository<EstadoTarea, Long> {
    boolean existsByEtaNombreIgnoreCase(String etaNombre);
}
