package py.com.nsa.api.commons.components.cfg.cajaagencia.model.request;

import lombok.Data;
import java.util.List;

@Data
public class UpdateOperacionRequest {
    private List<Long> cajaIds;
    private String operacion;
}