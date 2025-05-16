package py.com.nsa.api.commons.components.cfg.documento_agencia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import py.com.nsa.api.commons.components.cfg.documento_agencia.model.DocumentoAgencia;
import py.com.nsa.api.commons.components.cfg.documento_agencia.repository.DocumentoAgenciaRepository;
import py.com.nsa.api.commons.components.cfg.documento_agencia.repository.JdbcDocumentoRepository;
import py.com.nsa.api.commons.components.cfg.equipo.model.Equipo;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentoAgenciaService {
    @Autowired
    private DocumentoAgenciaRepository repository;
    private JdbcDocumentoRepository jdbcrepository;

    public DocumentoAgenciaService(JdbcDocumentoRepository jdbcrepository) {
        this.jdbcrepository = jdbcrepository;
    }

    public DocumentoAgencia.MensajeRespuesta getAgenciasAll() {
        try {
            List<DocumentoAgencia> DocAgencia = repository.findAll();

            if (DocAgencia.isEmpty()) {
                return new DocumentoAgencia.MensajeRespuesta(204L, "No se encontraron Agencias.", null);
            }
            return new DocumentoAgencia.MensajeRespuesta(200L, "Agencias obtenidas exitosamente.", DocAgencia);
        } catch (Exception e) {
            System.err.println("Error al obtener los documentos: " + e.getMessage());
            e.printStackTrace();
            return new DocumentoAgencia.MensajeRespuesta(500L,
                    "Error al obtener los Tipos de Documentos: " + e.getMessage(), null);
        }
    }

    public DocumentoAgencia.MensajeRespuesta getAgenciasFiltered(DocumentoAgencia filtro) {
        // Configuración del ExampleMatcher para filtros de coincidencia parcial y sin
        // sensibilidad a mayúsculas
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withMatcher("dcDoc", ExampleMatcher.GenericPropertyMatchers.exact()) // Coincidencia exacta para código
                                                                                      // de documento
                .withMatcher("agCod", ExampleMatcher.GenericPropertyMatchers.exact()) // Coincidencia exacta para código
                                                                                      // de agencia
                .withMatcher("tcdCod", ExampleMatcher.GenericPropertyMatchers.exact()) // Coincidencia exacta para
                                                                                       // código de tipo de documento
                .withMatcher("dcDescripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()) // Contiene
                                                                                                              // e
                                                                                                              // ignora
                                                                                                              // mayúsculas
                .withIgnorePaths("dcVencimiento", "dcAdjunto"); // Ignorar estos campos en la comparación

        // Crea un Example de DocumentoAgencia con el filtro y el matcher
        Example<DocumentoAgencia> example = Example.of(filtro, matcher);

        // Realiza la consulta usando el filtro como Example
        List<DocumentoAgencia> agencias = repository.findAll(example);

        // Devuelve el resultado en el formato requerido
        if (!agencias.isEmpty()) {
            return new DocumentoAgencia.MensajeRespuesta(200L, "Documento Agencia encontradas", agencias);
        } else {
            return new DocumentoAgencia.MensajeRespuesta(204L, "No se encontraron Documento Agencia", null);
        }
    }

    public DocumentoAgencia.MensajeRespuesta insert(DocumentoAgencia agencia, String base64File) {
        try {
            // Validar que el archivo no esté vacío
            if (base64File == null || base64File.isEmpty()) {
                return new DocumentoAgencia.MensajeRespuesta(204L, "El archivo Base64 está vacío.", null);
            }

            // Decodificar el archivo Base64 a un arreglo de bytes
            byte[] decodedBytes = Base64.getDecoder().decode(base64File);

            // Crear un InputStream a partir del arreglo de bytes
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);
            long fileSize = decodedBytes.length;

            // Llamada al repositorio para guardar el documento con BLOB
            jdbcrepository.saveDocumentoConBlob(agencia, inputStream, fileSize);

            return new DocumentoAgencia.MensajeRespuesta(200L, "Documento insertado exitosamente.", List.of(agencia));
        } catch (IllegalArgumentException e) {
            // Manejar error de decodificación Base64
            return new DocumentoAgencia.MensajeRespuesta(204L, "El archivo no está en un formato Base64 válido.", null);
        } catch (Exception e) {
            // Manejar errores generales
            return new DocumentoAgencia.MensajeRespuesta(500L, "Error al insertar el documento: " + e.getMessage(),
                    null);
        }
    }

    public DocumentoAgencia.MensajeRespuesta update(DocumentoAgencia agencia, String base64File) {
        try {
            // Validar que el archivo no esté vacío
            if (base64File == null || base64File.isEmpty()) {
                return new DocumentoAgencia.MensajeRespuesta(204L, "El archivo Base64 está vacío.", null);
            }

            // Decodificar el archivo Base64 a un arreglo de bytes
            byte[] decodedBytes = Base64.getDecoder().decode(base64File);

            // Crear un InputStream a partir del arreglo de bytes
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);
            long fileSize = decodedBytes.length;

            // Llamada al repositorio para guardar el documento con BLOB
            jdbcrepository.updateDocumentoConBlob(agencia, inputStream, fileSize);

            return new DocumentoAgencia.MensajeRespuesta(200L, "Documento actualizado exitosamente.", List.of(agencia));
        } catch (IllegalArgumentException e) {
            // Manejar error de decodificación Base64
            return new DocumentoAgencia.MensajeRespuesta(204L, "El archivo no está en un formato Base64 válido.", null);
        } catch (Exception e) {
            // Manejar errores generales
            return new DocumentoAgencia.MensajeRespuesta(500L, "Error al actualizar el documento: " + e.getMessage(),
                    null);
        }
    }

    public DocumentoAgencia.MensajeRespuesta deleteDocumento(Long codigo) {
        try {
            if (repository.existsById(codigo)) {
                repository.deleteById(codigo);
                return new DocumentoAgencia.MensajeRespuesta(200L, "Documento de Agencia con código " + codigo + " eliminado exitosamente", null);
            } else {
                return new DocumentoAgencia.MensajeRespuesta(204L, "Documento de Agencia con código " + codigo + " no encontrado", null);
            }
        } catch (JpaSystemException e) {
            // Personalizamos el mensaje de error
            String mensaje = "No se puede eliminar el Documento de la Agencia porque está referenciada por otros registros"; // Mensaje
            // personalizado
            return new DocumentoAgencia.MensajeRespuesta(204L,
                    "Error al eliminar el Documento de la Agencia: " + mensaje, null);
        } catch (Exception e) {
            return new DocumentoAgencia.MensajeRespuesta(500L, "Error al eliminar el Documento de Agencia con código " + codigo + ": " + e.getMessage(), null);
        }
    }

}