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


	public  void getFeature(String projectName, String name,
			 String sessionID, AsyncCallback callback) {
		_service.getFeature(projectName, name, sessionID, callback);

	}


	public  void getFeatures(String projectName, String sessionID, AsyncCallback callback) {
		_service.getFeatures(projectName, sessionID, callback);

	}


	public  void vote(String projectName, String feature,
			 String sessionID, AsyncCallback callback) {
		_service.vote(projectName, feature, sessionID, callback);

	}
	
	
	public  void removeVote(String projectName, String feature,
			 String sessionID, AsyncCallback callback) {
		_service.removeVote(projectName, feature, sessionID, callback);

	}

	public void addComment(String projectName, String featureName,
			String comment, String sessionID, AsyncCallback callback) {
		_service.addComment(projectName, featureName, comment, sessionID, callback);

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

	public void getAdmins(String sessionID, AsyncCallback callback){
		_service.getAdmins(sessionID, callback);
	}
	
	public void addAdmin(String userName, String sessionID, AsyncCallback callback){
		_service.addAdmin(userName, sessionID,  callback);
	}
	
	public void removeAdmin(String userName, String sessionID, AsyncCallback callback){
		_service.removeAdmin(userName, sessionID,  callback);
	}
	

}
