package py.com.nsa.api.commons.components.cfg.menu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.tipo_obj.model.TipObj;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_PORTAL_WEB_MENU", schema = "GNSAT")
public class Menu {

    //TODO. mapear campos

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_generator")
    @SequenceGenerator(name = "menu_generator", sequenceName = "GNSAT.SEQ_CFG_PORTAL_WEB_MENU", allocationSize = 1)
    @Column(name = "PWM_CODIGO")
    private Long pwmCod;

    @Column(name = "PWD_ICONO")
    private String pwmIcono;

    @Column(name = "PWD_TITULO")
    private String pwdTitulo;

    @Column(name = "PWD_DESCRIPCION")
    private String pwmDescripcion;

    @Column(name = "PWD_SISTEMA")
    private String pwmSistema;

    @Column(name = "PWD_CSS_CLASE")
    private String pwdCssClase;

    @Column(name = "PWD_COD_MENU_PADRE")
    private Long pwdCodMenuPadre;

    @Column(name = "PWD_MENU_LINK")
    private String pwdMenuLink;

    @Column(name = "PWD_ORDEN_MENU")
    private Long pwdOrdenMenu;

    @Column(name = "PDW_GRUPO")
    private String pwdGrupo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Menu> permisos;
    }

}
