package py.com.nsa.api.commons.components.cfg.cajausuario.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class UpdateOperacionRequest {
    @NotEmpty(message = "La lista de usuarios no puede estar vacía")
    private List<Long> usuariosIds;
    
    @NotNull(message = "La operación no puede ser nula")
    private String operacion;
}