package py.com.nsa.api.commons.components.cfg.cliente_limite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.cliente_limite.model.ClienteLimite;
import py.com.nsa.api.commons.components.cfg.cliente_limite.model.pk.ClienteLimitePK;
import py.com.nsa.api.commons.components.cfg.cliente_limite.repository.ClienteLimiteRepository;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ClienteLimiteService {

    @Autowired
    private ClienteLimiteRepository repository;

    public ClienteLimite.MensajeRespuesta getList() {
        try {
            List<ClienteLimite> clienteLimites = repository.findAll();
            if (clienteLimites != null && !clienteLimites.isEmpty()) {
                return new ClienteLimite.MensajeRespuesta(200L, "Límites de cliente obtenidos exitosamente.", clienteLimites);
            } else {
                return new ClienteLimite.MensajeRespuesta(204L, "No se encontraron límites de cliente.", null);
            }
        } catch (Exception e) {
            return new ClienteLimite.MensajeRespuesta(500L, "Error al obtener límites de cliente: " + e.getMessage(), null);
        }
    }

    // In ClienteLimiteService.java
    public ClienteLimite.MensajeRespuesta getClientesLimitesFiltrados(ClienteLimite filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("ccodCliente", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("cestado", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("tlCod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("cliTipoDocumento", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("cliNombre", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("cliente.ccod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("cliente.cruc", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("tipolimite.tlDescripcion", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            Example<ClienteLimite> example = Example.of(filtro, matcher);
            List<ClienteLimite> clienteLimites = repository.findAll(example);

            if (!clienteLimites.isEmpty()) {
                return new ClienteLimite.MensajeRespuesta(200L, "Clientes límites encontrados", clienteLimites);
            } else {
                return new ClienteLimite.MensajeRespuesta(204L, "No se encontraron clientes límites", null);
            }
        } catch (Exception e) {
            return new ClienteLimite.MensajeRespuesta(500L, "Error al filtrar clientes límites: " + e.getMessage(), null);
        }
    }
    public ClienteLimite.MensajeRespuesta save(ClienteLimite clienteLimite) {
        try {
            // Validación de valores nulos
            if (clienteLimite.getCCodCliente() == null || clienteLimite.getTlCod() == null) {
                return new ClienteLimite.MensajeRespuesta(
                        204L, "Códigos de cliente o tipo de límite no pueden ser nulos.", null
                );
            }

            // Establecer fechas de creación y modificación
            Date fechaActual = new Date();
            clienteLimite.setCliFechaGrab(fechaActual);
            clienteLimite.setCliFechaMod(fechaActual);

            // Si el estado no está establecido, configurarlo como 'A' (Activo)
            /*if (clienteLimite.getCEstado() == null) {
                clienteLimite.setCEstado("A");
            }*/

            ClienteLimite savedClienteLimite = repository.save(clienteLimite);
            return new ClienteLimite.MensajeRespuesta(200L, "Límite de cliente insertado exitosamente.", List.of(savedClienteLimite));
        } catch (Exception e) {
            return new ClienteLimite.MensajeRespuesta(500L, "Error al insertar el límite de cliente: " + e.getMessage(), null);
        }
    }

    /*public ClienteLimite.MensajeRespuesta update(ClienteLimite clienteLimite) {
        try {
            if (clienteLimite.getCCodCliente() == null || !repository.existsById(clienteLimite.getCCodCliente())) {
                return new ClienteLimite.MensajeRespuesta(204L, "Límite de cliente no encontrado.", null);
            }

            ClienteLimite updatedClienteLimite = repository.save(clienteLimite);
            return new ClienteLimite.MensajeRespuesta(200L, "Límite de cliente actualizado exitosamente.", List.of(updatedClienteLimite));
        } catch (Exception e) {
            return new ClienteLimite.MensajeRespuesta(500L, "Error al actualizar el límite de cliente: " + e.getMessage(), null);
        }
    }*/

    public ClienteLimite.MensajeRespuesta update(ClienteLimite clienteLimite) {
        try {
            // Validación de valores nulos
            if (clienteLimite.getCCodCliente() == null || clienteLimite.getTlCod() == null) {
                return new ClienteLimite.MensajeRespuesta(
                        204L, "Códigos de cliente o tipo de límite no pueden ser nulos.", null
                );
            }

            // Establecer la fecha de modificación actual
            Date fechaModificacion = new Date();
            clienteLimite.setCliFechaMod(fechaModificacion);

            // Llamada al repositorio con los valores correctos
            int registrosActualizados = repository.actualizarClienteLimite(
                    clienteLimite.getCEstado(),
                    clienteLimite.getCliMonto(),
                    clienteLimite.getCliCantTransacc(),
                    clienteLimite.getCliNombre(),
                    clienteLimite.getUsuMod(),
                    fechaModificacion,
                    clienteLimite.getCCodCliente(),
                    clienteLimite.getTlCod(),
                    clienteLimite.getTlCodold()
            );

            // Comprobar si se actualizaron registros
            if (registrosActualizados > 0) {
                ClienteLimitePK clientelimitePK = new ClienteLimitePK(clienteLimite.getCCodCliente(), clienteLimite.getTlCod());
                ClienteLimite updatedClientelimite = repository.findById(clientelimitePK).orElse(null);

                if (updatedClientelimite != null) {
                    return new ClienteLimite.MensajeRespuesta(200L,
                            "Límite del cliente actualizado exitosamente.",
                            List.of(updatedClientelimite));
                } else {
                    return new ClienteLimite.MensajeRespuesta(500L,
                            "Límite del Cliente actualizado, pero no se pudieron recuperar los datos.",
                            null);
                }
            } else {
                return new ClienteLimite.MensajeRespuesta(500L,
                        "No se pudo actualizar el límite de Cliente.",
                        null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ClienteLimite.MensajeRespuesta(500L,
                    "Error al actualizar el límite del Cliente: " + e.getMessage(), null);
        }
    }

    public ClienteLimite.MensajeRespuesta delete(Long ccodCliente, Long tlCod) {
        try {
            // Crear la clave primaria compuesta
            ClienteLimitePK clientelimitePK = new ClienteLimitePK(ccodCliente, tlCod);

            // Verificar si el registro con la clave primaria compuesta existe
            if (repository.existsById(clientelimitePK)) {
                // Eliminar el registro si existe
                repository.deleteById(clientelimitePK);
                return new ClienteLimite.MensajeRespuesta(200L, "Límite de Cliente eliminado exitosamente.", null);
            } else {
                // Si no existe, devolver respuesta con código 204
                return new ClienteLimite.MensajeRespuesta(204L, "Límite de Cliente no encontrado.", null);
            }
        } catch (JpaSystemException e) {

            return new ClienteLimite.MensajeRespuesta(204L, "No se puede eliminar el Límite del Cliente porque está referenciado por otros registros: " , null);
        } catch (Exception e) {
            // Otros errores generales
            return new ClienteLimite.MensajeRespuesta(500L, "Error al eliminar el Límite del Cliente: " + e.getMessage(),
                    null);
        }
    }
}