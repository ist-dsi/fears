package eu.ist.fears.client.communication;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FearsServiceAsync {
	
	public void vote(String projectName, String featureName,  AsyncCallback callback);
	public void addFeature(String projectName, String featureName, String description,  AsyncCallback callback);
	public void getFeatures(String projectName, AsyncCallback callback);
	public void getFeature(String projectName, String featureName, AsyncCallback callback);
	public void addComment(String projectName, String featureName, String comment, AsyncCallback callback);
	
	public void addProject(String name, String description,  AsyncCallback callback);
	public void getProjects(AsyncCallback callback);
	public void deleteProject(String name, AsyncCallback callback);
		
}
