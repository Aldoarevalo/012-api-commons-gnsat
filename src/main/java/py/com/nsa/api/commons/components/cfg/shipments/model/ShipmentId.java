package py.com.nsa.api.commons.components.cfg.shipments.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentId implements Serializable {
    private String tmsWhseid;
    private String tmsStorerkey;
    private String tmsExternorderkey;
}