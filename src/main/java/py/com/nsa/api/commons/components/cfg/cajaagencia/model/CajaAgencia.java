package py.com.nsa.api.commons.components.cfg.cajaagencia.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_CAJA_AGENCIA", schema = "GNSAT")
public class CajaAgencia {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "caja_agencia_generator")
    @SequenceGenerator(name = "caja_agencia_generator", sequenceName = "GNSAT.SEQ_CFG_CAJA_AGENCIA", allocationSize = 1)
    @Column(name = "CJA_COD_CAJA", nullable = false)
    private Long cjaCodCaja;

    @Column(name = "CJA_DESCRIPCION", length = 50)
    private String cjaDescripcion;

    @Column(name = "CJA_PAIS", nullable = false)
    private Long cjaPais;

    @Column(name = "CJA_CIUDAD", nullable = false)
    private Long cjaCiudad;

    @Column(name = "CJA_AGENCIA", nullable = false)
    private Long cjaAgencia;

    @Column(name = "CJA_MONEDA", length = 4, nullable = false)
    private String cjaMoneda;

    @Column(name = "CJA_TL_COD_LIMITE", nullable = false)
    private Long cjaTlCodLimite;

    @Column(name = "CJA_OPERACION", length = 1, nullable = false)
    private String cjaOperacion;

    @Column(name = "CJA_SALDO_AG", precision = 22, scale = 2)
    private BigDecimal cjaSaldoAg;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private java.util.List<CajaAgencia> cajas;
    }
}
