package py.com.nsa.api.commons.components.nsa_web.contacto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import py.com.nsa.api.commons.components.nsa_web.contacto.model.ContactoTelefonico;

@Repository
public interface ContactoTelefonicoRepository extends JpaRepository<ContactoTelefonico, Long> {
}