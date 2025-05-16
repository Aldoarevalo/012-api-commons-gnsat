package py.com.nsa.api.commons.components.nsa_web.banner_pasajero.model;

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
@Table(name = "CFG_BANNER_PASAJERO", schema = "GNSAT")
public class Pasajero {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pasajero_generator")
    @SequenceGenerator(name = "pasajero_generator", sequenceName = "GNSAT.SEQ_CFG_BANNER_PASAJERO", allocationSize = 1)
    @Column(name = "BAP_CODIGO")
    private Long bapCodigo;

    @Column(name = "BAP_IMAGEN")
    private String bapImagen;

    @Lob
    @Column(name = "BAP_DESCRIPCION")
    private String bapDescripcion;

    @Column(name = "BAP_ORDEN")
    private Long bapOrden;

    @CreationTimestamp
    @Column(name = "BAP_CREACION", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date bapCreacion;

    @UpdateTimestamp
    @Column(name = "BAP_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bapModificacion;

    @Column(name = "BAP_ELIMINACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bapEliminacion;
}