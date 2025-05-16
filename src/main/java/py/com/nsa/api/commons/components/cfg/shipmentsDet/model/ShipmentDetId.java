package py.com.nsa.api.commons.components.cfg.shipmentsDet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDetId implements Serializable {
    private String tmsWhseid;
    private String tmsStorerkey;
    private String tmsExternorderkey;
    private String tmsExternlineno;
}