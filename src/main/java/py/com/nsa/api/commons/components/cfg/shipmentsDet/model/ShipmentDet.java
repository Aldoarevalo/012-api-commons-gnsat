package py.com.nsa.api.commons.components.cfg.shipmentsDet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TMS_SHIPMENTS_DET", schema = "GNSAT")
@IdClass(ShipmentDetId.class)
public class ShipmentDet {
    @Id
    @Column(name = "TMS_WHSEID", length = 30, nullable = false)
    private String tmsWhseid;

    @Id
    @Column(name = "TMS_STORERKEY", length = 15, nullable = false)
    private String tmsStorerkey;

    @Id
    @Column(name = "TMS_EXTERNORDERKEY", length = 30, nullable = false)
    private String tmsExternorderkey;

    @Id
    @Column(name = "TMS_EXTERNLINENO", length = 20, nullable = false)
    private String tmsExternlineno;

    @Column(name = "TMS_SKU", length = 50, nullable = false)
    private String tmsSku;

    @Column(name = "TMS_DESCSKU", length = 60, nullable = false)
    private String tmsDescsku;

    @Column(name = "TMS_UOM", length = 10, nullable = false)
    private String tmsUom;

    @Column(name = "TMS_ORIGINALQTY", precision = 22, scale = 2, nullable = false)
    private BigDecimal tmsOriginalqty;

    @Column(name = "TMS_WGT", precision = 22, scale = 2, nullable = false)
    private BigDecimal tmsWgt;

    @Column(name = "TMS_VOLUMEN", precision = 22, scale = 2, nullable = false)
    private BigDecimal tmsVolumen;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<ShipmentDet> shipmentDets;
    }
}