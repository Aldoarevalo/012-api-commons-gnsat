package py.com.nsa.api.commons.components.ref.asientos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;
import py.com.nsa.api.commons.components.ref.vehiculo.model.Vehiculo;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_ASIENTO", schema = "GNSAT")
public class Asiento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asiento_generator")
    @SequenceGenerator(name = "asiento_generator", sequenceName = "GNSAT.SEQ_REF_ASIENTOS", allocationSize = 1)
    @Column(name = "VAS_COD")
    private Long vasCod;

    @ManyToOne
    @JoinColumn(name = "VE_COD", referencedColumnName = "VE_COD", nullable = false)
    @JsonBackReference
    private Vehiculo vehiculo;

    @Column(name = "VAS_NROASIENTO")
    private Integer vasNroAsiento;

    @ManyToOne
    @JoinColumn(name = "VAS_TASIENTO", referencedColumnName = "PAR_VALOR", nullable = false)
    private ParValor vasTasiento;

    @ManyToOne
    @JoinColumn(name = "VAS_TUBICACION", referencedColumnName = "PAR_VALOR", nullable = false)
    private ParValor vasTubicacion;

    @Column(name = "VAS_PISO")
    private Integer vasPiso;

    @Column(name = "VAS_FILA")
    private Long vasFila;

    @Column(name = "VAS_COLUMNA")
    private Long vasColumna;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Asiento> asientos;
    }
}
