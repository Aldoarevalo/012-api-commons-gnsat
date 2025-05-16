package py.com.nsa.api.commons.components.cfg.perfil_servicio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.app_servicio.model.AppServicio;
import py.com.nsa.api.commons.components.cfg.perfil_servicio.model.pk.PerfilServicioPK;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_PERFIL_SERVICIO", schema = "GNSAT")
@IdClass(value = PerfilServicioPK.class)
public class PerfilServicio {

    //codigo perfil de usuario
    @Id
    @Column(name = "PAU_CODIGO")
    private Long cod_perfil;

    //codigo de servicio/permiso
    @Id
    @Column(name = "SEA_CODIGO")
    private Long cod_servicio;

    @Column(name = "PES_FECHA_ALTA")
    private Date fecha_alta;

    @Column(name = "PES_HABILITADO")
    private Long habilitado;

    @Column(name = "PES_MOSTRAR")
    private Long mostrar;

    @Column(name = "PES_MODIFICAR")
    private Long modificar;

    @Column(name = "PES_AGREGAR")
    private Long agregar;

    @Column(name = "PES_BORRAR")
    private Long borrar;

    @Column(name = "PES_FECHA_HASTA")
    private Date fecha_hasta;

    @ManyToOne
    @JoinColumn(name = "SEA_CODIGO", referencedColumnName = "SEA_CODIGO", nullable = false, insertable = false, updatable = false)
    private AppServicio nombre;
}
