package py.com.nsa.api.commons.components.cfg.tipo_obj.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import py.com.nsa.api.commons.components.cfg.tipo_obj.model.TipObj;
import py.com.nsa.api.commons.components.cfg.tipo_obj.service.TipObjService;

import java.util.List;

@RestController
@RequestMapping("tipo_obj")
public class TipObjController {
    @Autowired
    private TipObjService service;

    @GetMapping("/lista")
    public List<TipObj> getList() {
        return service.getList();
    }

    @PostMapping("/insert")
    public TipObj save(@Valid @RequestBody TipObj tipObj) {
        return service.save(tipObj);
    }

    @PutMapping("/update")
    public TipObj update(@Valid @RequestBody TipObj tipObj) {
        return service.update(tipObj);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Boolean> delete(@PathVariable("codigo") Long codigo) {
        boolean result = service.deleteById(codigo);
        return ResponseEntity.ok(result);
    }
}
