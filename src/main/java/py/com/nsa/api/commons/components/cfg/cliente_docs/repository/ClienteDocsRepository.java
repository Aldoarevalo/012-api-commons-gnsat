package py.com.nsa.api.commons.components.cfg.cliente_docs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import py.com.nsa.api.commons.components.cfg.cliente_docs.model.ClienteDocs;
import py.com.nsa.api.commons.components.cfg.cliente_docs.model.pk.ClienteDocsPK;

@Repository
public interface ClienteDocsRepository extends JpaRepository<ClienteDocs, Long> {
}


