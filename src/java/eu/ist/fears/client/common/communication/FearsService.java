package eu.ist.fears.client.common.communication;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import eu.ist.fears.client.common.State;
import eu.ist.fears.client.common.exceptions.FearsException;
import eu.ist.fears.client.common.exceptions.NoProjectException;
import eu.ist.fears.client.common.views.ViewAdmins;
import eu.ist.fears.client.common.views.ViewFeatureDetailed;
import eu.ist.fears.client.common.views.ViewFeatureResume;
import eu.ist.fears.client.common.views.ViewProject;
import eu.ist.fears.client.common.views.ViewVoterDetailed;
import eu.ist.fears.client.common.views.ViewVoterResume;

public interface FearsService extends RemoteService  {
	
	public ViewFeatureDetailed vote(String projectName, String feature, String sessionID) throws FearsException, NoProjectException;
	public ViewFeatureDetailed removeVote(String projectName, String feature, String sessionID) throws FearsException;
	public void addFeature(String projectName, String name, String description,  String sessionID) throws FearsException;
	public ViewFeatureDetailed getFeature(String projectName, String featuremName, String sessionID) throws FearsException;
	public ViewFeatureDetailed addComment(String projectName, String featureName, String comment, State newState, String sessionID) throws FearsException;
	
	public void addProject(String name, String description, String sessionID);
	public ViewProject[] getProjects(String sessionID);
	public void deleteProject(String name, String sessionID);
	public String getProjectName(String projectID) throws NoProjectException, FearsException;
	
	public ViewVoterResume validateSessionID(String sessionID);
	public ViewVoterResume login(String user, String password);
	public void logoff(String sessionID);
	
	public ViewAdmins getAdmins(String sessionID);
	public ViewAdmins addAdmin(String userName, String sessionID);
	public ViewAdmins removeAdmin(String userName, String sessionID);
	
	public List<ViewFeatureResume> search(String projectID, String search, String sort, int page, String filter, String sessionID) throws FearsException, NoProjectException;
		
	public ViewVoterDetailed getVoter(String _projectid, String voterName, String cookie) throws FearsException;
	
	public ViewVoterResume getCurrentVoter(String _projectid, String cookie) throws FearsException;
	
}
