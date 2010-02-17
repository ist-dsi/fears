package eu.ist.fears.server.domain;

public class Role {

    protected String role;

    public Role(String r) {
	role = r;
    }

    public String getRole() {
	return role;
    }

    public void setRole(String role) {
	this.role = role;
    }

    public class RoleFind {
	public Role getRoleByName(String name) {
	    return new Role(name);
	}

    }

    public boolean equals(Role role) {
	return this.role == role.getRole();

    }

}
