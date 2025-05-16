package py.com.nsa.api.commons.components.ref.rutacab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;
import py.com.nsa.api.commons.components.ref.empresa.model.Empresa;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;
import py.com.nsa.api.commons.components.ref.rutadet.model.RutaDet;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "REF_RUTA_CAB", schema = "GNSAT")
public class RutaCab {

    @Id
    @Column(name = "RUC_COD")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ruta_cab_generator")
    @SequenceGenerator(name = "ruta_cab_generator", sequenceName = "GNSAT.SEQ_REF_RUTA_CAB", allocationSize = 1)
    private Long rucCod;

    @Column(name = "RUC_ESTADO", length = 1, nullable = false)
    private String rucEstado;

    @Column(name = "RUC_DESCRIPCION", length = 50, nullable = false)
    private String rucDescripcion;

    @Column(name = "RUC_SERVICIO", length = 4, nullable = false)
    private String rucServicio;

    @Column(name = "RUC_EMPRESA", nullable = false)
    private Long rucEmpresa;

    @Column(name = "RUC_ULTMOD", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date rucUltmod;

    @Column(name = "USU_COD", nullable = false)
    private Long usuCod;


    @ManyToOne
    @JoinColumn(name = "RUC_SERVICIO", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor servicio;


    @ManyToOne
    @JoinColumn(name = "RUC_EMPRESA", referencedColumnName = "EM_COD", insertable = false, updatable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "USU_COD", referencedColumnName = "USU_COD", insertable = false, updatable = false)
    private Usuario usuario;

    @Transient // Campo transitorio, no mapeado por JPA, solo para recibir detalles del JSON
    private List<RutaDet> detalles;

    /*@OneToMany(mappedBy = "rutaCab", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Evita que Jackson serialice esta colección
    private List<RutaDet> detalles;*/

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<RutaCab> rutas;
    }
}