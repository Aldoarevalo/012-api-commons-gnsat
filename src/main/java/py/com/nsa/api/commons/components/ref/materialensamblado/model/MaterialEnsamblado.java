package py.com.nsa.api.commons.components.ref.materialensamblado.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.ref.producto.model.Producto;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_MATERIAL_ENSAMBLADO", schema = "GNSAT")
@IdClass(MaterialEnsambladoId.class)
public class MaterialEnsamblado {

    @Id
    @Column(name = "ME_LINEA", nullable = false)
    private Integer meLinea;

    @Id
    @Column(name = "PRO_COD", length = 7, nullable = false)
    private String proCod;

    @NotNull(message = "La cantidad no puede ser nula")
    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    @Column(name = "ME_CANTIDAD", nullable = false)
    private Integer meCantidad;

    @NotNull(message = "La unidad de medida no puede ser nula")
    @Column(name = "ME_UM", length = 4, nullable = false)
    private String meUm;

    @Column(name = "ME_COD", length = 7)
    private String meCod;

    @ManyToOne
    @JoinColumn(name = "PRO_COD", referencedColumnName = "PRO_COD", insertable = false, updatable = false)
    @JsonProperty("materialdeensamble") // Cambia "producto" a "materialdeensamble" en JSON
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "ME_UM", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor unidadMedida;

    @ManyToOne
    @JoinColumn(name = "ME_COD", referencedColumnName = "PRO_COD", insertable = false, updatable = false)
    @JsonProperty("materialdeensambleinsumo") // Cambia "insumo" a "materialdeensambleinsumo" en JSON
    private Producto insumo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<MaterialEnsamblado> materiales;
    }
}