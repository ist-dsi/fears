package eu.ist.fears.client.common.communication;

import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.ist.fears.client.common.State;
import eu.ist.fears.client.common.exceptions.FearsException;
import eu.ist.fears.client.common.views.ViewVoterResume;


public interface FearsServiceAsync {
	
	public void vote(String projectName, String featureName, String sessionID, AsyncCallback callback);
	public void removeVote(String projectName, String featureName, String sessionID, AsyncCallback callback);
	public void addFeature(String projectName, String featureName, String description, String sessionID,  AsyncCallback callback);
	public void getFeature(String projectName, String featureName, String sessionID, AsyncCallback callback);
	public void addComment(String projectName, String featureName, String comment, State newState, String sessionID, AsyncCallback callback);
	
	public void addProject(String name, String description, int nvotes, String sessionID,  AsyncCallback callback);
	public void editProject(String  projectID, String name, String description, int nvotes, String sessionID,  AsyncCallback callback);
	public void getProjects(String sessionID, AsyncCallback callback);
	public void deleteProject(String name, String sessionID, AsyncCallback callback);
	public void getProjectName(String projectID, AsyncCallback callback);
	public void getProject(String projectID, AsyncCallback callback);
	
	public void validateSessionID(String sessionID, AsyncCallback callback);
	//public void login(String user, String password, AsyncCallback callback);
	public void logoff(String sessionID, AsyncCallback callback);
	
	public void getAdmins(String sessionID, AsyncCallback callback);
	public void addAdmin(String userName, String sessionID, AsyncCallback callback);
	public void removeAdmin(String userName, String sessionID, AsyncCallback callback);
		
	public void search(String projectID, String search, String sort, int page, String filter, String sessionID, AsyncCallback callback );
	public void getVoter(String _projectid, String voterName, String cookie,
			AsyncCallback callback);
	
	public void getCurrentVoter(String _projectid, String cookie,
			AsyncCallback callback);
		
	public void CASlogin(String ticket, String cookie, AsyncCallback callback);
	
}
