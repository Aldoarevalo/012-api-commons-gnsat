package py.com.nsa.api.commons.components.cfg.grupo_negocio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.grupo_negocio.model.GrupoNegocio;
import py.com.nsa.api.commons.components.cfg.grupo_negocio.repository.GrupoNegocioRepository;

import java.util.List;

@Service
public class GrupoNegocioService {

    @Autowired
    private GrupoNegocioRepository grupoNegocioRepository;

    public GrupoNegocio.MensajeRespuesta getGruposNegocioAll(String keyword) {
        try {
            List<GrupoNegocio> gruposNegocio;
            if (keyword == null || keyword.isEmpty()) {
                gruposNegocio = grupoNegocioRepository.findAll();
            } else {
                gruposNegocio = grupoNegocioRepository.findByGrnNombreStartingWith(keyword);
            }
            if (gruposNegocio.isEmpty()) {
                return new GrupoNegocio.MensajeRespuesta(200L, "No se encontraron grupos de negocio.", null);
            }
            return new GrupoNegocio.MensajeRespuesta(200L, "Grupos de negocio obtenidos exitosamente.", gruposNegocio);
        } catch (Exception e) {
            System.err.println("Error al obtener los grupos de negocio: " + e.getMessage());
            e.printStackTrace();
            return new GrupoNegocio.MensajeRespuesta(500L, "Error al obtener los grupos de negocio: " + e.getMessage(), null);
        }
    }

    public GrupoNegocio.MensajeRespuesta insertarGrupoNegocio(GrupoNegocio grupoNegocio) {
        try {
            if (grupoNegocioRepository.existsByGrnNombreIgnoreCase(grupoNegocio.getGrnNombre())) {
                return new GrupoNegocio.MensajeRespuesta(204L, "Ya existe un grupo de negocio con el mismo nombre.", null);
            }
            // Crear el objeto Empleado y asignar los datos necesarios
            Long nextgrnCodigo = grupoNegocioRepository.getNextgrnCodigo();
            grupoNegocio.setGrnCodigo(nextgrnCodigo);
            GrupoNegocio nuevoGrupoNegocio = grupoNegocioRepository.save(grupoNegocio);
            return new GrupoNegocio.MensajeRespuesta(200L, "Grupo de negocio creado exitosamente.", List.of(nuevoGrupoNegocio));
        } catch (Exception e) {
            System.err.println("Error al insertar el grupo de negocio: " + e.getMessage());
            e.printStackTrace();
            return new GrupoNegocio.MensajeRespuesta(500L, "Error al insertar el grupo de negocio: " + e.getMessage(), null);
        }
    }

    public GrupoNegocio.MensajeRespuesta updateGrupoNegocio(GrupoNegocio grupoNegocio) {
        try {
            if (grupoNegocioRepository.existsByGrnNombreIgnoreCase(grupoNegocio.getGrnNombre())) {
                return new GrupoNegocio.MensajeRespuesta(204L, "Ya existe un grupo de negocio con el mismo nombre.", null);
            }
            if (grupoNegocio.getGrnCodigo() == null || !grupoNegocioRepository.existsById(grupoNegocio.getGrnCodigo())) {
                return new GrupoNegocio.MensajeRespuesta(204L, "Grupo de Negocio no encontrado.", null);
            }
            GrupoNegocio updatedGrupoNegocio = grupoNegocioRepository.save(grupoNegocio);
            return new GrupoNegocio.MensajeRespuesta(200L, "Grupo de negocio actualizado exitosamente.", List.of(updatedGrupoNegocio));
        } catch (Exception e) {
            System.err.println("Error al actualizar el grupo de negocio: " + e.getMessage());
            e.printStackTrace();
            return new GrupoNegocio.MensajeRespuesta(500L, "Error al actualizar el grupo de negocio: " + e.getMessage(), null);
        }
    }

    public GrupoNegocio.MensajeRespuesta deleteGrupoNegocio(Long grnCodigo) {
        try {
            if (grupoNegocioRepository.existsById(grnCodigo)) {
                grupoNegocioRepository.deleteById(grnCodigo);
                return new GrupoNegocio.MensajeRespuesta(200L, "Grupo de negocio eliminado exitosamente.", null);
            } else {
                return new GrupoNegocio.MensajeRespuesta(204L, "Grupo de negocio no encontrado.", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar el Grupo de Negocio porque está referenciado por otros registros.";
            return new GrupoNegocio.MensajeRespuesta(204L, "Error al eliminar el Grupo de Negocio: " + mensaje, null);
        } catch (Exception e) {
            System.err.println("Error al eliminar el grupo de negocio: " + e.getMessage());
            e.printStackTrace();
            return new GrupoNegocio.MensajeRespuesta(500L, "Error al eliminar el grupo de negocio: " + e.getMessage(), null);
        }
    }
}
