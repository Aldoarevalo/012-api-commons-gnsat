package py.com.nsa.api.commons.components.nsa_web.banner_turismo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_BANNER_TURISMO", schema = "GNSAT")
public class Turismo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turismo_generator")
    @SequenceGenerator(name = "turismo_generator", sequenceName = "GNSAT.SEQ_CFG_BANNER_TURISMO", allocationSize = 1)
    @Column(name = "BAT_CODIGO")
    private Long batCodigo;

    @Column(name = "BAT_IMAGEN", length = 191)
    private String batImagen;

    @Lob
    @Column(name = "BAT_DESCRIPCION")
    private String batDescripcion;

    @Column(name = "BAT_ORDEN")
    private Long batOrden;

    @CreationTimestamp
    @Column(name = "BAT_CREACION", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date batCreacion;

    @UpdateTimestamp
    @Column(name = "BAT_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date batModificacion;

    @Column(name = "BAT_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date batEliminacion;
}