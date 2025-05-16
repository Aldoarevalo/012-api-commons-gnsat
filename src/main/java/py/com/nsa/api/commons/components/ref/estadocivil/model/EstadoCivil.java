package py.com.nsa.api.commons.components.ref.estadocivil.model;

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
@Table(name = "REF_ESTADO_CIVIL", schema = "GNSAT")
public class EstadoCivil {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_estado_civil")
    @SequenceGenerator(name = "seq_estado_civil", sequenceName = "GNSAT.SEQ_REF_ESTADO_CIVIL", allocationSize = 1)
    @Column(name = "ECI_CODIGO")
    private Long eciCodigo;

    @Column(name = "ECI_DESCRIPCION")
    private String eciDescripcion;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<EstadoCivil> estadosciviles;
    }
}