package py.com.nsa.api.commons.components.trx.movimientoproducto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.almacen.model.Almacen;
import  py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;
import py.com.nsa.api.commons.components.ref.producto.model.Producto;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TRX_MOV_PRODUCTO", schema = "GNSAT")
public class MovimientoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movimiento_producto_generator")
    @SequenceGenerator(name = "movimiento_producto_generator", sequenceName = "GNSAT.SEQ_TRX_MOV_PRODUCTO", allocationSize = 1)
    @Column(name = "MP_COD", nullable = false)
    private Long mpCod;

    @NotNull(message = "El código de producto no puede ser nulo")
    @Column(name = "PRO_COD", length = 7, nullable = false)
    private String proCod;

    @NotNull(message = "La fecha de movimiento no puede ser nula")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @Column(name = "MP_FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date mpFecha;

    @NotNull(message = "El tipo de movimiento no puede ser nulo")
    @Column(name = "PAR_TIPO", length = 4, nullable = false)
    private String parTipo;

    @Column(name = "MP_DOC", length = 15)
    private String mpDoc;

    @Column(name = "MP_TDOC", length = 4)
    private String mpTdoc;

    @Column(name = "MP_NROLINEA")
    private Integer mpNrolinea;

    @Column(name = "MP_DESCRIPCION", length = 50)
    private String mpDescripcion;


    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    @Column(name = "MP_CANTIDAD", nullable = false)
    private Integer mpCantidad;

    @NotNull(message = "El código de almacén no puede ser nulo")
    @Column(name = "AL_COD", nullable = false)
    private Long alCod;

    @NotNull(message = "El código de usuario no puede ser nulo")
    @Column(name = "USU_COD", nullable = false)
    private Long usuCod;
    // Relaciones con otras entidades
    @ManyToOne
    @JoinColumn(name = "PRO_COD", referencedColumnName = "PRO_COD", insertable = false, updatable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "PAR_TIPO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor tipoMovimiento;

    @ManyToOne
    @JoinColumn(name = "MP_TDOC", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor tipoDocumento; // Nueva relación para mpTdoc

    @ManyToOne
    @JoinColumn(name = "AL_COD", referencedColumnName = "AL_COD", insertable = false, updatable = false)
    private Almacen almacen;

    @ManyToOne
    @JoinColumn(name = "USU_COD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuario;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<MovimientoProducto> movimientos;
    }
}