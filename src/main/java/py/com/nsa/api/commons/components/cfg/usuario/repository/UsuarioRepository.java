package py.com.nsa.api.commons.components.cfg.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import py.com.nsa.api.commons.components.cfg.usuario.model.Usuario;

import java.util.Date;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

        boolean existsByusuNombreIgnoreCase(String usuNombre);

        @Query("SELECT u.usuContrasena FROM Usuario u WHERE u.usuCod = :usuCod")
        String getusuContrasena(@Param("usuCod") Long usuCod);

        @Query("SELECT u.usuNombre FROM Usuario u WHERE u.usuCod = :usuCod")
        String getusuNombre(@Param("usuCod") Long usuCod);

        @Query("SELECT u.usuCambioPass FROM Usuario u WHERE u.usuCod = :usuCod")
        String getusuCambioPass(@Param("usuCod") Long usuCod);

        @Query("SELECT u.eCod FROM Usuario u WHERE u.usuCod = :usuCod")
        Long getECod(@Param("usuCod") Long usuCod);

        @Query("SELECT u.usuEstado FROM Usuario u WHERE u.usuCod = :usuCod")
        String getusuEstado(@Param("usuCod") Long usuCod);

        @Query("SELECT u.usuContador FROM Usuario u WHERE u.usuCod = :usuCod")
        Long getusuContador(@Param("usuCod") Long usuCod);

        @Query("SELECT u.usuFechavenc FROM Usuario u WHERE u.usuCod = :usuCod")
        Date getusuFechavenc(@Param("usuCod") Long usuCod);

        @Query("SELECT u FROM Usuario u " +
                        "LEFT JOIN UsuarioAgencia ua ON u.usuCod = ua.usuario.usuCod " +
                        "LEFT JOIN Agencia a ON ua.agencia.agCod = a.agCod " +
                        "LEFT JOIN Empleado e ON u.empleado.eCod = e.eCod " + // Relación Usuario - Empleado
                        "LEFT JOIN Persona p ON e.persona.pcod = p.pcod " + // Relación Empleado - Persona
                        "WHERE (:nombreUsuarioOEmpleado IS NULL OR :nombreUsuarioOEmpleado = '' OR " +
                        "UPPER(p.pnombre) LIKE %:#{#nombreUsuarioOEmpleado == null || #nombreUsuarioOEmpleado.isEmpty() ? '' : #nombreUsuarioOEmpleado.toUpperCase()}% OR "
                        +
                        "UPPER(u.usuNombre) LIKE %:#{#nombreUsuarioOEmpleado == null || #nombreUsuarioOEmpleado.isEmpty() ? '' : #nombreUsuarioOEmpleado.toUpperCase()}% OR "
                        +
                        "UPPER(p.papellido) LIKE %:#{#nombreUsuarioOEmpleado == null || #nombreUsuarioOEmpleado.isEmpty() ? '' : #nombreUsuarioOEmpleado.toUpperCase()}%) "
                        +
                        "AND (:nombreAgencia IS NULL OR :nombreAgencia = '' OR " +
                        "UPPER(a.agNombreFantasia) LIKE %:#{#nombreAgencia == null || #nombreAgencia.isEmpty() ? '' : #nombreAgencia.toUpperCase()}%)")
        List<Usuario> findByFilters(@Param("nombreUsuarioOEmpleado") String nombreUsuarioOEmpleado,
                        @Param("nombreAgencia") String nombreAgencia);

        boolean existsByeCod(Long eCod);

        List<Usuario> findAllByUsuCodIn(List<Long> usuCods);

}
