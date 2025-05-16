package py.com.nsa.api.commons.components.ref.tipotarifa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_TIPO_TARIFA", schema = "GNSAT")
public class TipoTarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_tarifa")
    @SequenceGenerator(name = "seq_tipo_tarifa", sequenceName = "GNSAT.SEQ_REF_tipo_tarifa", allocationSize = 1)
    @Column(name = "TIT_CODIGO")
    private Long titCodigo;

    @Column(name = "TIT_NOMBE")
    private String titNombre;

    @Column(name = "TIT_DESCRIPCION")
    private String titDescripcion;

    @Column(name = "TIT_MONEDA")
    private Long titMoneda;

    @Column(name = "TIT_ESTA_ACTIVA")
    private String titEstaActiva;

    @Column(name = "TIT_FECHA_DESDE")
    private Date titFechaDesde;

    @Column(name = "TIT_FECHA_HASTA")
    private Date titFechaHasta;

    @Column(name = "TIT_FECHA_ULT_ACTUALIZACION")
    private Date titFechaUltActualizacion;

    @Column(name = "CAMPO_1")
    private String campo1;

    @Column(name = "CAMPO_2")
    private String campo2;

    @Column(name = "CAMPO_3")
    private String campo3;

    @Column(name = "CAMPO_4")
    private Long campo4;

    @Column(name = "CAMPO_5")
    private Long campo5;

    @Column(name = "CAMPO_6")
    private Long campo6;

    @Column(name = "CAMPO_7")
    private Date campo7;

    @Column(name = "CAMPO_8")
    private Date campo8;

    @Column(name = "CAMPO_9")
    private Date campo9;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private String status;
        private String mensaje;
        private List<TipoTarifa> tipotarifa; // Debe ser List<Marca>
    }

}
