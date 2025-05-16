package py.com.nsa.api.commons.components.trx.transferenciaproddet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaProductoDetalleId implements Serializable {
    private Long trfpCod;
    private Integer trfpdLinea;
}
