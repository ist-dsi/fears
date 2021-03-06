package eu.ist.fears.common.communication;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import eu.ist.fears.common.State;
import eu.ist.fears.common.exceptions.FearsException;
import eu.ist.fears.common.exceptions.NoProjectException;
import eu.ist.fears.common.views.ViewAdmins;
import eu.ist.fears.common.views.ViewFeatureDetailed;
import eu.ist.fears.common.views.ViewFeatureResume;
import eu.ist.fears.common.views.ViewProject;
import eu.ist.fears.common.views.ViewVoterDetailed;
import eu.ist.fears.common.views.ViewVoterResume;

public interface FearsService extends RemoteService {

    public ViewFeatureDetailed vote(String projectName, String feature, String sessionID) throws FearsException,
	    NoProjectException;

    public ViewFeatureDetailed removeVote(String projectName, String feature, String sessionID) throws FearsException;

    public void addFeature(String projectName, String name, String description, String sessionID) throws FearsException;

    public ViewFeatureDetailed getFeature(String projectName, String featuremName, String sessionID) throws FearsException;

    public ViewFeatureDetailed addComment(String projectName, String featureName, String comment, State newState, String sessionID)
	    throws FearsException;

    public void addProject(String name, String description, int nvotes, String sessionID) throws FearsException;

    public void editProject(String projectID, String name, String description, int nvotes, String sessionID)
	    throws FearsException;

    public List<ViewProject> getProjects(String sessionID);

    public void deleteProject(String name, String sessionID) throws FearsException;

    public String getProjectName(String projectID) throws NoProjectException, FearsException;

    public ViewProject getProject(String projectID) throws NoProjectException, FearsException;

    public ViewVoterResume validateSessionID(String sessionID);

    // public ViewVoterResume login(String user, String password) throws
    // FearsException;
    public void logoff(String sessionID) throws FearsException;

    public ViewAdmins getAdmins(String sessionID) throws FearsException;

    public ViewAdmins addAdmin(String userName, String sessionID) throws FearsException;

    public ViewAdmins removeAdmin(String userName, String sessionID) throws FearsException;

    public List<ViewFeatureResume> search(String projectID, String search, int sort, int page, String filter, String sessionID)
	    throws FearsException, NoProjectException;

    public ViewAdmins getProjectAdmins(String projectID) throws FearsException;

    public ViewAdmins addProjectAdmin(String newAdmin, String projectID) throws FearsException;

    public ViewAdmins removeProjectAdmin(String oldAdmin, String projectID) throws FearsException;

    public List<ViewVoterDetailed> getVoter(String _projectid, String userOID, String cookie) throws FearsException;

    public ViewVoterResume getCurrentVoter(String _projectid, String cookie) throws FearsException;

    public ViewVoterResume CASlogin(String ticket, boolean admin, String cookie) throws FearsException;

    public List<ViewProject> projectUp(String projectId, String cookie) throws FearsException;

    public List<ViewProject> projectDown(String projectId, String cookie) throws FearsException;

    public Boolean userCreatedFeature(String cookie) throws FearsException;

}
