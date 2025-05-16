package py.com.nsa.api.commons.components.cfg.productoimpuesto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoImpuestoId implements Serializable {
    private String proCod;
    private Long paCod;
}