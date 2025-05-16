package py.com.nsa.api.commons.components.cfg.cliente.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.cliente.model.Cliente;
import py.com.nsa.api.commons.components.cfg.cliente.repository.ClienteRepository;
import py.com.nsa.api.commons.components.ref.persona.model.Persona;
import py.com.nsa.api.commons.components.ref.persona.repository.PersonaRepository;

import java.util.Date;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PersonaRepository personaRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    public Cliente.MensajeRespuesta getList() {
        try {
            List<Cliente> clientes = repository.findAll();
            if (clientes != null && !clientes.isEmpty()) {
                return new Cliente.MensajeRespuesta(200L, "Clientes obtenidos exitosamente.", clientes);
            } else {
                return new Cliente.MensajeRespuesta(204L, "No se encontraron clientes.", null);
            }
        } catch (Exception e) {
            return new Cliente.MensajeRespuesta(500L, "Error al obtener clientes: " + e.getMessage(), null);
        }
    }

    public Cliente.MensajeRespuesta getClientesFiltrados(Cliente filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreNullValues()
                    .withMatcher("ccod", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("cruc", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("ctelContacto", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                    .withMatcher("cesCorrentista", ExampleMatcher.GenericPropertyMatchers.exact())
                    .withMatcher("persona.pnombre", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

            Example<Cliente> example = Example.of(filtro, matcher);
            List<Cliente> clientes = repository.findAll(example);

            if (!clientes.isEmpty()) {
                return new Cliente.MensajeRespuesta(200L, "Clientes encontrados", clientes);
            } else {
                return new Cliente.MensajeRespuesta(204L, "No se encontraron clientes", null);
            }
        } catch (Exception e) {
            return new Cliente.MensajeRespuesta(500L, "Error al filtrar clientes: " + e.getMessage(), null);
        }
    }

    public Cliente.MensajeRespuesta save(Cliente cliente) {
        try {
            if (cliente.getCFechaCreacion() == null) {
                cliente.setCFechaCreacion(new Date());
            }

            Cliente savedCliente = repository.save(cliente);
            return new Cliente.MensajeRespuesta(200L, "Cliente insertado exitosamente.", List.of(savedCliente));
        } catch (Exception e) {
            return new Cliente.MensajeRespuesta(500L, "Error al insertar el cliente: " + e.getMessage(), null);
        }
    }

    @Transactional
    public Cliente.MensajeRespuesta update(Cliente cliente) {
        try {
            Cliente existingCliente = repository.findById(cliente.getCCod())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado."));

            cliente.setCFechaCreacion(existingCliente.getCFechaCreacion());
            cliente.setCFechaModificacion(new Date());

            //logger.error("personas clientes: {}", cliente);
             int   registrosActualizadosPersona = personaRepository.updatePersonaFields(
                        cliente.getPCod(),
                        cliente.getPdireccion(),
                        cliente.getCTelContacto(),
                        cliente.getBCod(),
                        cliente.getPaCod()
                );


            Cliente updatedCliente = repository.save(cliente);

            if (registrosActualizadosPersona > 0) {

                Persona updatedPersona = personaRepository.findById(cliente.getPCod()).orElse(null);

                if (updatedPersona != null) {
                    return new Cliente.MensajeRespuesta(200L,
                            "Cliente actualizado exitosamente.",
                            List.of(updatedCliente));
                } else {
                    return new Cliente.MensajeRespuesta(500L,
                            "Cliente actualizado, pero no se pudo recuperar la información de Persona.",
                            List.of(updatedCliente));
                }
            }

            return new Cliente.MensajeRespuesta(200L, "Cliente actualizado exitosamente.", List.of(updatedCliente));
        } catch (Exception e) {
            return new Cliente.MensajeRespuesta(500L, "Error al actualizar el cliente: " + e.getMessage(), null);
        }
    }
    public Cliente.MensajeRespuesta deleteById(Long ccod) {
        try {
            if (repository.existsById(ccod)) {
                repository.deleteById(ccod);
                return new Cliente.MensajeRespuesta(200L, "Cliente eliminado exitosamente.", null);
            } else {
                return new Cliente.MensajeRespuesta(204L, "Cliente no encontrado.", null);
            }
        } catch (JpaSystemException e) {
            return new Cliente.MensajeRespuesta(204L, "No se puede eliminar el cliente porque está referenciado por otros registros.", null);
        } catch (Exception e) {
            return new Cliente.MensajeRespuesta(500L, "Error al eliminar el cliente: " + e.getMessage(), null);
        }
    }
}