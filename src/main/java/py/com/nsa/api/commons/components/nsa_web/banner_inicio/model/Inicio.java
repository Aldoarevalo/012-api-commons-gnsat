package py.com.nsa.api.commons.components.nsa_web.banner_inicio.model;
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
@Table(name = "CFG_BANNER_INICIO", schema = "GNSAT")
public class Inicio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inicio_generator")
    @SequenceGenerator(name = "inicio_generator", sequenceName = "GNSAT.SEQ_CFG_BANNER_INICIO", allocationSize = 1)
    @Column(name = "BAI_CODIGO")
    private Long inicioCodigo;

    @Column(name = "BAI_IMAGEN")
    private String imagenInicio;

    @Column(name = "BAI_URL")
    private String imagenUrl;

    @Column(name = "BAI_DESCRIPCION")
    private String inicioDescripcion;

    @Column(name = "BAI_ORDEN")
    private Long inicioOrden;

    @CreationTimestamp
    @Column(name = "BAI_CREACION", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date baiCreacion;

    @UpdateTimestamp
    @Column(name = "BAI_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date baiModificacion;

    @Column(name = "BAI_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date baiEliminacion;
}