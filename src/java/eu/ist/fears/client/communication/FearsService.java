package eu.ist.fears.client.communication;

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService; 
import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewProject;
import eu.ist.fears.client.views.ViewVoter;

public interface FearsService extends RemoteService  {
	
	public ViewFeatureDetailed vote(String projectName, String feature, String sessionID);
	public ViewFeatureDetailed removeVote(String projectName, String feature, String sessionID);
	public void addFeature(String projectName, String name, String description,  String sessionID);
	public List getFeatures(String projectName, String sessionID);
	public ViewFeatureDetailed getFeature(String projectName, String featuremName, String sessionID);
	public ViewFeatureDetailed addComment(String projectName, String featureName, String comment, String sessionID);
	
	public void addProject(String name, String description, String sessionID);
	public ViewProject[] getProjects(String sessionID);
	public void deleteProject(String name, String sessionID);
	
	public ViewVoter validateSessionID(String sessionID);
	public ViewVoter login(String user, String password);
		
}
