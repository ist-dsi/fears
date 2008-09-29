package eu.ist.fears.client.communication;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService; 

import eu.ist.fears.client.views.ViewAdmins;
import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewFeatureResume;
import eu.ist.fears.client.views.ViewProject;
import eu.ist.fears.client.views.ViewUserDetailed;
import eu.ist.fears.client.views.ViewUserResume;

public interface FearsService extends RemoteService  {
	
	public ViewFeatureDetailed vote(String projectName, String feature, String sessionID);
	public ViewFeatureDetailed removeVote(String projectName, String feature, String sessionID);
	public void addFeature(String projectName, String name, String description,  String sessionID);
	public ViewFeatureDetailed getFeature(String projectName, String featuremName, String sessionID);
	public ViewFeatureDetailed addComment(String projectName, String featureName, String comment, String newState, String sessionID);
	
	public void addProject(String name, String description, String sessionID);
	public ViewProject[] getProjects(String sessionID);
	public void deleteProject(String name, String sessionID);
	public String getProjectName(String projectID);
	
	public ViewUserResume validateSessionID(String sessionID);
	public ViewUserResume login(String user, String password);
	public void logoff(String sessionID);
	
	public ViewAdmins getAdmins(String sessionID);
	public ViewAdmins addAdmin(String userName, String sessionID);
	public ViewAdmins removeAdmin(String userName, String sessionID);
	
	public List<ViewFeatureResume> search(String projectID, String search, String sort, int page, String filter, String sessionID);
		
	public ViewUserDetailed getVoter(String _projectid, String voterName, String cookie);
	
}
