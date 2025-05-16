package py.com.nsa.api.commons.components.ref.tipodocumento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_TIPO_DOCUMENTO", schema = "GNSAT")
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_documento")
    @SequenceGenerator(name = "seq_tipo_documento", sequenceName = "GNSAT.SEQ_REF_TIPO_DOCUMENTO", allocationSize = 1)
    @Column(name = "TDO_CODIGO")
    private Long tdoCodigo;

    @Column(name = "TDO_DESCRIPCION")
    private String tdoDescripcion;

    @Column(name = "PA_COD")
    private Long paiCodigo;

    @Column(name = "TDO_ABREVIATURA")
    private String tdoAbreviatura;

    @Builder.Default
    @Column(name = "TDO_GENERA_PUNTOSCLUB")
    private String tdoGeneraPuntosClub = "N";

    @Column(name = "TDO_DOCUMENTO_WU")
    private String tdoDocumentoWu;

    @Column(name = "TDO_WU")
    private String tdoWu;

    @Column(name = "TDO_ESTADO")
    private String tdoEstado;

    @Column(name = "TDO_IMG_ALTO")
    private Double tdoImgAlto;

    @Column(name = "TDO_IMG_CARAS")
    private Double tdoImgCaras;

    @Column(name = "TDO_IMG_ANCHO")
    private Double tdoImgAncho;

    @Builder.Default
    @Column(name = "TDO_GENERA_RECEPCION")
    private String tdoGeneraRecepcion = "N";

    @Column(name = "TDO_PRECEPCION")
    private String tdoPrecepcion;

    @ManyToOne
    @JoinColumn(name = "PA_COD", referencedColumnName = "PA_COD", insertable = false, updatable = false)
    private Pais pais;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<TipoDocumento> tipoDocumentos;
    }
}
