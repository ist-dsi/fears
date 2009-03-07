package eu.ist.fears.client.common.views;

import java.io.Serializable;
import java.util.List;

public class ViewAdmins implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3812674061992808340L;
	
	
	protected List<java.lang.String> _admins;
	protected String _projectID;
	protected String _projectName;
	
	public ViewAdmins(){}
	
	public ViewAdmins(List<java.lang.String> admins, String projectId, String projectName){
		_admins=admins;
		_projectID=projectId;
		_projectName=projectName;
	}
	
	public ViewAdmins(List<java.lang.String> admins){
		_admins=admins;
	}
	
	public List<java.lang.String> getAdmins(){
		return _admins;
	}
	
	public String getProjectId(){
		return _projectID;
	}
	
	public String getProjectName(){
		return _projectName;
	}

}
