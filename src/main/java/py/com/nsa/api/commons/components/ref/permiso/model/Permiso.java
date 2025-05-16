package py.com.nsa.api.commons.components.ref.permiso.model;

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
@Table(name = "REF_PERMISO", schema = "GNSAT")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_permiso")
    @SequenceGenerator(name = "seq_permiso", sequenceName = "GNSAT.SEQ_REF_PERMISO", allocationSize = 1)
    @Column(name = "PER_COD")
    private Long perCod;

    @Column(name = "PER_NOMBRE")
    private String perNombre;

    @Column(name = "CAMPO_1")
    private String campo1;

    @Column(name = "CAMPO_2")
    private String campo2;

    @Column(name = "CAMPO_3")
    private String campo3;

    @Column(name = "CAMPO_4")
    private String campo4;

    @Column(name = "CAMPO_5")
    private String campo5;

    @Column(name = "CAMPO_6")
    private String campo6;

    @Column(name = "CAMPO_7")
    private String campo7;

    @Column(name = "CAMPO_8")
    private String campo8;

    @Column(name = "CAMPO_9")
    private String campo9;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private String status;
        private String mensaje;
        private List<Permiso> permiso;

    }
}
