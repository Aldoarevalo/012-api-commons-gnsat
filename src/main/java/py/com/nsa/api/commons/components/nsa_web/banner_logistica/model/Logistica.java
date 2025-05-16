package py.com.nsa.api.commons.components.nsa_web.banner_logistica.model;

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
@Table(name = "CFG_BANNER_LOGISTICA", schema = "GNSAT")
public class Logistica {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logistica_generator")
    @SequenceGenerator(name = "logistica_generator", sequenceName = "GNSAT.SEQ_CFG_BANNER_LOGISTICA", allocationSize = 1)
    @Column(name = "BAL_CODIGO")
    private Long balCodigo;

    @Column(name = "BAL_IMAGEN_PORTADA")
    private String imagenPortada;

    @Column(name = "BAI_DESCRIPCION")
    private String baiDescripcion;

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