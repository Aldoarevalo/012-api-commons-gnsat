package py.com.nsa.api.commons.components.cfg.menu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import py.com.nsa.api.commons.components.cfg.menu.model.Menu;
import py.com.nsa.api.commons.components.cfg.menu.repository.MenuRepository;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuRepository repository;

    public Menu.MensajeRespuesta getMenuAll() {
        try {
            List<Menu> menus = repository.findAll();

            if (menus.isEmpty()) {
                return new Menu.MensajeRespuesta(204L, "No se encontraron Menus.", null);
            }
            return new Menu.MensajeRespuesta(200L, "Menus obtenidos exitosamente.", menus);
        } catch (Exception e) {
            System.err.println("Error al obtener los Menus: " + e.getMessage());
            e.printStackTrace();
            return new Menu.MensajeRespuesta(500L, "Error al obtener los Menus: " + e.getMessage(), null);
        }
    }

}