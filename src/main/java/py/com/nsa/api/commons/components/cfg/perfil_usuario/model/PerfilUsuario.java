package py.com.nsa.api.commons.components.cfg.perfil_usuario.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.equipo.model.Equipo;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cfg_perfil_acceso_usu", schema = "GNSAT")
public class PerfilUsuario {

    //perfil de usuario
    @Id
    @Column(name = "PAU_CODIGO")
    private Long pauCodigo;

    //usuario
    @Column(name = "USU_COD")
    private Long usuCodigo;

    @Column(name = "USE_CODIGO")
    private Long useCodigo;

    @Column(name = "PAU_FECHA_MOD")
    private Date pauFechaMod;

    @Column(name = "PA_COD")
    private Long paiCodigo;

    @Column(name = "COL_CODIGO")
    private Long codCodigo;

    @Column(name = "TDC_CODIGO")
    private String tdcCodigo;

    @Column(name = "DOC_NUMERO")
    private String docNumero;

    @Column(name = "AGE_CODIGO")
    private Long ageCodigo;

    @Column(name = "PAU_TIPO_PERFIL")
    private Long pauTipoPerfil;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<PerfilUsuario> perfilesaccesos;
    }

}
