package py.com.nsa.api.commons.components.ref.cargo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.grupocargo.model.GrupoCargo;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_CARGO", schema = "GNSAT")
public class Cargo {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cargo")
    @SequenceGenerator(name = "seq_cargo", sequenceName = "GNSAT.SEQ_REF_CARGO", allocationSize = 1)
    @Column(name = "CAR_CODIGO")
    private Long carCodigo;

    @Column(name = "CAR_DESCRIPCION")
    private String carDescripcion;

    @Column(name = "GCA_CODIGO")
    private Long gcaCodigo;

    @ManyToOne
    @JoinColumn(name = "GCA_CODIGO", referencedColumnName = "GCA_CODIGO", insertable = false, updatable = false)
    private GrupoCargo grupoCargo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Cargo> cargos;
    }
}