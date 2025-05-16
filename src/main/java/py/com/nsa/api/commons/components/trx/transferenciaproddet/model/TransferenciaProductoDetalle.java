package py.com.nsa.api.commons.components.trx.transferenciaproddet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;
import py.com.nsa.api.commons.components.ref.producto.model.Producto;
import py.com.nsa.api.commons.components.trx.transferenciaproducto.model.TransferenciaProducto;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TRX_TRANSF_PROD_DET", schema = "GNSAT")
@IdClass(TransferenciaProductoDetalleId.class)
public class TransferenciaProductoDetalle {

    @Id
    @Column(name = "TRFP_COD", nullable = false)
    private Long trfpCod;

    @Id
    @Column(name = "TRFPD_LINEA", nullable = false)
    private Integer trfpdLinea;

    @NotNull(message = "El código de producto no puede ser nulo")
    @Column(name = "PRO_COD", length = 7, nullable = false)
    private String proCod;

    @NotNull(message = "La cantidad enviada no puede ser nula")
    @Min(value = 1, message = "La cantidad enviada debe ser mayor a cero")
    @Column(name = "TRFPD_CANT_ENV", nullable = false)
    private Integer trfpdCantEnv;

    @Column(name = "TRFPD_CANT_REC")
    private Integer trfpdCantRec;

    @NotNull(message = "La unidad de medida no puede ser nula")
    @Column(name = "TRFPD_UM", length = 4, nullable = false)
    private String trfpdUm;

    @Column(name = "TRFPD_REF_LINEA")
    private Integer trfpdRefLinea;

    // Relaciones con otras entidades
    @ManyToOne
    @JoinColumn(name = "PRO_COD", referencedColumnName = "PRO_COD", insertable = false, updatable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "TRFPD_UM", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor unidadMedida;

    @ManyToOne
    @JoinColumn(name = "TRFP_COD", referencedColumnName = "TRFP_COD", insertable = false, updatable = false)
    private TransferenciaProducto transferenciaProducto;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<TransferenciaProductoDetalle> detalles;
    }
}