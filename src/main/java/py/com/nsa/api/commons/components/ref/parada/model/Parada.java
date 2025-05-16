package py.com.nsa.api.commons.components.ref.parada.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_PARADA", schema = "GNSAT")
public class Parada {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parada_generator")
    @SequenceGenerator(name = "parada_generator", sequenceName = "GNSAT.SEQ_REF_PARADA", allocationSize = 1)
    @Column(name = "PARA_COD")
    private Long paraCod;

    @Column(name = "PARA_DESCRIPCION")
    private String paraDescripcion;

    @Column(name = "AG_COD")
    private Long agCod;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private java.util.List<Parada> paradas;
    }
}
