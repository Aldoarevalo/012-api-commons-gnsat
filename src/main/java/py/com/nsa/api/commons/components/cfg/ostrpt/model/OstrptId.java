package py.com.nsa.api.commons.components.cfg.ostrpt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OstrptId implements Serializable {
    private String wmsStorerkey;
    private String wmsExternorderkey;
}