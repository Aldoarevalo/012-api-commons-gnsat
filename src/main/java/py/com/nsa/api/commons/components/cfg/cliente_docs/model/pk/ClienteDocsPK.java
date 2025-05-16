package py.com.nsa.api.commons.components.cfg.cliente_docs.model.pk;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDocsPK implements Serializable {

    @Column(name = "CD_CODCLIENTE")
    private Long cdCodCliente;

    @Column(name = "CD_TIPO_DOC")
    private String cdTipoDoc;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ClienteDocsPK that = (ClienteDocsPK) o;
        return Objects.equals(cdCodCliente, that.cdCodCliente) &&
                Objects.equals(cdTipoDoc, that.cdTipoDoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cdCodCliente, cdTipoDoc);
    }
}