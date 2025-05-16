package py.com.nsa.api.commons.components.cfg.productoimpuesto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.impuesto.model.Impuesto;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;
import py.com.nsa.api.commons.components.ref.producto.model.Producto;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_PRODUCTO_IMPUESTO", schema = "GNSAT")
@IdClass(ProductoImpuestoId.class)
public class ProductoImpuesto {

    @Id
    @Column(name = "PRO_COD", length = 7, nullable = false)
    private String proCod;

    @Id
    @Column(name = "PA_COD", nullable = false)
    private Long paCod;

    @Column(name = "IMP_COD", nullable = false)
    private Long impCod;

    // Relaciones con otras entidades
    @ManyToOne
    @JoinColumn(name = "PRO_COD", referencedColumnName = "PRO_COD", insertable = false, updatable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "PA_COD", referencedColumnName = "PA_COD", insertable = false, updatable = false)
    private Pais pais;

    @ManyToOne
    @JoinColumn(name = "IMP_COD", referencedColumnName = "IMP_COD", insertable = false, updatable = false)
    private Impuesto impuesto;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<ProductoImpuesto> productoImpuestos;
    }
}