package py.com.nsa.api.commons.components.ref.profesion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.profesion.model.Profesion;
import py.com.nsa.api.commons.components.ref.profesion.repository.ProfesionRepository;

import java.util.List;

@Service
public class ProfesionService {

    @Autowired
    private ProfesionRepository profesionRepository;


    public Profesion.MensajeRespuesta getProfesionesAll(String keyword) {
        try {
            List<Profesion> profesiones;
            if (keyword == null || keyword.isEmpty()) {
                profesiones = profesionRepository.findAll();
            } else {
                profesiones = profesionRepository.findByProfDescripcionOrAbreviatura(keyword);
            }
            // Si la lista de profesiones está vacía, devolvemos el mensaje adecuado
            if (profesiones.isEmpty()) {
                return new Profesion.MensajeRespuesta(204L, "No se encontraron profesiónes.", null);
            }
            return new Profesion.MensajeRespuesta(200L, "Profesiónes obtenidas exitosamente.", profesiones);
        } catch (Exception e) {
            System.err.println("Error al obtener las profesiones: " + e.getMessage());
            e.printStackTrace();
            return new Profesion.MensajeRespuesta(500L, "Error al obtener las profesiónes: " + e.getMessage(), null);
        }
    }

    // Insertar una nueva profesión
    public Profesion.MensajeRespuesta insertarProfesion(Profesion profesion) {
        try {
            if (profesionRepository.existsByProfDescripcionIgnoreCaseAndProfAbreviaturaIgnoreCase(
                    profesion.getProfDescripcion(), profesion.getProfAbreviatura())) {
                return new Profesion.MensajeRespuesta(204L,
                        "Ya existe una profesión con el mismo nombre y abreviatura.", null);
            }
            Profesion nuevaProfesion = profesionRepository.save(profesion);
            return new Profesion.MensajeRespuesta(200L,
                    "Profesión creada exitosamente.", List.of(nuevaProfesion));

        } catch (Exception e) {
            System.err.println("Error al insertar la Profesión: " + e.getMessage());
            e.printStackTrace();
            return new Profesion.MensajeRespuesta(500L, "Error al insertar la Profesión: " + e.getMessage(), null);
        }

    }

    public Profesion.MensajeRespuesta updateProfesion(Profesion profesion) {
        try {
            if (profesionRepository.existsByProfDescripcionIgnoreCaseAndProfAbreviaturaIgnoreCase(
                    profesion.getProfDescripcion(), profesion.getProfAbreviatura())) {
                return new Profesion.MensajeRespuesta(204L,
                        "Ya existe una profesión con el mismo nombre y abreviatura.", null);
            }
            if (profesion.getProfCodigo() == null || !profesionRepository.existsById(profesion.getProfCodigo())) {
                return new Profesion.MensajeRespuesta(204L, "Profesión no encontrada.", null);
            }
            Profesion updatedProfesion = profesionRepository.save(profesion);
            return new Profesion.MensajeRespuesta(200L, "Profesión actualizada exitosamente.", List.of(updatedProfesion));
        } catch (Exception e) {
            System.err.println("Error al actualizar la Profesión: " + e.getMessage());
            e.printStackTrace();
            return new Profesion.MensajeRespuesta(500L, "Error al actualizar la Profesión: " + e.getMessage(), null);
        }
    }

    public Profesion.MensajeRespuesta deleteProfesion(Long profCodigo) {
        try {
            if (profesionRepository.existsById(profCodigo)) {
                profesionRepository.deleteById(profCodigo);
                return new Profesion.MensajeRespuesta(200L, "Profesión con código " + profCodigo + " eliminado exitosamente", null);
            } else {
                return new Profesion.MensajeRespuesta(204L, "Profesión con código " + profCodigo + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el registro está referenciado por otros registros
            String mensaje = "No se puede eliminar la Profesión porque está referenciado por otros registros.";
            return new Profesion.MensajeRespuesta(204L, "Error al eliminar la Profesión: " + mensaje, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Profesion.MensajeRespuesta(500L, "Error al eliminar la Profesión con código " + profCodigo + ": " + e.getMessage(), null);
        }
    }
}
