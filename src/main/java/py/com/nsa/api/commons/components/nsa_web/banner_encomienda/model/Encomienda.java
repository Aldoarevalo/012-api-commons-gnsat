package py.com.nsa.api.commons.components.nsa_web.banner_encomienda.model;

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
@Table(name = "CFG_BANNER_ENCOMIENDA", schema = "GNSAT")
public class Encomienda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "encomienda_generator")
    @SequenceGenerator(name = "encomienda_generator", sequenceName = "GNSAT.SEQ_CFG_BANNER_ENCOMIENDA", allocationSize = 1)
    @Column(name = "BAE_CODIGO")
    private Long baeCodigo;

    @Column(name = "BAE_IMAGEN_PORTADA")
    private String baeImagenPortada;

    @Lob
    @Column(name = "BAE_DESCRIPCION")
    private String baeDescripcion;

    @CreationTimestamp
    @Column(name = "BAE_CREACION", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date baeCreacion;

    @UpdateTimestamp
    @Column(name = "BAE_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date baeModificacion;

    @Column(name = "BAE_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date baeEliminacion;
}