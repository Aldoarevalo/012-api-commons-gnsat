package py.com.nsa.api.commons.components.cfg.trx_cumplimiento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TRX_CUMPLIMIENTO", schema = "GNSAT")
public class Cumplimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cumplimiento_generator")
    @SequenceGenerator(name = "cumplimiento_generator", sequenceName = "GNSAT.SEQ_TRX_CUMPLIMIENTO", allocationSize = 1)
    @Column(name = "CU_COD")
    private Long cuCod;

    @Column(name = "AGE_COD")
    private Long ageCod;

    @Column(name = "CU_FECHA")
    private Date cuFecha;

    @Column(name = "CU_AUDITOR")
    private String cuAuditor;

    @Column(name = "CU_TIPO")
    private String cuTipo;

    @ManyToOne
    @JoinColumn(name = "AGE_COD", referencedColumnName = "AG_COD", insertable = false, updatable = false)
    private Agencia agencia;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Cumplimiento> cumplimientos;
    }
}
