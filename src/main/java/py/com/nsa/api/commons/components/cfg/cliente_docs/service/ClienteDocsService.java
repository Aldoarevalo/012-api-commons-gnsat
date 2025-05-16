package py.com.nsa.api.commons.components.cfg.cliente_docs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.cliente.model.Cliente;
import py.com.nsa.api.commons.components.cfg.cliente_docs.model.ClienteDocs;
import py.com.nsa.api.commons.components.cfg.cliente_docs.model.pk.ClienteDocsPK;
import py.com.nsa.api.commons.components.cfg.cliente_docs.repository.ClienteDocsRepository;
import py.com.nsa.api.commons.components.cfg.cliente_docs.repository.JdbcClienteDocsRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Service
public class ClienteDocsService {

    @Autowired
    private ClienteDocsRepository repository;

    @Autowired
    private JdbcClienteDocsRepository jdbcRepository;

    public ClienteDocsService(JdbcClienteDocsRepository jdbcRepository) {
        this.jdbcRepository = jdbcRepository;
    }

    public ClienteDocs.MensajeRespuesta getList() {
        try {
            List<ClienteDocs> documentos = repository.findAll();
            if (documentos != null && !documentos.isEmpty()) {
                return new ClienteDocs.MensajeRespuesta(200L, "Documentos obtenidos exitosamente.", documentos);
            } else {
                return new ClienteDocs.MensajeRespuesta(204L, "No se encontraron documentos.", null);
            }
        } catch (Exception e) {
            return new ClienteDocs.MensajeRespuesta(500L, "Error al obtener documentos: " + e.getMessage(), null);
        }
    }

    public ClienteDocs.MensajeRespuesta getClienteDocsFiltered(ClienteDocs filtro) {
        try {
            // Configuración del ExampleMatcher para filtros
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("cdCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("agCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("cdCodCliente", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("cdTipoDoc", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("cdDescripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("cdFechaVto", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withIgnorePaths("cdArchivos"); // Ignoramos el campo de archivo binario

            // Crear Example con el filtro y matcher
            Example<ClienteDocs> example = Example.of(filtro, matcher);

            // Realizar la consulta usando el filtro
            List<ClienteDocs> documentos = repository.findAll(example);

            // Devolver resultado en el formato requerido
            if (!documentos.isEmpty()) {
                return new ClienteDocs.MensajeRespuesta(200L,
                        "Documentos de cliente encontrados", documentos);
            } else {
                return new ClienteDocs.MensajeRespuesta(204L,
                        "No se encontraron documentos de cliente", null);
            }
        } catch (Exception e) {
            return new ClienteDocs.MensajeRespuesta(500L,
                    "Error al filtrar documentos de cliente: " + e.getMessage(), null);
        }
    }

    public ClienteDocs.MensajeRespuesta insert(ClienteDocs documento, String base64File) {
        try {
            // Validate that the file is not empty
            if (base64File == null || base64File.isEmpty()) {
                return new ClienteDocs.MensajeRespuesta(204L, "El archivo Base64 está vacío.", null);
            }

            // Validate that AG_COD is not null
            if (documento.getAgCod() == null) {
                return new ClienteDocs.MensajeRespuesta(204L, "El código de agencia (AG_COD) es obligatorio.", null);
            }

            // Decode the Base64 file to a byte array
            byte[] decodedBytes = Base64.getDecoder().decode(base64File);

            // Create an InputStream from the byte array
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);
            long fileSize = decodedBytes.length;

            // Call repository to save the document with BLOB
            jdbcRepository.saveClienteDocsWithBlob(documento, inputStream, fileSize);

            return new ClienteDocs.MensajeRespuesta(200L, "Documento de cliente insertado exitosamente.", List.of(documento));
        } catch (IllegalArgumentException e) {
            // Handle Base64 decoding error
            return new ClienteDocs.MensajeRespuesta(204L, "El archivo no está en un formato Base64 válido.", null);
        } catch (Exception e) {
            // Handle general errors
            return new ClienteDocs.MensajeRespuesta(500L, "Error al insertar el documento: " + e.getMessage(), null);
        }
    }

    public ClienteDocs.MensajeRespuesta update(ClienteDocs documento, String base64File) {
        try {
            // Validate that the file is not empty
            if (base64File == null || base64File.isEmpty()) {
                return new ClienteDocs.MensajeRespuesta(204L, "El archivo Base64 está vacío.", null);
            }

            // Validate that AG_COD is not null
            if (documento.getAgCod() == null) {
                return new ClienteDocs.MensajeRespuesta(204L, "El código de agencia (AG_COD) es obligatorio.", null);
            }

            // Verify if the document exists
           /* ClienteDocsPK clienteDocsPK = new ClienteDocsPK(documento.getCdCodCliente(), documento.getCdTipoDoc());
            if (!repository.existsById(clienteDocsPK)) {
                return new ClienteDocs.MensajeRespuesta(204L, "Documento del Cliente no encontrado.", null);
            }*/
            if (!repository.existsById(documento.getCdCod())) {
                return new ClienteDocs.MensajeRespuesta(204L, "Documento del Cliente no encontrado.", null);
            }


            // Decode the Base64 file to a byte array
            byte[] decodedBytes = Base64.getDecoder().decode(base64File);

            // Create an InputStream from the byte array
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);
            long fileSize = decodedBytes.length;

            // Call repository to update the document with BLOB
            jdbcRepository.updateClienteDocsWithBlob(documento, inputStream, fileSize);

            return new ClienteDocs.MensajeRespuesta(200L, "Documento de cliente actualizado exitosamente.", List.of(documento));
        } catch (IllegalArgumentException e) {
            // Handle Base64 decoding error
            return new ClienteDocs.MensajeRespuesta(204L, "El archivo no está en un formato Base64 válido.", null);
        } catch (Exception e) {
            // Handle general errors
            return new ClienteDocs.MensajeRespuesta(500L, "Error al actualizar el documento: " + e.getMessage(), null);
        }
    }

    public ClienteDocs.MensajeRespuesta deleteclientedocs(Long cdCod) {
        try {
            // Check if the record with the primary key exists
            if (repository.existsById(cdCod)) {
                // Delete the record if it exists
                repository.deleteById(cdCod);
                return new ClienteDocs.MensajeRespuesta(200L, "Documento del cliente eliminado exitosamente.", null);
            } else {
                // If it doesn't exist, return a response with code 204
                return new ClienteDocs.MensajeRespuesta(204L, "Documento de Cliente no encontrado.", null);
            }
        } catch (JpaSystemException e) {
            return new ClienteDocs.MensajeRespuesta(204L, "No se puede eliminar el documento del cliente porque está referenciado por otros registros.", null);
        } catch (Exception e) {
            // Other general errors
            return new ClienteDocs.MensajeRespuesta(500L, "Error al eliminar el Documento del Cliente: " + e.getMessage(), null);
        }
    }
}