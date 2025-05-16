package py.com.nsa.api.commons.components.ref.tiposervicio.model;


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
@Table(name = "REF_TIPO_SERVICIO", schema = "GNSAT")
public class TipoServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_servicio")
    @SequenceGenerator(name = "seq_tipo_servicio", sequenceName = "GNSAT.SEQ_REF_TIPO_SERVICIO", allocationSize = 1)
    @Column(name = "TIS_CODIGO")
    private Long tisCodigo;

    @Column(name = "VEH_CODIGO")
    private Long vehCodigo;

    @Column(name = "TIS_DESCRIPCION")
    private String tisDescripcion;

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
        private List<TipoServicio> tiposervicio;
    }
}

