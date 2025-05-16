package py.com.nsa.api.commons.components.cfg.tipo_obj.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="CFG_TIPO_OBJ",schema="GNSAT")
public class TipObj {
@Id
@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="tipo_obj_generator")
@SequenceGenerator(name="tipo_obj_generator", sequenceName="GNSAT.SEQ_CFG_TIPO_OBJ", allocationSize=1)
@Column(name="T_OBJ_CODIGO")
    private Long tipObjCodigo;

@Column(name="T_OBJ_NOMBRE")
    private String tipObjNombre;


}