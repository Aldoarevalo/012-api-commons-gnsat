package py.com.nsa.api.commons.components.cfg.warehouse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "WMS_WAREHOUSE", schema = "GNSAT")
public class Warehouse {

    @Id
    @Column(name = "WMS_WHSEID", length = 30)
    private String wmsWhseid;

    @Column(name = "WMS_WAREHOUSE", length = 100)
    private String wmsWarehouse;

    @Column(name = "WMS_AMBIENTE", length = 30)
    private String wmsAmbiente;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Warehouse> warehouses;
    }
}