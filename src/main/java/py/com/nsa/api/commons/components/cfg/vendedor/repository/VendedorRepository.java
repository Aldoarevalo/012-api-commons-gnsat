package py.com.nsa.api.commons.components.cfg.vendedor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.vendedor.model.Vendedor;
import py.com.nsa.api.commons.components.cfg.vendedor.model.pk.VendedorPK;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, VendedorPK> {
    //añadir métodos personalizados aquí si es necesario
    @Query("SELECT COALESCE(MAX(v.vendCodigo), 0) + 1 FROM Vendedor v WHERE v.carCodigo = :carCodigo")
    Long getNextVendCodigoByCarCodigo(@Param("carCodigo") Long carCodigo);

}
