package py.com.nsa.api.commons.components.ref.recorrido.model;

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
@Table(name = "REF_RECORRIDO", schema = "GNSAT")
public class Recorrido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_recorrido")
    @SequenceGenerator(name = "seq_recorrido", sequenceName = "GNSAT.SEQ_REF_RECORRIDO", allocationSize = 1)
    @Column(name = "REC_CODIGO")
    private Long recCodigo;

    @Column(name = "REC_DESCRIPCION")
    private String recNombre;

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
        private List<Recorrido> recorridos;
    }
}
