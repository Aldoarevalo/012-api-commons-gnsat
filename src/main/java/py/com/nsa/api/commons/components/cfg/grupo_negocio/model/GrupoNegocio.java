package py.com.nsa.api.commons.components.cfg.grupo_negocio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_GRUPO_NEGOCIO", schema = "GNSAT")
public class GrupoNegocio {

    @Id
    @Column(name = "GRN_CODIGO")
    private Long grnCodigo;

    @Column(name = "GRN_NOMBRE")
    private String grnNombre;

    @Column(name = "CAMPO_1")
    private String campo1;

    @Column(name = "CAMPO_2")
    private String campo2;

    @Column(name = "CAMPO_3")
    @Temporal(TemporalType.DATE)
    private Date campo3;

    @Column(name = "CAMPO_4")
    private Long campo4;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<GrupoNegocio> gruposNegocio;
    }
}
