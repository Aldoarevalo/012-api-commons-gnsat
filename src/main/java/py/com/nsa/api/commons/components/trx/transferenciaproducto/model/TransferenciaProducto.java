package py.com.nsa.api.commons.components.trx.transferenciaproducto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario; // Importar la entidad Usuario
import py.com.nsa.api.commons.components.ref.almacen.model.Almacen;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TRX_TRANSF_PROD_CAB", schema = "GNSAT")
public class TransferenciaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transferencia_producto_generator")
    @SequenceGenerator(name = "transferencia_producto_generator", sequenceName = "GNSAT.SEQ_TRX_TRANSF_PROD_CAB", allocationSize = 1)
    @Column(name = "TRFP_COD", nullable = false)
    private Long trfpCod;

    @NotNull(message = "El tipo de transferencia no puede ser nulo")
    @Column(name = "TRFP_TIPO", length = 4, nullable = false)
    private String trfpTipo;

    @NotNull(message = "El almacén de origen no puede ser nulo")
    @Column(name = "TRFP_ORIGEN", nullable = false)
    private Long trfpOrigen;

    @NotNull(message = "El almacén de destino no puede ser nulo")
    @Column(name = "TRFP_DESTINO", nullable = false)
    private Long trfpDestino;

    @NotNull(message = "La fecha no puede ser nula")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @Column(name = "TRFP_FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date trfpFecha;

    @NotNull(message = "El estado no puede ser nulo")
    @Column(name = "TRFP_ESTADO", length = 1, nullable = false)
    private String trfpEstado;

    @Column(name = "TRFP_REF_ENV")
    private Long trfpRefEnv;

    // Nuevo campo usu_cod
    @NotNull(message = "El código de usuario no puede ser nulo")
    @Column(name = "USU_COD", nullable = false)
    private Long usuCod;

    // Relaciones con otras entidades
    @ManyToOne
    @JoinColumn(name = "TRFP_TIPO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor tipoTransferencia;

    @ManyToOne
    @JoinColumn(name = "TRFP_ORIGEN", referencedColumnName = "AL_COD", insertable = false, updatable = false)
    private Almacen almacenOrigen;

    @ManyToOne
    @JoinColumn(name = "TRFP_DESTINO", referencedColumnName = "AL_COD", insertable = false, updatable = false)
    private Almacen almacenDestino;

    @ManyToOne
    @JoinColumn(name = "TRFP_REF_ENV", referencedColumnName = "TRFP_COD", insertable = false, updatable = false)
    private TransferenciaProducto referenciaEnvio;

    // Relación con la entidad Usuario
    @ManyToOne
    @JoinColumn(name = "USU_COD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuario;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<TransferenciaProducto> transferencias;
    }
}