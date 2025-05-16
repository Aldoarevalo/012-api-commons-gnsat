package py.com.nsa.api.commons.components.ref.materialensamblado.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialEnsambladoId implements Serializable {
    private Integer meLinea;
    private String proCod;
}