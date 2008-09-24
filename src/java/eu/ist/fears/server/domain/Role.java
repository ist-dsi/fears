package eu.ist.fears.server.domain;

import pt.ist.dmapl.AccessControlRole;
import pt.ist.dmapl.RoleFinder;;

public class Role implements AccessControlRole {

	protected String _role;

	public Role(String r){
		_role=r;
	}

	public String getRole() {
		return _role;
	}

	public void setRole(String role) {
		this._role = role;
	}

	public class RoleFind implements RoleFinder {
		public AccessControlRole getRoleByName(String name){
			return new Role(name);
		}

	}

	public boolean equals(Role r){
		return _role==r.getRole();

	}

}


