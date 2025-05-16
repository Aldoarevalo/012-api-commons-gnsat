package py.com.nsa.api.commons.components.cfg.cotizacion.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PAR_COTIZACION", schema = "GNSAT")
public class Cotizacion {

    // Definición de mapeo
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_par_cotizacion")
    @SequenceGenerator(name = "seq_par_cotizacion", sequenceName = "GNSAT.SEQ_PAR_COTIZACION", allocationSize = 1)
    @Column(name = "COT_COD")
    private Integer cotCod;

    @NotBlank(message = "La moneda es requerida")
    @Size(max = 4, message = "La moneda no puede exceder los 4 caracteres")
    @Column(name = "COT_MONEDA", nullable = false, length = 4)
    private String cotMoneda;

    @NotBlank(message = "El tipo de transacción es requerido")
    @Size(max = 4, message = "El tipo de transacción no puede exceder los 4 caracteres")
    @Column(name = "COT_TIPO_TRANSAC", nullable = false, length = 4)
    private String cotTipoTransac;

    @NotNull(message = "El valor de cambio es requerido")
    @Column(name = "COT_VALOR", nullable = false)
    private BigDecimal cotValor;

    @NotBlank(message = "El estado es requerido")
    @Size(max = 1, message = "El estado no puede exceder 1 caracter")
    @Column(name = "COT_ESTADO", nullable = false, length = 1)
    private String cotEstado;

    @NotNull(message = "La fecha de vigencia es requerida")
    @Column(name = "COT_FECHAVIGENCIA", nullable = false)
    private Date cotFechaVigencia;

    @Column(name = "COT_FECHAFINCOTI")
    private Date cotFechaFinCoti;

    @NotNull(message = "El usuario de grabación es requerido")
    @Column(name = "COT_USU_GRAB", nullable = false)
    private Integer cotUsuGrab;

    @NotNull(message = "La fecha de grabación es requerida")
    @Column(name = "COT_FECHAGRAB", nullable = false)
    private Date cotFechaGrab;

    @NotNull(message = "La fecha de modificación es requerida")
    @Column(name = "COT_FECHAMOD", nullable = false)
    private Date cotFechaMod;

    @NotNull(message = "El usuario de modificación es requerido")
    @Column(name = "COT_USU_MOD", nullable = false)
    private Integer cotUsuMod;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Cotizacion> cotizaciones;
    }
}
