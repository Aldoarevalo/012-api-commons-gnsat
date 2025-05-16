package py.com.nsa.api.commons.components.cfg.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.app.model.App;

@Repository
public interface AppRepository extends JpaRepository<App, Long> {

}
