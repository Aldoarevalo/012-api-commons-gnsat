package py.com.nsa.api.commons.components.ref.empleado.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;
import py.com.nsa.api.commons.components.ref.persona.model.Persona;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_EMPLEADO", schema = "GNSAT")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ref_empleado")
    @SequenceGenerator(name = "seq_ref_empleado", sequenceName = "GNSAT.SEQ_REF_EMPLEADO", allocationSize = 1)
    @Column(name = "E_COD")
    private Long eCod;

    @Column(name = "P_COD")
    private Long pcod;

    @Column(name = "E_ESPROPIO")
    private String eEsPropio;

    @Column(name = "E_FECH_CONTRATACION")
    @Temporal(TemporalType.DATE)
    private Date eFechContratacion;

    @Column(name = "PAR_CARGO")
    private String cCodCargo;

    @OneToOne
    @JoinColumn(name = "P_COD", referencedColumnName = "P_COD", insertable = false, updatable = false)
    private Persona persona;

    @OneToOne
    @JoinColumn(name = "PAR_CARGO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parvalor;

    @Transient
    private Long oldpcod;

    @Transient
    private Long nuevapcod;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Empleado> empleados;
    }
}
