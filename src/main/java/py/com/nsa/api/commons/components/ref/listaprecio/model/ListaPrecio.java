package py.com.nsa.api.commons.components.ref.listaprecio.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.producto.model.Producto;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_LISTA_PRECIO", schema = "GNSAT")
public class ListaPrecio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ref_lista_precio")
    @SequenceGenerator(name = "seq_ref_lista_precio", sequenceName = "GNSAT.SEQ_REF_LISTA_PRECIO", allocationSize = 1)
    @Column(name = "LP_COD")
    private Long lpCod;

    @Column(name = "PRO_COD", length = 7, nullable = false)
    private String proCod;

    @Column(name = "PAR_LISTAPRECIO", length = 4, nullable = false)
    private String parListaPrecio;

    @Column(name = "AG_COD")
    private Long agCod;

    @Column(name = "PAR_MONEDA", length = 4, nullable = false)
    private String parMoneda;

    @Column(name = "LP_PRECIO", precision = 10, scale = 2, nullable = false)
    private BigDecimal lpPrecio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @Column(name = "LP_INICIO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date lpInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @Column(name = "LP_FIN")
    @Temporal(TemporalType.DATE)
    private Date lpFin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @Column(name = "LP_ULTMOD", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date lpUltMod;

    @Column(name = "USU_COD", nullable = false)
    private Long usuCod;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "PRO_COD", referencedColumnName = "PRO_COD", insertable = false, updatable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "AG_COD", referencedColumnName = "AG_COD", insertable = false, updatable = false)
    private Agencia agencia;

    @ManyToOne
    @JoinColumn(name = "PAR_LISTAPRECIO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorListaPrecio;

    @ManyToOne
    @JoinColumn(name = "PAR_MONEDA", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorMoneda;

    @ManyToOne
    @JoinColumn(name = "USU_COD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuario;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<ListaPrecio> listaPrecios;
    }
}