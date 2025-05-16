package py.com.nsa.api.commons.components.cfg.cajaagencia.model.request;

import lombok.Data;

@Data
public class CajaAgenciaFiltroRequest {
    private Long paisCod;
    private Long ciudadCod;
    private String tipoAgencia;
    private Long agenciaCod;
    private Long usuarioCod;
}