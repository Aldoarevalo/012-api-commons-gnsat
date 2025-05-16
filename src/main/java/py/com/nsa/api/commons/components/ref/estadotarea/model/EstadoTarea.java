package py.com.nsa.api.commons.components.ref.estadotarea.model;

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
@Table(name = "REF_ESTADO_TAREA", schema = "GNSAT")
public class EstadoTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_estado_tarea")
    @SequenceGenerator(name = "seq_estado_tarea", sequenceName = "GNSAT.SEQ_REF_ESTADO_TAREA", allocationSize = 1)
    @Column(name = "ETA_CODIGO")
    private Long etaCodigo;

    @Column(name = "ETA_NOMBRE")
    private String etaNombre;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private String status;
        private String mensaje;
        private List<EstadoTarea> estadotarea; // Debe ser List<Marca>
    }
}
