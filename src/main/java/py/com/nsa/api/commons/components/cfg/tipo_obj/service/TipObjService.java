package py.com.nsa.api.commons.components.cfg.tipo_obj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.tipo_obj.model.TipObj;
import py.com.nsa.api.commons.components.cfg.tipo_obj.repository.TipObjRepository;

import java.util.List;

@Service
public class TipObjService {
    @Autowired
    private TipObjRepository repository;

    public List<TipObj> getList() {
        return (List<TipObj>) repository.findAll();
    }

    public TipObj save(TipObj tipObj) {
        return repository.save(tipObj);
    }

    public TipObj update(TipObj tipObj) {
        return repository.save(tipObj);
    }

    public boolean deleteById(Long id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        } catch (DataIntegrityViolationException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
