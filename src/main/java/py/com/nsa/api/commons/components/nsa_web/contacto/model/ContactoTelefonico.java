package py.com.nsa.api.commons.components.nsa_web.contacto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_CONTACTO_TELEFONICO", schema = "GNSAT")
public class ContactoTelefonico {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contacto_telefonico_generator")
    @SequenceGenerator(name = "contacto_telefonico_generator", sequenceName = "GNSAT.SEQ_CFG_CONTACTO_TELEFONICO", allocationSize = 1)
    @Column(name = "CONT_CODIGO")
    private Long contCodigo;

    @Column(name = "CONT_ICONO")
    private String contIcono;

    @ManyToOne
    @JoinColumn(name = "PA_COD", referencedColumnName = "PA_COD")
    private Pais pais;

    @Column(name = "CONT_TELEFONO")
    private String contTelefono;

    @Column(name = "CONT_ORDEN")
    private Long contOrden;

    @CreationTimestamp
    @Column(name = "CONT_CREADO", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date contCreado;

    @UpdateTimestamp
    @Column(name = "CONT_MODIFICADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contModificado;

    @Column(name = "CONT_ELIMINADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date contEliminado;
}