package py.com.nsa.api.commons.components.cfg.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.menu.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

}
