package eu.ist.fears.client.common.views;

import java.io.Serializable;
import java.util.List;

public class ViewAdmins implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3812674061992808340L;
	
	
	protected List<java.lang.String> _admins;
	protected List<java.lang.String> _adminsNicks;
	protected String _projectID;
	protected String _projectName;
	
	public ViewAdmins(){}
	
	public ViewAdmins(List<java.lang.String> admins, List<java.lang.String> adminsNick, String projectId, String projectName){
		_admins=admins;
		_adminsNicks=adminsNick;
		_projectID=projectId;
		_projectName=projectName;
	}
	
	public ViewAdmins(List<java.lang.String> admins, List<java.lang.String> adminsNick){
		_admins=admins;
		_adminsNicks=adminsNick;
	}
	
	public List<java.lang.String> getAdmins(){
		return _admins;
	}
	
	public List<java.lang.String> getAdminsNick(){
		return _adminsNicks;
	}
	
	public String getProjectId(){
		return _projectID;
	}
	
	public String getProjectName(){
		return _projectName;
	}

}
