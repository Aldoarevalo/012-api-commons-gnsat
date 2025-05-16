package py.com.nsa.api.commons.components.ref.empresa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.ref.empresa.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    boolean existsByEmDescripcionIgnoreCase(String emDescripcion);
    boolean existsByEmRuc(String emRuc);
}