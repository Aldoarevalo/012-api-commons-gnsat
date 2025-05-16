package py.com.nsa.api.commons.components.cfg.cliente_docs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import py.com.nsa.api.commons.components.cfg.cliente.model.Cliente;
import py.com.nsa.api.commons.components.cfg.agencia.model.Agencia;
import py.com.nsa.api.commons.components.cfg.cliente_docs.model.pk.ClienteDocsPK;
import py.com.nsa.api.commons.components.ref.parvalor.model.ParValor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "CFG_CLIENTE_DOCS", schema = "GNSAT")
//@IdClass(value = ClienteDocsPK.class)
public class ClienteDocs {

    @Id
    @Column(name = "CD_COD", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CFG_CLIENTE_DOCS")
    @SequenceGenerator(name = "SEQ_CFG_CLIENTE_DOCS", sequenceName = "SEQ_CFG_CLIENTE_DOCS", allocationSize = 1, schema = "GNSAT")
    private Long cdCod;

    @Column(name = "AG_COD", nullable = false)
    private Long agCod;


    @Column(name = "CD_CODCLIENTE", nullable = false)
    private Long cdCodCliente;


    @Column(name = "CD_TIPO_DOC", length = 4, nullable = false)
    private String cdTipoDoc;

    @Column(name = "CD_FECHA_VTO")
    @Temporal(TemporalType.DATE)
    private Date cdFechaVto;

    @Column(name = "CD_DESCRIPCION", length = 20)
    private String cdDescripcion;

    @Lob
    @Column(name = "CD_ARCHIVOS")
    private byte[] cdArchivos;

    @ManyToOne
    @JoinColumn(name = "CD_TIPO_DOC", referencedColumnName = "PAR_VALOR", insertable = false, updatable = false)
    private ParValor tipodoccliente;

    @ManyToOne
    @JoinColumn(name = "CD_CODCLIENTE", referencedColumnName = "C_COD", insertable = false, updatable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "AG_COD", referencedColumnName = "AG_COD", insertable = false, updatable = false)
    private Agencia agencia;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MensajeRespuesta {
        private Long status;
        private String mensaje;
        private List<ClienteDocs> clientedocs;
    }
}