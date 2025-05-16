package py.com.nsa.api.commons.components.cfg.usuarioagencia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import py.com.nsa.api.commons.components.cfg.usuarioagencia.model.UsuarioAgencia;

import java.util.List;

public interface UsuarioAgenciaRepository extends JpaRepository<UsuarioAgencia, Long> {

    List<UsuarioAgencia> findAllByUsuarioUsuCodIn(List<Long> usuCods);

}
