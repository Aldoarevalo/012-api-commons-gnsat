package py.com.nsa.api.commons.components.ref.empresa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.pais.model.Pais;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_EMPRESA", schema = "GNSAT")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_empresa")
    @SequenceGenerator(name = "seq_empresa", sequenceName = "GNSAT.SEQ_REF_EMPRESA", allocationSize = 1)
    @Column(name = "EM_COD")
    private Long emCod;

    @Column(name = "EM_DESCRIPCION")
    private String emDescripcion;

    @Column(name = "EM_RUC")
    private String emRuc;

    @Column(name = "EM_DIRECCION")
    private String emDir;

    @Column(name = "EM_TELEFONO")
    private String emTel;

    @Column(name = "PA_COD")
    private Long paCod;

    @ManyToOne
    @JoinColumn(name = "PA_COD", referencedColumnName = "PA_COD", insertable = false, updatable = false)
    private Pais pais;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Empresa> empresas;
    }
}