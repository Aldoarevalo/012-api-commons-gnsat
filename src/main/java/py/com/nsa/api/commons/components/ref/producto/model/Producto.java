package py.com.nsa.api.commons.components.ref.producto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.ref.empresa.model.Empresa;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_PRODUCTO", schema = "GNSAT")
public class Producto {

    @Id
    @Column(name = "PRO_COD", length = 7, nullable = false)
    private String proCod;

    @Column(name = "PRO_DESCRIPCION", length = 50, nullable = false)
    private String proDescripcion;

    @ManyToOne
    @JoinColumn(name = "PRO_EMPRESA", referencedColumnName = "EM_COD", nullable = false)
    private Empresa empresa;

    @Column(name = "PRO_CODSAP", length = 10)
    private String proCodSap;

    @Column(name = "PAR_TIPO", length = 4, nullable = false)
    private String parTipo;

    @Column(name = "PAR_UM", length = 4, nullable = false)
    private String parUm;

    @Column(name = "PRO_CANTMIN", nullable = false)
    private Integer proCantMin;

    @Column(name = "PAR_SERVICIO", length = 4, nullable = false)
    private String parServicio;

    @Column(name = "PRO_CAT1", length = 4)
    private String proCat1;

    @Column(name = "PRO_CAT2", length = 4)
    private String proCat2;

    @Column(name = "PRO_CAT3", length = 4)
    private String proCat3;

    @Column(name = "PRO_CAT4", length = 4)
    private String proCat4;

    @Column(name = "PRO_CAT5", length = 4)
    private String proCat5;

    @Column(name = "PRO_CAT6", length = 4)
    private String proCat6;

    @Column(name = "PRO_CAT7", length = 4)
    private String proCat7;

    @Column(name = "PRO_CAT8", length = 4)
    private String proCat8;

    @Column(name = "PRO_CAT9", length = 4)
    private String proCat9;

    @Column(name = "PRO_CAT10", length = 4)
    private String proCat10;

    @Column(name = "PRO_CUENTAPAR", length = 15)
    private String proCuentaPar;

    @Column(name = "PRO_CUENTAARG", length = 15)
    private String proCuentaArg;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @Column(name = "PRO_ULTMODIF", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date proUltModif;

    @Column(name = "PRO_USUMODIF", nullable = false)
    private Integer proUsuModif;

    @Column(name = "PRO_ESTADO", length = 1, nullable = false)
    private String proEstado;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd")
    @Column(name = "PRO_CREACION", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date proCreacion;

    // Relaciones con otras entidades
    @ManyToOne
    @JoinColumn(name = "PRO_USUMODIF", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "PAR_TIPO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorTipo;

    @ManyToOne
    @JoinColumn(name = "PAR_UM", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorUm;

    @ManyToOne
    @JoinColumn(name = "PAR_SERVICIO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorServicio;

    @ManyToOne
    @JoinColumn(name = "PRO_CAT1", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorCat1;

    @ManyToOne
    @JoinColumn(name = "PRO_CAT2", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorCat2;

    @ManyToOne
    @JoinColumn(name = "PRO_CAT3", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorCat3;

    @ManyToOne
    @JoinColumn(name = "PRO_CAT4", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorCat4;

    @ManyToOne
    @JoinColumn(name = "PRO_CAT5", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorCat5;

    @ManyToOne
    @JoinColumn(name = "PRO_CAT6", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorCat6;

    @ManyToOne
    @JoinColumn(name = "PRO_CAT7", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorCat7;

    @ManyToOne
    @JoinColumn(name = "PRO_CAT8", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorCat8;

    @ManyToOne
    @JoinColumn(name = "PRO_CAT9", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorCat9;

    @ManyToOne
    @JoinColumn(name = "PRO_CAT10", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor parValorCat10;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<Producto> productos;
    }
}