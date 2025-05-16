package py.com.nsa.api.commons.components.ref.grupocargo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.grupocargo.model.GrupoCargo;
import py.com.nsa.api.commons.components.ref.grupocargo.repository.GrupoCargoRepository;

import java.util.List;

@Service
public class GrupoCargoService {

    @Autowired
    private GrupoCargoRepository grupoCargoRepository;

    public GrupoCargo.MensajeRespuesta getGruposCargosAll(String keyword) {
        try {
            List<GrupoCargo> gruposCargos;
            if (keyword == null || keyword.isEmpty()) {
                gruposCargos = grupoCargoRepository.findAll();
            } else {
                gruposCargos = grupoCargoRepository.findByGcaDescripcionStartingWith(keyword);
            }
            if (gruposCargos.isEmpty()) {
                return new GrupoCargo.MensajeRespuesta(204L, "No se encontraron grupos de cargos.", null);
            }
            return new GrupoCargo.MensajeRespuesta(200L, "Grupos de cargos obtenidos exitosamente.", gruposCargos);
        } catch (Exception e) {
            return new GrupoCargo.MensajeRespuesta(500L, "Error al obtener los grupos de cargos: " + e.getMessage(), null);
        }
    }

    public GrupoCargo.MensajeRespuesta insertarGrupoCargo(GrupoCargo grupoCargo) {
        try {
            if (grupoCargoRepository.existsByGcaDescripcionIgnoreCase(grupoCargo.getGcaDescripcion())) {
                return new GrupoCargo.MensajeRespuesta(204L, "Ya existe un grupo de cargo con la misma descripción.", null);
            }
            GrupoCargo nuevoGrupoCargo = grupoCargoRepository.save(grupoCargo);
            return new GrupoCargo.MensajeRespuesta(200L, "Grupo de cargo creado exitosamente.", List.of(nuevoGrupoCargo));
        } catch (Exception e) {
            return new GrupoCargo.MensajeRespuesta(500L, "Error al insertar el grupo de cargo: " + e.getMessage(), null);
        }
    }

    public GrupoCargo.MensajeRespuesta updateGrupoCargo(GrupoCargo grupoCargo) {
        try {
            if (grupoCargoRepository.existsByGcaDescripcionIgnoreCase(grupoCargo.getGcaDescripcion())) {
                return new GrupoCargo.MensajeRespuesta(204L, "Ya existe un grupo de cargo con la misma descripción.", null);
            }
            if (grupoCargo.getGcaCodigo() == null || !grupoCargoRepository.existsById(grupoCargo.getGcaCodigo())) {
                return new GrupoCargo.MensajeRespuesta(204L, "Grupo Cargo no encontrado.", null);
            }
            GrupoCargo updatedGrupoCargo = grupoCargoRepository.save(grupoCargo);
            return new GrupoCargo.MensajeRespuesta(200L, "Grupo de cargo actualizado exitosamente.", List.of(updatedGrupoCargo));
        } catch (Exception e) {
            return new GrupoCargo.MensajeRespuesta(500L, "Error al actualizar el grupo de cargo: " + e.getMessage(), null);
        }
    }

    public GrupoCargo.MensajeRespuesta deleteGrupoCargo(Integer gcaCodigo) {
        try {
            if (grupoCargoRepository.existsById(gcaCodigo)) {
                grupoCargoRepository.deleteById(gcaCodigo);
                return new GrupoCargo.MensajeRespuesta(200L, "Grupo de cargo con código " + gcaCodigo + " eliminado exitosamente", null);
            } else {
                return new GrupoCargo.MensajeRespuesta(204L, "Grupo de cargo con código " + gcaCodigo + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el Grupo de Cargo porque está referenciado por otros registros.";
            return new GrupoCargo.MensajeRespuesta(204L, "Error al eliminar el Grupo de Cargo: " + mensaje, null);
        } catch (Exception e) {
            return new GrupoCargo.MensajeRespuesta(500L, "Error al eliminar el grupo de cargo con código " + gcaCodigo + ": " + e.getMessage(), null);
        }
    }
}
