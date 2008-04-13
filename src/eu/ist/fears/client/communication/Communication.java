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
			AsyncCallback callback) {
		_service.addFeature(projectName, name, description, callback);

	}


	public  void getFeature(String projectName, String name, AsyncCallback callback) {
		_service.getFeature(projectName, name, callback);

	}


	public  void getFeatures(String projectName, AsyncCallback callback) {
		_service.getFeatures(projectName, callback);

	}


	public  void vote(String projectName, String feature, AsyncCallback callback) {
		_service.vote(projectName, feature, callback);

	}

	public void addComment(String projectName, String featureName, String comment, AsyncCallback callback) {
		_service.addComment(projectName, featureName, comment, callback);

	}

	public void addProject(String name, String description, AsyncCallback callback) {
		_service.addProject(name, description, callback);

	}

	public void getProjects(AsyncCallback callback) {
		_service.getProjects(callback);

	}

	public void deleteProject(String name, AsyncCallback callback) {
		_service.deleteProject(name,  callback);

	}


}
