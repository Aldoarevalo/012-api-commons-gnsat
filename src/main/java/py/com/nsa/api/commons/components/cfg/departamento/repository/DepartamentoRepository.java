package py.com.nsa.api.commons.components.cfg.departamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.cfg.departamento.model.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    boolean existsByDpDescripcionIgnoreCaseAndPaCod(String dpDescripcion, Long paCod);
}
