package py.com.nsa.api.commons.components.ref.persona.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.ref.persona.model.Persona;
import py.com.nsa.api.commons.components.ref.persona.service.PersonaService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/persona")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonaService personService;

    @GetMapping("/lista")
    public ResponseEntity<Persona.MensajeRespuesta> getAllPersons() {
        try {
            Persona.MensajeRespuesta respuesta = personService.getPersonasAll();
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al obtener las personas: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Persona.MensajeRespuesta(500L, "Error al obtener las personas: " + e.getMessage(), null));
        }
    }

    @PostMapping("/lista-filtro")
    public ResponseEntity<Persona.MensajeRespuesta> getPersonaFiltered(@RequestBody Persona filtro) {
        try {
            Persona.MensajeRespuesta respuesta = personService.getPersonaFiltered(filtro);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al obtener Personas: {} ===>", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Persona.MensajeRespuesta(500L, "Error al obtener personas: " + e.getMessage(), null));
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<Persona.MensajeRespuesta> insertPerson(@Valid @RequestBody Persona person) {
        try {
            Persona.MensajeRespuesta respuesta = personService.insertarPersona(person);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al insertar la persona: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Persona.MensajeRespuesta(500L, "Error al insertar la persona: " + e.getMessage(), null));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Persona.MensajeRespuesta> updatePerson(@Valid @RequestBody Persona person) {
        try {
            // Imprimir los datos recibidos
            System.out.println("Datos recibidos para la actualización: " + person);
            Persona.MensajeRespuesta respuesta = personService.updatePersona(person);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("Error al actualizar la persona con ID {}: {}", person.getPcod(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Persona.MensajeRespuesta(500L, "Error al actualizar la persona: " + e.getMessage(),
                            null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Persona.MensajeRespuesta> deletePersona(@RequestParam("pCod") Long pcod) {
        try {
            Persona.MensajeRespuesta respuesta = personService.deletePersona(pcod);
            if (200L == respuesta.getStatus()) {
                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            }
        } catch (RuntimeException e) {
            logger.error("<=== Error al eliminar la persona con ID {}: {} ===>", pcod, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Persona.MensajeRespuesta(500L, "Error al eliminar la persona: " + e.getMessage(), null));
        }
    }

    // Nuevo método para obtener los barrios por ciuad
    @GetMapping("/ciudad")
    public ResponseEntity<Map<String, Object>> getCiudadByPais(@RequestParam("paCod") Long paCod) {
        try {
            Map<String, Object> response = personService.getCiudadByPais(paCod);
            int status = (int) response.get("status");
            if (status == 200) {
                return ResponseEntity.ok(response);
            } else if (status == 204) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener las Ciudades por pais con código {}: {} ===>", paCod, e.getMessage(),
                    e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("mensaje", "Error al obtener las Ciudades: " + e.getMessage());
            errorResponse.put("detalles", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Nuevo método para obtener los barrios por país
    @GetMapping("/barrio")
    public ResponseEntity<Map<String, Object>> getBarriosByCiudad(@RequestParam("ciuCod") Long ciuCod) {
        try {
            Map<String, Object> response = personService.getBarriosByCiudad(ciuCod);
            int status = (int) response.get("status");
            if (status == 200) {
                return ResponseEntity.ok(response);
            } else if (status == 204) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los Barrios por Ciudad con código {}: {} ===>", ciuCod, e.getMessage(),
                    e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("mensaje", "Error al obtener las Ciudades: " + e.getMessage());
            errorResponse.put("detalles", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/bpostal")
    public ResponseEntity<Map<String, Object>> getbPostal(@RequestParam("bcod") Long bcod) {
        try {
            Map<String, Object> response = personService.getbPostal(bcod);
            int status = (int) response.get("status");
            if (status == 200) {
                return ResponseEntity.ok(response);
            } else if (status == 204) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener el codigo postal del Barrio con código {}: {} ===>", bcod,
                    e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("mensaje", "Error al obtener los codigos postales: " + e.getMessage());
            errorResponse.put("detalles", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Nuevo método para obtener los barrios por país
    @GetMapping("/buscarPorDocumento")
    public ResponseEntity<Map<String, Object>> getbyDocumento(@RequestParam("PDocNroDoc") String PDocNroDoc) {
        try {
            Map<String, Object> response = personService.getbyDocumento(PDocNroDoc);
            int status = (int) response.get("status");
            if (status == 200) {
                return ResponseEntity.ok(response);
            } else if (status == 204) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }
        } catch (Exception e) {
            logger.error("<=== Error al obtener los datos por nombre {}: {} ===>", PDocNroDoc, e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", 500);
            errorResponse.put("mensaje", "Error al obtener los datos de la persona: " + e.getMessage());
            errorResponse.put("detalles", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
