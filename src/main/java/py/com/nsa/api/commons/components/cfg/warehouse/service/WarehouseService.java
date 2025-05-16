package py.com.nsa.api.commons.components.cfg.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.warehouse.model.Warehouse;
import py.com.nsa.api.commons.components.cfg.warehouse.repository.WarehouseRepository;
import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public Warehouse.MensajeRespuesta getWarehouseByFilters(String wmsWhseid, String wmsAmbiente) {
        try {
            List<Warehouse> warehouses;

            if (wmsWhseid != null && wmsAmbiente != null) {
                warehouses = warehouseRepository.findByWmsWhseidAndWmsAmbiente(wmsWhseid, wmsAmbiente);
            } else if (wmsWhseid != null) {
                warehouses = warehouseRepository.findByWmsWhseid(wmsWhseid);
            } else if (wmsAmbiente != null) {
                warehouses = warehouseRepository.findByWmsAmbiente(wmsAmbiente);
            } else {
                warehouses = warehouseRepository.findAll();
            }

            if (warehouses.isEmpty()) {
                return new Warehouse.MensajeRespuesta(204L, "No se encontraron almacenes con los filtros especificados.", null);
            }
            return new Warehouse.MensajeRespuesta(200L, "Almacenes obtenidos exitosamente.", warehouses);
        } catch (Exception e) {
            return new Warehouse.MensajeRespuesta(500L, "Error al obtener los almacenes: " + e.getMessage(), null);
        }
    }
}