package py.com.nsa.api.commons.components.cfg.permiso.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.tipo_obj.model.TipObj;
import py.com.nsa.api.commons.components.cfg.menu.model.Menu;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_PERMISO", schema = "GNSAT")
public class CfgPermiso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permiso_generator")
    @SequenceGenerator(name = "permiso_generator", sequenceName = "GNSAT.SEQ_CFG_PERMISO", allocationSize = 1)
    @Column(name = "PER_COD")
    private Long perCodigo;

    @Column(name = "PER_NOMBRE")
    private String perNombre;

    @Column(name = "PER_TIPO_OBJ")
    private Long perTipoObj;

    @Column(name = "PER_REF_NUM1")
    private Long perRefNum1;

    @Column(name = "PER_REF_NUM2")
    private Long perRefNum2;

    @Column(name = "PER_REF_NUM3")
    private Long perRefNum3;

    @Column(name = "PER_REF_NUM4")
    private Long perRefNum4;

    @Column(name = "PER_REF_NUM5")
    private Long perRefNum5;

    @Column(name = "PER_REF_STR1")
    private String perRefStr1;

    @Column(name = "PER_REF_STR2")
    private String perRefStr2;

    @Column(name = "PER_REF_STR3")
    private String perRefStr3;

    @Column(name = "PER_REF_STR4")
    private String perRefStr4;

    @Column(name = "PER_REF_STR5")
    private String perRefStr5;

    @ManyToOne
    @JoinColumn(name = "PER_TIPO_OBJ", referencedColumnName = "T_OBJ_CODIGO", insertable = false, updatable = false)
    private TipObj tipObj;

    @ManyToOne
    @JoinColumn(name = "PER_REF_NUM3", referencedColumnName = "PWM_CODIGO", insertable = false, updatable = false)
    private Menu menu;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<CfgPermiso> permisos;
    }

}
