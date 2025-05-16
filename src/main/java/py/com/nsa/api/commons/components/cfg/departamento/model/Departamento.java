package py.com.nsa.api.commons.components.cfg.departamento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_DEPARTAMENTO", schema = "GNSAT")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_departamento")
    @SequenceGenerator(name = "seq_departamento", sequenceName = "GNSAT.SEQ_CFG_DEPARTAMENTO", allocationSize = 1)
    @Column(name = "DP_COD")
    private Long dpCod;

    @Column(name = "DP_DESCRIPCION")
    private String dpDescripcion;

    @Column(name = "PA_COD")
    private Long paCod;

    @ManyToOne
    @JoinColumn(name = "PA_COD", referencedColumnName = "PA_COD", insertable = false, updatable = false)
    private Pais pais;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Departamento> departamentos;
    }
}
