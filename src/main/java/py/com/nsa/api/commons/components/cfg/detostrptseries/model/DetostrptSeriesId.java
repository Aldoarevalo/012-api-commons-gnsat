package py.com.nsa.api.commons.components.cfg.detostrptseries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetostrptSeriesId implements Serializable {
    private String wmsStorerkey;
    private String wmsExternorderkey;
    private Long wmsIddet;
    private Long wmsIdserie;
}