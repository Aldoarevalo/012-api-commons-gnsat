package py.com.nsa.api.commons.components.ref.parametro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.ref.parametro.model.Parametro;
import py.com.nsa.api.commons.components.ref.parametro.model.pk.ParametroPK;
import py.com.nsa.api.commons.components.ref.parametro.repository.ParametroRepository;

import java.util.List;

@Service
public class ParametroService {

    @Autowired
    private ParametroRepository parametroRepository;

    public Parametro.MensajeRespuesta getParametrosAll() {
        try {
            List<Parametro> parametros = parametroRepository.findAll();
            if (parametros.isEmpty()) {
                return new Parametro.MensajeRespuesta(204L, "No se encontraron parámetros.", null);
            }
            return new Parametro.MensajeRespuesta(200L, "Parámetros obtenidos exitosamente.", parametros);
        } catch (Exception e) {
            e.printStackTrace();
            return new Parametro.MensajeRespuesta(500L, "Error al obtener parámetros: " + e.getMessage(), null);
        }
    }

    public Parametro.MensajeRespuesta insertarParametro(Parametro parametro) {
        try {
            // Verificar si ya existe un parámetro con el mismo código y nombre

            if (parametroRepository.existsByPmNombreIgnoreCase(parametro.getPmNombre())) {
                return new Parametro.MensajeRespuesta(204L, "Ya existe un Parámetro con la misma descripción.", null);
            }
            // Obtener el siguiente valor de la secuencia
            /*Long nextPmCodigo = parametroRepository.getNextPmCod();
            parametro.setPmCod(nextPmCodigo);*/

            Parametro nuevoParametro = parametroRepository.save(parametro);
            return new Parametro.MensajeRespuesta(200L, "Parámetro creado exitosamente.", List.of(nuevoParametro));
        } catch (Exception e) {
            e.printStackTrace();
            return new Parametro.MensajeRespuesta(500L, "Error al insertar el parámetro: " + e.getMessage(), null);
        }
    }

    /*public Parametro.MensajeRespuesta updateParametro(Parametro parametro) {
        try {
            if (parametro.getPmCod() == null || parametro.getPmNombre() == null) {
                return new Parametro.MensajeRespuesta(204L, "El código o nombre del parámetro no pueden ser nulos.", null);
            }

            // Crear la clave primaria compuesta
            ParametroPK parametroPK = new ParametroPK(parametro.getPmCod(), parametro.getPmNombre());

            // Verificar si existe el registro con la clave primaria compuesta
            if (!parametroRepository.existsById(parametroPK)) {
                return new Parametro.MensajeRespuesta(204L, "Parámetro no encontrado.", null);
            }

            Parametro updatedParametro = parametroRepository.save(parametro);
            return new Parametro.MensajeRespuesta(200L, "Parámetro actualizado exitosamente.", List.of(updatedParametro));
        } catch (Exception e) {
            e.printStackTrace();
            return new Parametro.MensajeRespuesta(500L, "Error al actualizar el Parámetro: " + e.getMessage(), null);
        }
    }*/

    public Parametro.MensajeRespuesta deleteParametro(Long pmCod, String pmNombre) {
        try {
            // Crear la clave primaria compuesta
            ParametroPK parametroPK = new ParametroPK(pmCod, pmNombre);

            // Verificar si el parámetro con la clave primaria compuesta existe
            if (parametroRepository.existsById(parametroPK)) {
                // Eliminar el registro si existe
                parametroRepository.deleteById(parametroPK);
                return new Parametro.MensajeRespuesta(200L, "Parámetro eliminado exitosamente.", null);
            } else {
                // Si no existe, devolver respuesta con código 204
                return new Parametro.MensajeRespuesta(204L, "Parámetro no encontrado.", null);
            }
        } catch (JpaSystemException e) {
            // Caso en el que el parámetro está referenciado por otros registros
            String mensaje = "No se puede eliminar el Parámetro porque está referenciado por otros registros.";
            return new Parametro.MensajeRespuesta(204L, "Error al eliminar el Parámetro: " + mensaje, null);
        } catch (Exception e) {
            // Otros errores generales
            return new Parametro.MensajeRespuesta(500L, "Error al eliminar el Parámetro: " + e.getMessage(), null);
        }
    }

}
