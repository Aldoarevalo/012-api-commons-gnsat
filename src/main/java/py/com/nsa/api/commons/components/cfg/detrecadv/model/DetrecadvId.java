package py.com.nsa.api.commons.components.cfg.detrecadv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetrecadvId implements Serializable {
    private String wmsStorerkey;
    private String wmsExternreceiptkey;
    private Long wmsId;
}