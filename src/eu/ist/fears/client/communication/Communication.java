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

	public  void addFeature(String name, String description,
			AsyncCallback callback) {
		_service.addFeature(name, description, callback);
		
	}

	
	public  void getFeature(String name, AsyncCallback callback) {
		_service.getFeature(name, callback);
		
	}

	
	public  void getFeatures(AsyncCallback callback) {
		_service.getFeatures(callback);
		
	}

	
	public  void vote(String feature, AsyncCallback callback) {
		_service.vote(feature, callback);
		
	}

	public void addComment(String featureName, String comment, AsyncCallback callback) {
		_service.addComment(featureName, comment, callback);
		
	}
	
}
