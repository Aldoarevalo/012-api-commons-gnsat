package py.com.nsa.api.commons.components.ref.marca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Table(name = "REF_MARCA", schema = "GNSAT")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_marca")
    @SequenceGenerator(name = "seq_marca", sequenceName = "GNSAT.SEQ_REF_MARCA", allocationSize = 1)
    @Column(name = "MAR_CODIGO")
    private Long marCodigo;

    @Column(name = "MAR_DESCRIPCION")
    private String marDescripcion;

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

    // @Column(name = "ROWID")
    // private String rowid;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private String status;
        private String mensaje;
        private List<Marca> marca; // Debe ser List<Marca>
    }
}
