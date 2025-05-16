package py.com.nsa.api.commons.components.cfg.recadv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecadvId implements Serializable {
    private String wmsStorerkey;
    private String wmsExternreceiptkey;
}