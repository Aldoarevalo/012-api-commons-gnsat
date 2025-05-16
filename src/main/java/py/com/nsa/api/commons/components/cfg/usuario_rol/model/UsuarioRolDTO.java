package py.com.nsa.api.commons.components.cfg.usuario_rol.model;

public class UsuarioRolDTO {
    private Long usuCod;
    private String usuNombre;
    private String rolesCodigos;
    private String rolesNombres;

    public UsuarioRolDTO(Long usuCod, String usuNombre, String rolesCodigos, String rolesNombres) {
        this.usuCod = usuCod;
        this.usuNombre = usuNombre;
        this.rolesCodigos = rolesCodigos;
        this.rolesNombres = rolesNombres;
    }

    // Getters y Setters
    public Long getUsuCod() {
        return usuCod;
    }

    public void setUsuCod(Long usuCod) {
        this.usuCod = usuCod;
    }

    public String getUsuNombre() {
        return usuNombre;
    }

    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }

    public String getRolesCodigos() {
        return rolesCodigos;
    }

    public void setRolesCodigos(String rolesCodigos) {
        this.rolesCodigos = rolesCodigos;
    }

    public String getRolesNombres() {
        return rolesNombres;
    }

    public void setRolesNombres(String rolesNombres) {
        this.rolesNombres = rolesNombres;
    }
}
