package eu.ist.fears.client.communication;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


public class Communication implements FearsServiceAsync{

	private  FearsServiceAsync _service=null;

	public Communication(String path){

		_service = (FearsServiceAsync) GWT.create(FearsService.class);

		ServiceDefTarget endpoint = (ServiceDefTarget) _service;
		String moduleRelativeURL = GWT.getModuleBaseURL() + path;
		endpoint.setServiceEntryPoint(moduleRelativeURL);
	}

	public  void addFeature(String projectName, String name, String description,
			 String sessionID, AsyncCallback callback) {
		_service.addFeature(projectName, name, description, sessionID, callback);
	}


	public  void getFeature(String projectName, String id,
			 String sessionID, AsyncCallback callback) {
		_service.getFeature(projectName, id, sessionID, callback);
	}

	public  void vote(String projectName, String id,
			 String sessionID, AsyncCallback callback) {
		_service.vote(projectName, id, sessionID, callback);
	}
	
	
	public  void removeVote(String projectName, String feature,
			 String sessionID, AsyncCallback callback) {
		_service.removeVote(projectName, feature, sessionID, callback);
	}

	public void addComment(String projectName, String featureID,
			String comment,String newState, String sessionID, AsyncCallback callback) {
		_service.addComment(projectName, featureID, comment, newState, sessionID, callback);
	}

	public void addProject(String name, String description,
			String sessionID, AsyncCallback callback) {
		_service.addProject(name, description, sessionID, callback);
	}

	public void getProjects(String sessionID, AsyncCallback callback) {
		_service.getProjects(sessionID, callback);
	}

	public void deleteProject(String name, String sessionID, AsyncCallback callback) {
		_service.deleteProject(name, sessionID , callback );
	}

	public void validateSessionID(String sessionID,
			AsyncCallback callback) {
		_service.validateSessionID(sessionID, callback);
	}

	public void login(String user, String password, AsyncCallback callback ) {
		_service.login(user,password,  callback);
	}
	
	public void logoff(String sessionID, AsyncCallback callback) {
		_service.logoff(sessionID, callback);
	}

	public void getAdmins(String sessionID, AsyncCallback callback){
		_service.getAdmins(sessionID, callback);
	}
	
	public void addAdmin(String userName, String sessionID, AsyncCallback callback){
		_service.addAdmin(userName, sessionID,  callback);
	}
	
	public void removeAdmin(String userName, String sessionID, AsyncCallback callback){
		_service.removeAdmin(userName, sessionID,  callback);
	}
	
	public void search(String projectID, String search, String sort, int page, String filter, String sessionID, AsyncCallback callback ){
		_service.search(projectID, search, sort, page, filter, sessionID, callback);
	}

	public void getProjectName(String projectID, AsyncCallback callback) {
		_service.getProjectName(projectID, callback);	
	}

	public void getVoter(String projectid, String voterName, String cookie,
			AsyncCallback callback){
		_service.getVoter(projectid, voterName, cookie, callback);
		
	}

	

}
