package py.com.nsa.api.commons.components.cfg.usuario_app_servicio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.app_servicio.model.pk.AppServicioPK;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//TODO. cual es la tabla? si voy a usar un query compuesto para buscar:
//los servicios asociados a una app, y a un usuario en particulaer
@Table(name = "CFG_SERVICIO_APP", schema = "GNSAT")
@IdClass(value = AppServicioPK.class)
public class Admin {

    @Id
    @Column(name = "SEA_CODIGO")
    private Long seaCodigo;

    @Id
    @Column(name = "APP_CODIGO")
    private Long appCodigo;

    @Column(name = "SEA_NOMBRE")
    private String seaNombre;

    @Column(name = "SEA_DESCRIPCION")
    private String seaDescripcion;

    @Column(name = "SEA_HABILITADO")
    private String seaHabilitado;

    @Column(name = "SEA_NIVEL_ACCION")
    private Long seaNivelAccion;

    @Column(name = "SEA_SERVICIO_SUPERIOR_CODIGO")
    private String seaServicioSuperiorCodigo;

    @Column(name = "SEA_URL_SERVICIO")
    private String seaUrlServicio;

    @Column(name = "SEA_ICONO_SERVICIO")
    private String seaIconoServicio;

    @Column(name = "SEA_IMAGEN_SERVICIO")
    private Long seaImagenServicio;

}
