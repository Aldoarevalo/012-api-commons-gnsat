package py.com.nsa.api.commons.components.cfg.comision.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAR_COMISION", schema = "GNSAT")
public class Comision {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_par_comision")
    @SequenceGenerator(name = "seq_par_comision", sequenceName = "GNSAT.SEQ_PAR_COMISION", allocationSize = 1)
    @Column(name = "COM_COD")
    private Integer comCod;

    @NotBlank(message = "El tipo de transacción es requerido")
    @Column(name = "COM_TIPO_TRANSAC", nullable = false)
    private String comTipoTransac;

    @Column(name = "COM_MONTO_INICIAL")
    private BigDecimal comMontoInicial;

    @Column(name = "COM_MONTO_FINAL")
    private BigDecimal comMontoFinal;

    @Column(name = "COM_PORCENTAJE")
    private Float comPorcentaje;

    @Column(name = "COM_MONTOFIJO")
    private BigDecimal comMontoFijo;

    @NotBlank(message = "La moneda es requerida")
    @Size(max = 4, message = "La moneda no puede exceder los 4 caracteres")
    @Column(name = "COM_MONEDA", nullable = false, length = 4)
    private String comMoneda;

    @Column(name = "COM_COD_AG")
    private Integer comCodAg;

    @ManyToOne
    @JoinColumn(name = "COM_COD_AG", referencedColumnName = "AG_COD", insertable = false, updatable = false)
    private Agencia agencia;

    @NotBlank(message = "El estado es requerido")
    @Size(max = 1, message = "El estado no puede exceder 1 caracter")
    @Column(name = "COM_ESTADO", nullable = false, length = 1)
    private String comEstado;

    @NotNull(message = "El usuario de grabación es requerido")
    @Column(name = "COD_USU_GRAB", nullable = false)
    private Integer codUsuGrab;

    @ManyToOne
    @JoinColumn(name = "COD_USU_GRAB", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuarioGrab;

    @NotNull(message = "El usuario de modificación es requerido")
    @Column(name = "COD_USU_MOD", nullable = false)
    private Integer codUsuMod;

    @ManyToOne
    @JoinColumn(name = "COD_USU_MOD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuarioMod;

    @NotNull(message = "La fecha de grabación es requerida")
    @Column(name = "COD_FECHA_GRAB", nullable = false)
    private Date codFechaGrab;

    @NotNull(message = "La fecha de modificación es requerida")
    @Column(name = "COD_FECHA_MOD", nullable = false)
    private Date codFechaMod;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Comision> comisiones;
    }
}
