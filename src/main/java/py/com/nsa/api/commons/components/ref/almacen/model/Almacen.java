package py.com.nsa.api.commons.components.ref.almacen.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_ALMACEN", schema = "GNSAT")
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "almacen_generator")
    @SequenceGenerator(name = "almacen_generator", sequenceName = "GNSAT.SEQ_REF_ALMACEN", allocationSize = 1)
    @Column(name = "AL_COD", nullable = false)
    private Long alCod;

    @Column(name = "AL_DESCRIPCION", length = 10, nullable = false)
    private String alDescripcion;

    @Column(name = "AG_COD", nullable = false)
    private Long agCod;

    @ManyToOne
    @JoinColumn(name = "AG_COD", referencedColumnName = "AG_COD", insertable = false, updatable = false)
    private Agencia agencia;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Almacen> almacenes;
    }
}