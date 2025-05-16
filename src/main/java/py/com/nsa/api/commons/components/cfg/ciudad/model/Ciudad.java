package py.com.nsa.api.commons.components.cfg.ciudad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.departamento.model.Departamento;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_CIUDAD", schema = "GNSAT")
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ciudad")
    @SequenceGenerator(name = "seq_ciudad", sequenceName = "GNSAT.SEQ_CFG_CIUDAD", allocationSize = 1)
    @Column(name = "CIU_COD")
    private Long ciuCod;

    @Column(name = "CIU_DESCRIPCION")
    private String ciuDescripcion;

    @Column(name = "DP_COD")
    private Long dpCod;

    @ManyToOne
    @JoinColumn(name = "DP_COD", referencedColumnName = "DP_COD", insertable = false, updatable = false)
    private Departamento departamento;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Ciudad> ciudades;
    }
}
