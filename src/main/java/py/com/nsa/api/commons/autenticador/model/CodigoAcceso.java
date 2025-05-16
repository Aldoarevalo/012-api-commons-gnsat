package py.com.nsa.api.commons.autenticador.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodigoAcceso {
    private String usuario;
    private String codigo;
    private LocalDateTime fechaExpiracion;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SolicitudCodigo {
        private String usuario;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerificacionCodigo {
        private String usuario;
        private String codigo;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RespuestaAutenticacion {
        private Long status;
        private String mensaje;
        private String token;
    }
}