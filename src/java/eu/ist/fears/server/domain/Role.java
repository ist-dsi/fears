package eu.ist.fears.server.domain;

public class Role {

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
	
}