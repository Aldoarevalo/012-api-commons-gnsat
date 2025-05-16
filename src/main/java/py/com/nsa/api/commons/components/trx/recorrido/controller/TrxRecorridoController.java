package py.com.nsa.api.commons.components.trx.recorrido.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import py.com.nsa.api.commons.components.trx.recorrido.model.TrxRecorrido;
import py.com.nsa.api.commons.components.trx.recorrido.model.TrxRecorridoId;
import py.com.nsa.api.commons.components.trx.recorrido.service.TrxRecorridoService;

@RestController
@RequestMapping("/trx-recorrido")
public class TrxRecorridoController {

    @Autowired
    private TrxRecorridoService service;

    @GetMapping("/lista")
    public ResponseEntity<TrxRecorrido.MensajeRespuesta> listarTodos() {
        TrxRecorrido.MensajeRespuesta respuesta = service.listarTodos();
        return mapResponse(respuesta);
    }

    @PostMapping("/insert")
    public ResponseEntity<TrxRecorrido.MensajeRespuesta> insertar(@RequestBody TrxRecorrido recorrido) {
        TrxRecorrido.MensajeRespuesta respuesta = service.insertar(recorrido);
        return mapResponse(respuesta);
    }

    @PutMapping("/update")
    public ResponseEntity<TrxRecorrido.MensajeRespuesta> actualizar(@RequestBody TrxRecorrido recorrido) {
        TrxRecorrido.MensajeRespuesta respuesta = service.actualizar(recorrido);
        return mapResponse(respuesta);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<TrxRecorrido.MensajeRespuesta> eliminar(@RequestBody TrxRecorridoId id) {
        TrxRecorrido.MensajeRespuesta respuesta = service.eliminar(id);
        return mapResponse(respuesta);
    }

    // Método auxiliar para mapear respuestas
    private ResponseEntity<TrxRecorrido.MensajeRespuesta> mapResponse(TrxRecorrido.MensajeRespuesta respuesta) {
        switch (respuesta.getStatus().intValue()) {
            case 200:
                return ResponseEntity.ok(respuesta);
            case 201:
                return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
            case 204:
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuesta);
            case 400:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
            case 404:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
        }
    }
}