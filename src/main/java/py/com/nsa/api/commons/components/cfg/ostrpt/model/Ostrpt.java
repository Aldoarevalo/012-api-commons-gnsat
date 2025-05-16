package py.com.nsa.api.commons.components.cfg.ostrpt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "WMS_OSTRPT", schema = "GNSAT")
@IdClass(OstrptId.class)
public class Ostrpt {
    @Id
    @Column(name = "WMS_STORERKEY", length = 15, nullable = false)
    private String wmsStorerkey;

    @Id
    @Column(name = "WMS_EXTERNORDERKEY", length = 15, nullable = false)
    private String wmsExternorderkey;

    @Column(name = "WMS_TYPE", length = 10)
    private String wmsType;

    @Column(name = "WMS_SUSR1", length = 30)
    private String wmsSusr1;

    @Column(name = "WMS_SUSR2", length = 30)
    private String wmsSusr2;

    @Column(name = "WMS_SUSR3", length = 30)
    private String wmsSusr3;

    @Column(name = "WMS_SUSR4", length = 30)
    private String wmsSusr4;

    @Column(name = "WMS_SUSR5", length = 30)
    private String wmsSusr5;

    @Column(name = "WMS_EXT_UDF_STR1", length = 128)
    private String wmsExtUdfStr1;

    @Column(name = "WMS_EXT_UDF_STR2", length = 128)
    private String wmsExtUdfStr2;

    @Column(name = "WMS_EXT_UDF_STR3", length = 128)
    private String wmsExtUdfStr3;

    @Column(name = "WMS_EXT_UDF_STR4", length = 128)
    private String wmsExtUdfStr4;

    @Column(name = "WMS_EXT_UDF_STR5", length = 128)
    private String wmsExtUdfStr5;

    @Column(name = "WMS_EXT_UDF_FLOAT1", precision = 22, scale = 2)
    private BigDecimal wmsExtUdfFloat1;

    @Column(name = "WMS_EXT_UDF_FLOAT2", precision = 22, scale = 2)
    private BigDecimal wmsExtUdfFloat2;

    @Column(name = "WMS_EXT_UDF_FLOAT3", precision = 22, scale = 2)
    private BigDecimal wmsExtUdfFloat3;

    @Column(name = "WMS_EXT_UDF_FLOAT4", precision = 22, scale = 2)
    private BigDecimal wmsExtUdfFloat4;

    @Column(name = "WMS_EXT_UDF_FLOAT5", precision = 22, scale = 2)
    private BigDecimal wmsExtUdfFloat5;

    @Column(name = "WMS_EXT_UDF_DATE1")
    @Temporal(TemporalType.DATE)
    private Date wmsExtUdfDate1;

    @Column(name = "WMS_EXT_UDF_DATE2")
    @Temporal(TemporalType.DATE)
    private Date wmsExtUdfDate2;

    @Column(name = "WMS_EXT_UDF_DATE3")
    @Temporal(TemporalType.DATE)
    private Date wmsExtUdfDate3;

    @Column(name = "WMS_EXT_UDF_DATE4")
    @Temporal(TemporalType.DATE)
    private Date wmsExtUdfDate4;

    @Column(name = "WMS_EXT_UDF_DATE5")
    @Temporal(TemporalType.DATE)
    private Date wmsExtUdfDate5;

    @Column(name = "WMS_CONSIGNEEKEY", length = 15)
    private String wmsConsigneekey;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Ostrpt> ostrpts;
    }
}