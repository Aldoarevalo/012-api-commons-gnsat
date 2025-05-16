package py.com.nsa.api.commons.components.ref.pdoc.model.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
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
public class PDocPK implements Serializable {

    @Column(name = "DOC_COD")
    private Long docCod;
    @Id
    @Column(name = "P_DOC_NRO_DOC")
    private String PDocNroDoc;
    @Id
    @Column(name = "P_COD")
    private Long pcod;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PDocPK that = (PDocPK) o;
        return Objects.equals(docCod, that.docCod) &&
                Objects.equals(PDocNroDoc, that.PDocNroDoc) &&
                Objects.equals(pcod, that.pcod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docCod, PDocNroDoc, pcod);
    }
}
