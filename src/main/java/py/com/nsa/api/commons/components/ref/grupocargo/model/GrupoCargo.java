package py.com.nsa.api.commons.components.ref.grupocargo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_GRUPO_CARGO", schema = "GNSAT")
public class GrupoCargo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_grupo_cargo")
    @SequenceGenerator(name = "seq_grupo_cargo", sequenceName = "GNSAT.SEQ_REF_GRUPO_CARGO", allocationSize = 1)
    @Column(name = "GCA_CODIGO")
    private Integer gcaCodigo;

    @Column(name = "GCA_DESCRIPCION")
    private String gcaDescripcion;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<GrupoCargo> gruposCargos;
    }
}
