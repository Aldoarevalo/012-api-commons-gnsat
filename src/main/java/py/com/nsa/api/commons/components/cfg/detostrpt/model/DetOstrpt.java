package py.com.nsa.api.commons.components.cfg.detostrpt.model;

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
@Table(name = "WMS_DET_OSTRPT", schema = "GNSAT")
@IdClass(DetOstrptId.class)
public class DetOstrpt {
    @Id
    @Column(name = "WMS_STORERKEY", length = 15, nullable = false)
    private String wmsStorerkey;

    @Id
    @Column(name = "WMS_EXTERNORDERKEY", length = 55, nullable = false)
    private String wmsExternorderkey;

    @Id
    @Column(name = "WMS_IDDET", nullable = false)
    private Long wmsIddet;

    @Column(name = "WMS_EXTERNLINENO", length = 20)
    private String wmsExternlineno;

    @Column(name = "WMS_SKU", length = 20)
    private String wmsSku;

    @Column(name = "WMS_PICKEDQTY", precision = 22, scale = 2)
    private BigDecimal wmsPickedqty;

    @Column(name = "WMS_DUOM", length = 10)
    private String wmsDuom;

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

    @Column(name = "WMS_LOTTABLE01", length = 50)
    private String wmsLottable01;

    @Column(name = "WMS_LOTTABLE02", length = 50)
    private String wmsLottable02;

    @Column(name = "WMS_LOTTABLE03", length = 50)
    private String wmsLottable03;

    @Column(name = "WMS_LOTTABLE04", length = 50)
    private String wmsLottable04;

    @Column(name = "WMS_LOTTABLE05", length = 50)
    private String wmsLottable05;

    @Column(name = "WMS_LOTTABLE06", length = 50)
    private String wmsLottable06;

    @Column(name = "WMS_LOTTABLE07", length = 50)
    private String wmsLottable07;

    @Column(name = "WMS_LOTTABLE08", length = 50)
    private String wmsLottable08;

    @Column(name = "WMS_LOTTABLE09", length = 50)
    private String wmsLottable09;

    @Column(name = "WMS_LOTTABLE10", length = 50)
    private String wmsLottable10;

    @Column(name = "WMS_LOTTABLE11", length = 50)
    private String wmsLottable11;

    @Column(name = "WMS_LOTTABLE12", length = 50)
    private String wmsLottable12;

    @Column(name = "WMS_WGT", precision = 22, scale = 2)
    private BigDecimal wmsWgt;

    @Column(name = "WMS_VOLUMEN", precision = 22, scale = 2)
    private BigDecimal wmsVolumen;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<DetOstrpt> detOstrpts;
    }
}