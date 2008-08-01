package eu.ist.fears.client.views;

import java.io.Serializable;
import java.util.List;

public class ViewAdmins implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3812674061992808340L;
	
	/**
	   * This field is a List that must always contain String.
	   * 
	   * @gwt.typeArgs <java.lang.String>
	   */
	protected List _admins;
	
	public ViewAdmins(){}
	
	public ViewAdmins(List admins){
		_admins=admins;
	}
	
	public List getAdmins(){
		return _admins;
	}

}
