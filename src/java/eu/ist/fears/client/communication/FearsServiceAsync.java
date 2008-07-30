package eu.ist.fears.client.communication;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FearsServiceAsync {
	
	public void vote(String projectName, String featureName, String sessionID, AsyncCallback callback);
	public void removeVote(String projectName, String featureName, String sessionID, AsyncCallback callback);
	public void addFeature(String projectName, String featureName, String description, String sessionID,  AsyncCallback callback);
	public void getFeatures(String projectName, String sessionID, AsyncCallback callback);
	public void getFeature(String projectName, String featureName, String sessionID, AsyncCallback callback);
	public void addComment(String projectName, String featureName, String comment, String sessionID, AsyncCallback callback);
	
	public void addProject(String name, String description, String sessionID,  AsyncCallback callback);
	public void getProjects(String sessionID, AsyncCallback callback);
	public void deleteProject(String name, String sessionID, AsyncCallback callback);
	
	public void validateSessionID(String sessionID, AsyncCallback callback);
	public void login(String user, String password, AsyncCallback callback);
		
}
