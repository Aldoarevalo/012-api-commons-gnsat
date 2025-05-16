package py.com.nsa.api.commons.components.ref.moneda.model;

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
@Table(name = "REF_MONEDA", schema = "GNSAT")
public class Moneda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_moneda")
    @SequenceGenerator(name = "seq_moneda", sequenceName = "GNSAT.SEQ_REF_MONEDA", allocationSize = 1)
    @Column(name = "MON_CODIGO")
    private Long monCodigo;

    @Column(name = "MON_NOMBRE")
    private String monNombre;

    @Column(name = "MON_ABREVIACION")
    private String monAbreviacion;

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
        private List<Moneda> moneda;
    }
}
