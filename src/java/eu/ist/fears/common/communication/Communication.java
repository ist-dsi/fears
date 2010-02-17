package eu.ist.fears.common.communication;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import eu.ist.fears.common.State;
import eu.ist.fears.common.views.ViewVoterDetailed;
@SuppressWarnings("unchecked")
public class Communication implements FearsServiceAsync {

    private FearsServiceAsync service = null;

    public Communication(String path) {

	service = (FearsServiceAsync) GWT.create(FearsService.class);

	ServiceDefTarget endpoint = (ServiceDefTarget) service;
	String moduleRelativeURL = GWT.getModuleBaseURL() + path;
	endpoint.setServiceEntryPoint(moduleRelativeURL);
    }

    public FearsServiceAsync getService() {
	return service;
    }

    public void setService(FearsServiceAsync service) {
	this.service = service;
    }

    public void addFeature(String projectName, String name, String description, String sessionID, AsyncCallback callback) {
	getService().addFeature(projectName, name, description, sessionID, callback);
    }

    public void getFeature(String projectName, String id, String sessionID, AsyncCallback callback) {
	getService().getFeature(projectName, id, sessionID, callback);
    }

    public void vote(String projectName, String id, String sessionID, AsyncCallback callback) {
	getService().vote(projectName, id, sessionID, callback);
    }

    public void removeVote(String projectName, String feature, String sessionID, AsyncCallback callback) {
	getService().removeVote(projectName, feature, sessionID, callback);
    }

    public void addComment(String projectName, String featureID, String comment, State newState, String sessionID,
	    AsyncCallback callback) {
	getService().addComment(projectName, featureID, comment, newState, sessionID, callback);
    }

    public void addProject(String name, String description, int nvotes, String sessionID, AsyncCallback callback) {
	getService().addProject(name, description, nvotes, sessionID, callback);
    }

    public void editProject(String projectID, String name, String description, int nvotes, String sessionID,
	    AsyncCallback callback) {
	getService().editProject(projectID, name, description, nvotes, sessionID, callback);
    }

    public void getProjects(String sessionID, AsyncCallback callback) {
	getService().getProjects(sessionID, callback);
    }

    public void deleteProject(String name, String sessionID, AsyncCallback callback) {
	getService().deleteProject(name, sessionID, callback);
    }

    public void validateSessionID(String sessionID, AsyncCallback callback) {
	getService().validateSessionID(sessionID, callback);
    }

    // public void login(String user, String password, AsyncCallback callback )
    // {
    // _getService().login(user,password, callback);
    // }

    public void logoff(String sessionID, AsyncCallback callback) {
	getService().logoff(sessionID, callback);
    }

    public void getAdmins(String sessionID, AsyncCallback callback) {
	getService().getAdmins(sessionID, callback);
    }

    public void addAdmin(String userName, String sessionID, AsyncCallback callback) {
	getService().addAdmin(userName, sessionID, callback);
    }

    public void removeAdmin(String userName, String sessionID, AsyncCallback callback) {
	getService().removeAdmin(userName, sessionID, callback);
    }

    public void search(String projectID, String search, int sort, int page, String filter, String sessionID,
	    AsyncCallback callback) {
	getService().search(projectID, search, sort, page, filter, sessionID, callback);
    }

    public void getProjectName(String projectID, AsyncCallback callback) {
	getService().getProjectName(projectID, callback);
    }

    public void getProject(String projectID, AsyncCallback callback) {
	getService().getProject(projectID, callback);
    }

    public void addProjectAdmin(String newAdmin, String projectID, AsyncCallback callback) {
	getService().addProjectAdmin(newAdmin, projectID, callback);
    }

    public void getProjectAdmins(String projectID, AsyncCallback callback) {
	getService().getProjectAdmins(projectID, callback);
    }

    public void removeProjectAdmin(String oldAdmin, String projectID, AsyncCallback callback) {
	getService().removeProjectAdmin(oldAdmin, projectID, callback);

    }

    public void getVoter(String projectid, String userOID, String cookie, AsyncCallback<List<ViewVoterDetailed>> callback) {
	getService().getVoter(projectid, userOID, cookie, callback);

    }

    public void getCurrentVoter(String projectid, String cookie, AsyncCallback callback) {
	getService().getCurrentVoter(projectid, cookie, callback);

    }

    public void CASlogin(String ticket, boolean login, String cookie, AsyncCallback callback) {
	getService().CASlogin(ticket, login, cookie, callback);
    }

    public void projectUp(String projectId, String cookie, AsyncCallback callback) {
	getService().projectUp(projectId, cookie, callback);
    }

    public void projectDown(String projectId, String cookie, AsyncCallback callback) {
	getService().projectDown(projectId, cookie, callback);
    }

    public void userCreatedFeature(String cookie, AsyncCallback callback) {
	getService().userCreatedFeature(cookie, callback);
    }

}
