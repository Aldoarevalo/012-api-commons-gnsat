package py.com.nsa.api.commons.components.nsa_web.banner_parque_industrial.model;

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
@Table(name = "CFG_BANNER_PARQUE_INDUSTRIAL", schema = "GNSAT")
public class ParqueIndustrial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parque_industrial_generator")
    @SequenceGenerator(name = "parque_industrial_generator", sequenceName = "GNSAT.SEQ_CFG_BANNER_PARQUE_INDUST", allocationSize = 1)
    @Column(name = "BPI_CODIGO")
    private Long bpiCodigo;

    @Column(name = "BPI_IMAGEN_PORTADA")
    private String bpiImagenPortada;

    @Lob
    @Column(name = "BPI_DESCRIPCION")
    private String bpiDescripcion;

    @CreationTimestamp
    @Column(name = "BPI_CREACION", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date bpiCreacion;

    @UpdateTimestamp
    @Column(name = "BPI_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bpiModificacion;

    @Column(name = "BPI_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bpiEliminacion;
}