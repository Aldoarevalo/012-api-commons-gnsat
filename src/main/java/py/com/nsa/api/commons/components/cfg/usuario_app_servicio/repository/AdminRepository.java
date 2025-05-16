package py.com.nsa.api.commons.components.cfg.usuario_app_servicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.cfg.usuario_app_servicio.model.Admin;
import py.com.nsa.api.commons.components.cfg.usuario_app_servicio.model.pk.AdminPK;

@Repository
public interface AdminRepository extends JpaRepository<Admin, AdminPK> {
}
