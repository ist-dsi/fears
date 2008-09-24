package eu.ist.fears.server;


import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.client.rpc.SerializationException;
import eu.ist.fears.client.communication.FearsService;
import eu.ist.fears.client.views.ViewAdmins;
import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewFeatureResume;
import eu.ist.fears.client.views.ViewProject;
import eu.ist.fears.client.views.ViewVoter;
import eu.ist.fears.server.domain.*;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;
import pt.ist.dmapl.*;
import pt.ist.dmapl.enforcement.AccessControlSession;

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
			AccessControlSession.beginAccessControl(getVoterFromSession("..."));
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

		f.vote(getVoterFromSession(sessionID));
		return  f.getDetailedView(getVoterFromSession(sessionID));
	}

	public void addFeature(String projectID, String name,
			String description, String sessionID){

		Project p = FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectID);

		p.addFeature(new FeatureRequest(name, description, getVoterFromSession(sessionID)));
	}

	

	public ViewFeatureDetailed getFeature(String projectID,
			String name, String sessionID){
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectID);

		if(p.getFeature(name)==null)
			throw new RuntimeException("Nao existe essa sugestao: " + name);

		return p.getFeature(name).getDetailedView(getVoterFromSession(sessionID));

	}

	public ViewFeatureDetailed addComment(String projectID,
			String featureName, String comment, String sessionID) {
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectID);


		if(p.getFeature(featureName)==null)
			throw new RuntimeException("Nao existe essa sugestao: " + featureName);

		p.getFeature(featureName).addComment(comment, getVoterFromSession(sessionID));
		return p.getFeature(featureName).getDetailedView(getVoterFromSession(sessionID));
	}

	public void addProject(String name, String description, String sessionID) {

		FearsApp.getFears().addProject(new Project(name, FearsApp.getFears().getProjectCount()+1 , description, getVoterFromSession(sessionID)), getVoterFromSession(sessionID));
	}

	public ViewProject[] getProjects(String sessionID) {
		return  FearsApp.getFears().getProjects();
	}

	public void deleteProject(String name, String sessionID){
		FearsApp.getFears().deleteProject(name);
	}

	public ViewVoter login(String username, String password ){
		HttpSession session = this.getThreadLocalRequest().getSession();

		//Fingir que esta tudo bem.

		Voter temp = FearsApp.getFears().getVoter(username);
		ViewVoter ret =  new ViewVoter(temp.getName(), temp.getViewFeaturesCreated(getVoterFromSession(session.getId())), session.getId());
		session.setAttribute("fears_voter", ret);
		return ret;

	}

	public ViewVoter validateSessionID(String sessionID) {
		HttpSession session = this.getThreadLocalRequest().getSession();
		ViewVoter temp = (ViewVoter)session.getAttribute("fears_voter");
		if(temp==null){
			throw new RuntimeException("Sessao invalida");
		}
		return temp;
	}

	public Voter getVoterFromSession(String sessionID){
		HttpSession session = this.getThreadLocalRequest().getSession();
		ViewVoter temp = (ViewVoter)session.getAttribute("fears_voter");
		if(temp==null)
			return null;
		return FearsApp.getFears().getVoter(temp.getName());
	}

	public ViewFeatureDetailed removeVote(String projectID, String feature,
			String sessionID) {
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectID);

		FeatureRequest f= p.getFeature(feature);

		if(f==null)
			throw new RuntimeException("Nao existe essa sugestao: " + feature);

		f.removeVote(getVoterFromSession(sessionID));
		return  f.getDetailedView(getVoterFromSession(sessionID));
	}

	public ViewAdmins getAdmins(String sessionID) {
		return FearsApp.getFears().getViewAdmins();
	}


	public ViewAdmins addAdmin(String userName, String sessionID) {
		FearsApp.getFears().addAdmin(FearsApp.getFears().getVoter(userName));
		return FearsApp.getFears().getViewAdmins();
	}


	public ViewAdmins removeAdmin(String userName, String sessionID) {
		FearsApp.getFears().removeAdmin(FearsApp.getFears().getVoter(userName));
		return FearsApp.getFears().getViewAdmins();
	}

	
	public List<ViewFeatureResume> search(String projectID, String search, String sort, int page, String sessionID){
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectID);

		return p.search(search, sort, page, getVoterFromSession(sessionID));
		
	}

	public String getProjectName(String projectID) {
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectID);
		
		return p.getName();
	}



}
