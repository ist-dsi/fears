package eu.ist.fears.server;


import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import pt.ist.dmapl.enforcement.AccessControlSession;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import eu.ist.fears.client.common.State;
import eu.ist.fears.client.common.communication.FearsService;
import eu.ist.fears.client.common.views.ViewAdmins;
import eu.ist.fears.client.common.views.ViewFeatureDetailed;
import eu.ist.fears.client.common.views.ViewFeatureResume;
import eu.ist.fears.client.common.views.ViewProject;
import eu.ist.fears.client.common.views.ViewVoterDetailed;
import eu.ist.fears.client.common.views.ViewVoterResume;
import eu.ist.fears.server.domain.FearsApp;
import eu.ist.fears.server.domain.FeatureRequest;
import eu.ist.fears.server.domain.Project;
import eu.ist.fears.server.domain.User;

public class FearsServiceImpl extends RemoteServiceServlet implements FearsService {

	/**
	 * 
	 */	
	private static final long serialVersionUID = -9186875057311859285L;

	@Override
	public void init() throws ServletException {
		Config config = new Config() {{ 
			domainModelPath = "/fears.dml";
			dbAlias = "//localhost:3306/fears"; 
			dbUsername = "root";
			dbPassword = "fears";
		}};
		FenixFramework.initialize(config);
	}

	@Override
	public String processCall(final String payload) throws SerializationException {
		// process the RPC call within a transaction
		while (true) {
			Transaction.begin();
			//AccessControlSession.beginAccessControl(getUserFromSession("..."));
			boolean txFinished = false;
			try {
				String result = super.processCall(payload);
				Transaction.commit();
				txFinished = true;
				return result;
			} catch (jvstm.CommitException ce) {
				Transaction.abort();
				txFinished = true;
			} finally {
				AccessControlSession.endAccessControl();
				if (! txFinished) {
					Transaction.abort();
				}
			}
		}
	}

	public ViewFeatureDetailed vote(String projectID, String name, String sessionID){

		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectID);

		FeatureRequest f= p.getFeature(name);

		if(f==null)
			throw new RuntimeException("Nao existe essa sugestao: " + name);

		f.vote(getUserFromSession(sessionID).getVoter(p));
		return  f.getDetailedView(getUserFromSession(sessionID).getVoter(p));
	}

	public void addFeature(String projectID, String name,
			String description, String sessionID){

		Project p = FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectID);

		p.addFeature(new FeatureRequest(name, description, getUserFromSession(sessionID).getVoter(p) ));
	}

	

	public ViewFeatureDetailed getFeature(String projectID,
			String name, String sessionID){
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectID);

		if(p.getFeature(name)==null)
			throw new RuntimeException("Nao existe essa sugestao: " + name);

		if(getUserFromSession(sessionID)==null)
			return p.getFeature(name).getDetailedView(null);
		
		return p.getFeature(name).getDetailedView(getUserFromSession(sessionID).getVoter(p));

	}

	public ViewFeatureDetailed addComment(String projectID,
			String featureName, String comment, State newState, String sessionID) {
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectID);


		if(p.getFeature(featureName)==null)
			throw new RuntimeException("Nao existe essa sugestao: " + featureName);
		
		if(newState!=null){
			p.getFeature(featureName).setState(newState);
		}

		p.getFeature(featureName).addComment(comment, getUserFromSession(sessionID).getVoter(p), newState);
		return p.getFeature(featureName).getDetailedView(getUserFromSession(sessionID).getVoter(p));
	}

	public void addProject(String name, String description, String sessionID) {

		FearsApp.getFears().addProject(new Project(name, description, getUserFromSession(sessionID)), getUserFromSession(sessionID));
	}

	public ViewProject[] getProjects(String sessionID) {
		return  FearsApp.getFears().getProjects();
	}

	public void deleteProject(String name, String sessionID){
		FearsApp.getFears().deleteProject(name);
	}

	public ViewVoterResume login(String username, String password ){
		HttpSession session = this.getThreadLocalRequest().getSession();

		//Fingir que esta tudo bem.

		User temp = FearsApp.getFears().getUser(username);
		ViewVoterResume ret =  new ViewVoterResume(temp.getName(),  session.getId());
		session.setAttribute("fears_voter", ret);
		return ret;

	}

	public ViewVoterResume validateSessionID(String sessionID) {
		HttpSession session = this.getThreadLocalRequest().getSession();
		ViewVoterResume temp = (ViewVoterResume)session.getAttribute("fears_voter");
		if(temp==null){
			throw new RuntimeException("Sessao invalida");
		}
		return temp;
	}

	public User getUserFromSession(String sessionID){
		HttpSession session = this.getThreadLocalRequest().getSession();
		ViewVoterResume temp = (ViewVoterResume)session.getAttribute("fears_voter");
		if(temp==null)
			return null;
		return FearsApp.getFears().getUser(temp.getName());
	}

	public ViewFeatureDetailed removeVote(String projectID, String feature,
			String sessionID) {
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectID);

		FeatureRequest f= p.getFeature(feature);

		if(f==null)
			throw new RuntimeException("Nao existe essa sugestao: " + feature);

		f.removeVote(getUserFromSession(sessionID).getVoter(p));
		return  f.getDetailedView(getUserFromSession(sessionID).getVoter(p));
	}

	public ViewAdmins getAdmins(String sessionID) {
		return FearsApp.getFears().getViewAdmins();
	}


	public ViewAdmins addAdmin(String userName, String sessionID) {
		FearsApp.getFears().addAdmin(FearsApp.getFears().getUser(userName));
		return FearsApp.getFears().getViewAdmins();
	}


	public ViewAdmins removeAdmin(String userName, String sessionID) {
		FearsApp.getFears().removeAdmin(FearsApp.getFears().getUser(userName));
		return FearsApp.getFears().getViewAdmins();
	}

	
	public List<ViewFeatureResume> search(String projectID, String search, String sort, int page, String filter, String sessionID){
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectID);
		
		if(getUserFromSession(sessionID)==null)
			return p.search(search, sort, page, filter, null);		
		

		return p.search(search, sort, page, filter, getUserFromSession(sessionID).getVoter(p));
		
	}

	public String getProjectName(String projectID) {
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectID);
		
		return p.getName();
	}


	public ViewVoterDetailed getVoter(String projectid, String voterName,
			String sessionID) {
		Project p =FearsApp.getFears().getProject(projectid);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectid);
		
				
		return FearsApp.getFears().getUser(voterName).getVoter(p).getView(sessionID);
	}

	public void logoff(String sessionID){
		HttpSession session = this.getThreadLocalRequest().getSession();
		session.invalidate();
	}

	
	public ViewVoterResume getCurrentVoter(String _projectid, String cookie) {
		Project p =FearsApp.getFears().getProject(_projectid);
		
		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + _projectid);
		
		User u = getUserFromSession(cookie);
		
		if(u==null)
			return null;
		
		return u.getVoter(p).getCurrentVoterView(cookie);
	}

}
