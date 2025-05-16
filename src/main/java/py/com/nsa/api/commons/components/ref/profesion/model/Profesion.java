package py.com.nsa.api.commons.components.ref.profesion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.moneda.model.Moneda;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_PROFESION", schema = "GNSAT")
public class Profesion {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_profesion")
    @SequenceGenerator(name = "seq_profesion", sequenceName = "GNSAT.SEQ_REF_PROFESION", allocationSize = 1)
    @Column(name = "PROF_CODIGO")
    private Long profCodigo;

    @Column(name = "PROF_DESCRIPCION")
    private String profDescripcion;

    @Column(name = "PROF_ABREVIATURA")
    private String profAbreviatura;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Profesion> profesions;
    }

}