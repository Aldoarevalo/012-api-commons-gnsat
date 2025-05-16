package py.com.nsa.api.commons.components.cfg.tipodocagencia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.tipodocagencia.model.TipoDocAgencia;
import py.com.nsa.api.commons.components.cfg.tipodocagencia.repository.TipoDocAgenciaRepository;

import java.util.List;

@Service
public class TipoDocAgenciaService {

    @Autowired
    private TipoDocAgenciaRepository tipoDocAgenciaRepository;

    public TipoDocAgencia.MensajeRespuesta getTipoDocAgenciasAll() {
        try {
            List<TipoDocAgencia> tipoDocAgencias = tipoDocAgenciaRepository.findAll();
            if (tipoDocAgencias.isEmpty()) {
                return new TipoDocAgencia.MensajeRespuesta(204L, "No se encontraron tipos de documentos de agencia.", null);
            }
            return new TipoDocAgencia.MensajeRespuesta(200L, "Tipos de documentos de agencia obtenidos exitosamente.", tipoDocAgencias);
        } catch (Exception e) {
            e.printStackTrace();
            return new TipoDocAgencia.MensajeRespuesta(500L, "Error al obtener tipos de documentos de agencia: " + e.getMessage(), null);
        }
    }

    public TipoDocAgencia.MensajeRespuesta insertarTipoDocAgencia(TipoDocAgencia tipoDocAgencia) {
        try {
            // Verificar si ya existe un TipoDocAgencia con el mismo nombre ignorando mayúsculas/minúsculas
            if (tipoDocAgenciaRepository.existsByTcdNombreDocIgnoreCase(tipoDocAgencia.getTcdNombreDoc())) {
                return new TipoDocAgencia.MensajeRespuesta(204L, "Ya existe un tipo de documento de agencia con el nombre '" + tipoDocAgencia.getTcdNombreDoc() + "'.", null);
            }
            TipoDocAgencia nuevoTipoDocAgencia = tipoDocAgenciaRepository.save(tipoDocAgencia);
            return new TipoDocAgencia.MensajeRespuesta(200L, "Tipo de documento de agencia creado exitosamente.", List.of(nuevoTipoDocAgencia));
        } catch (Exception e) {
            e.printStackTrace();
            return new TipoDocAgencia.MensajeRespuesta(500L, "Error al insertar el tipo de documento de agencia: " + e.getMessage(), null);
        }
    }

    public TipoDocAgencia.MensajeRespuesta updateTipoDocAgencia(TipoDocAgencia tipoDocAgencia) {
        try {
            /*
            if (tipoDocAgencia.getTcdCod() == null || !tipoDocAgenciaRepository.existsById(tipoDocAgencia.getTcdCod())) {
                return new TipoDocAgencia.MensajeRespuesta(204L, "Tipo de documento de agencia no encontrado.", null);
            }
             */
            // Verificar si el nombre que se quiere asignar ya pertenece a otro registro (con tcdCod diferente)
            if (tipoDocAgenciaRepository.existsByTcdNombreDocIgnoreCaseAndTcdCodNot(tipoDocAgencia.getTcdNombreDoc(), tipoDocAgencia.getTcdCod())) {
                return new TipoDocAgencia.MensajeRespuesta(204L, "El nombre '" + tipoDocAgencia.getTcdNombreDoc() + "' ya está siendo utilizado por otro tipo de documento de agencia.", null);
            }
            TipoDocAgencia updatedTipoDocAgencia = tipoDocAgenciaRepository.save(tipoDocAgencia);
            return new TipoDocAgencia.MensajeRespuesta(200L, "Tipo de documento de agencia actualizado exitosamente.", List.of(updatedTipoDocAgencia));
        } catch (Exception e) {
            e.printStackTrace();
            return new TipoDocAgencia.MensajeRespuesta(500L, "Error al actualizar el tipo de documento de agencia: " + e.getMessage(), null);
        }
    }

    public TipoDocAgencia.MensajeRespuesta deleteTipoDocAgencia(Long tcdCod) {
        try {
            if (tipoDocAgenciaRepository.existsById(tcdCod)) {
                tipoDocAgenciaRepository.deleteById(tcdCod);
                return new TipoDocAgencia.MensajeRespuesta(200L, "Tipo de documento de agencia eliminado exitosamente", null);
            } else {
                return new TipoDocAgencia.MensajeRespuesta(204L, "Tipo de documento de agencia con código " + tcdCod + " no encontrado", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new TipoDocAgencia.MensajeRespuesta(500L, "Error al eliminar el tipo de documento de agencia: " + e.getMessage(), null);
        }
    }
}
