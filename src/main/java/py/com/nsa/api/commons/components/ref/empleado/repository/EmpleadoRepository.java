package py.com.nsa.api.commons.components.ref.empleado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import py.com.nsa.api.commons.components.ref.empleado.model.Empleado;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    boolean existsByPcod(Long pcod);

    // Método para encontrar empleados cuyos eCod no estén en la lista proporcionada
    // List<Empleado> findAllByEcCodNotIn(List<Long> ecCods);
    public List<Empleado> findByPersonaPcodIn(List<Long> pcods);


}
