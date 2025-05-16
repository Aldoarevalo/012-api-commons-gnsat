package py.com.nsa.api.commons.components.cfg.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_APP", schema = "GNSAT")
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agencia_generator")
    @SequenceGenerator(name = "agencia_generator", sequenceName = "GNSAT.SEQ_CFG_AGENCIA", allocationSize = 1)
    @Column(name = "APP_CODIGO")
    private Long appCodigo;

    @Column(name = "APP_NOMBRE")
    private String appNombre;

    @Column(name = "APP_ACTIVO")
    private String appActivo;

    @Column(name = "APP_DESCRIPCION")
    private String appDescripcion;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<App> apps;
    }

}
