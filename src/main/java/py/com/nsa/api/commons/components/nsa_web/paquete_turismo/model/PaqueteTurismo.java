package py.com.nsa.api.commons.components.nsa_web.paquete_turismo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TRX_PAQUETE_TURISMO", schema = "GNSAT")
public class PaqueteTurismo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paquete_turismo_generator")
    @SequenceGenerator(name = "paquete_turismo_generator", sequenceName = "GNSAT.SEQ_TRX_PAQUETE_TURISMO", allocationSize = 1)
    @Column(name = "PAT_CODIGO")
    private Long patCodigo;

    @Column(name = "PAT_TITULO")
    private String patTitulo;

    @ManyToOne
    @JoinColumn(name = "PA_COD", referencedColumnName = "PA_COD")
    private Pais pais;

    @Column(name = "PAT_TIPO")
    private String patTipo;

    @Column(name = "PAT_PRECIO", precision = 22, scale = 18)
    private BigDecimal patPrecio;

    @Column(name = "PAT_ORDEN")
    private Long patOrden;

    @Column(name = "PAT_DESCIPCION")
    @Lob
    private String patDescripcion;

    @Column(name = "PAT_IMAGEN_PORTADA")
    private String patImagenPortada;

    @Column(name = "PAT_IMAGEN_INICIO")
    private String patImagenInicio;

    @Column(name = "PAT_FORMULARIO")
    private String patFormulario;

    @Column(name = "PAT_SLUG")
    private String patSlug;

    @Column(name = "PAT_URL")
    private String patUrl;

    @Column(name = "PAT_CARACTERISTICAS")
    @Lob
    private String patCaracteristicas;

    @CreationTimestamp
    @Column(name = "PAT_CREACION", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date patCreacion;

    @UpdateTimestamp
    @Column(name = "PAT_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date patModificacion;

    @Column(name = "PAT_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date patEliminacion;
}