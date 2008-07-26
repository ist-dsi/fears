package eu.ist.fears.server;


import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.client.rpc.SerializationException;
import eu.ist.fears.client.communication.FearsService;
import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewFeatureResume;
import eu.ist.fears.client.views.ViewProject;
import eu.ist.fears.client.views.ViewVoter;
import eu.ist.fears.server.domain.*;

import jvstm.TransactionalCommand;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;


public class FearsServiceImpl extends RemoteServiceServlet implements FearsService {

	/**
	 * 
	 */	
	private static final long serialVersionUID = -9186875057311859285L;
	private static final Boolean True = null;

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
                if (! txFinished) {
                    Transaction.abort();
                }
            }
        }
    }

	public ViewFeatureDetailed vote(String projectName, String name, String sessionID){
		Project p =FearsApp.getFears().getProject(projectName);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectName);

		FeatureRequest f= p.getFeature(name);

		if(f==null)
			throw new RuntimeException("Nao existe essa sugestao: " + name);

		f.vote(getVoterFromSession(sessionID));
		return  f.getDetailedView();
	}

	public void addFeature(String projectName, String name,
			String description, String sessionID){
		Project p = FearsApp.getFears().getProject(projectName);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectName);

		p.addFeature(new FeatureRequest(name, description, getVoterFromSession(sessionID)));
	}

	public List<ViewFeatureResume> getFeatures(String projectName, String sessionID){
		Project p =FearsApp.getFears().getProject(projectName);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectName);

		return FearsApp.getViewFeaturesResumes(p.getFeatures());
		
	}

	public ViewFeatureDetailed getFeature(String projectName,
			String name, String sessionID){
		Project p =FearsApp.getFears().getProject(projectName);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectName);

		if(p.getFeature(name)==null)
			throw new RuntimeException("Nao existe essa sugestao: " + name);

		return p.getFeature(name).getDetailedView();

	}

	public ViewFeatureDetailed addComment(String projectName,
			String featureName, String comment, String sessionID) {
		Project p =FearsApp.getFears().getProject(projectName);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectName);


		if(p.getFeature(featureName)==null)
			throw new RuntimeException("Nao existe essa sugestao: " + featureName);

		p.getFeature(featureName).addComment(comment, getVoterFromSession(sessionID));
		return p.getFeature(featureName).getDetailedView();
	}

	public void addProject(String name, String description, String sessionID) {
		FearsApp.getFears().addProject(new Project(name, description, getVoterFromSession(sessionID)), getVoterFromSession(sessionID));
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
		ViewVoter ret =  new ViewVoter(temp.getName(), temp.getViewFeaturesCreated(), session.getId());
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
	
	private Voter getVoterFromSession(String sessionID){
		HttpSession session = this.getThreadLocalRequest().getSession();
		ViewVoter temp = (ViewVoter)session.getAttribute("fears_voter");
		return FearsApp.getFears().getVoter(temp.getName());
	}
	


}
