package py.com.nsa.api.commons.components.cfg.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.warehouse.model.Warehouse;
import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, String> {
    List<Warehouse> findByWmsWhseidAndWmsAmbiente(String wmsWhseid, String wmsAmbiente);
    List<Warehouse> findByWmsWhseid(String wmsWhseid);
    List<Warehouse> findByWmsAmbiente(String wmsAmbiente);
}
