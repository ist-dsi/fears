package eu.ist.fears.client.views;

import java.io.Serializable;
import java.util.List;

public class ViewAdmins implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3812674061992808340L;
	
	
	protected List<java.lang.String> _admins;
	
	public ViewAdmins(){}
	
	public ViewAdmins(List<java.lang.String> admins){
		_admins=admins;
	}
	
	public List<java.lang.String> getAdmins(){
		return _admins;
	}

}
