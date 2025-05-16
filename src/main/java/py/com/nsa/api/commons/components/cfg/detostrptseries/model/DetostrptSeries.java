package py.com.nsa.api.commons.components.cfg.detostrptseries.model;

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
@Table(name = "WMS_DETOSTRPT_SERIES", schema = "GNSAT")
@IdClass(DetostrptSeriesId.class)
public class DetostrptSeries {
    @Id
    @Column(name = "WMS_STORERKEY", length = 15, nullable = false)
    private String wmsStorerkey;

    @Id
    @Column(name = "WMS_EXTERNORDERKEY", length = 55, nullable = false)
    private String wmsExternorderkey;

    @Id
    @Column(name = "WMS_IDDET", nullable = false)
    private Long wmsIddet;

    @Id
    @Column(name = "WMS_IDSERIE", nullable = false)
    private Long wmsIdserie;

    @Column(name = "WMS_LPN", length = 50, nullable = false)
    private String wmsLpn;

    @Column(name = "WMS_SERIALNUMBER", length = 30, nullable = false)
    private String wmsSerialNumber;

    @Column(name = "WMS_SERIALNUMBERLONG", length = 500)
    private String wmsSerialNumberLong;

    @Column(name = "WMS_IOTHER1", length = 30)
    private String wmsIother1;

    @Column(name = "WMS_IOTHER2", length = 30)
    private String wmsIother2;

    @Column(name = "WMS_IOTHER3", length = 30)
    private String wmsIother3;

    @Column(name = "WMS_IOTHER4", length = 30)
    private String wmsIother4;

    @Column(name = "WMS_IOTHER5", length = 30)
    private String wmsIother5;

    @Column(name = "WMS_WGT", precision = 22, scale = 5)
    private BigDecimal wmsWgt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<DetostrptSeries> detostrptSeries;
    }
}