package eu.ist.fears.server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import pt.ist.dmapl.enforcement.AccessControlSession;
import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.yale.its.tp.cas.client.ServiceTicketValidator;
import eu.ist.fears.client.common.FearsConfig;
import eu.ist.fears.client.common.State;
import eu.ist.fears.client.common.communication.FearsService;
import eu.ist.fears.client.common.exceptions.FearsException;
import eu.ist.fears.client.common.exceptions.NoFeatureException;
import eu.ist.fears.client.common.exceptions.NoProjectException;
import eu.ist.fears.client.common.exceptions.RequiredLogin;
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
import eu.ist.fears.server.domain.Voter;
import eu.ist.fears.server.json.JSONException;
import eu.ist.fears.server.json.JSONObject;

public class FearsServiceImpl extends RemoteServiceServlet implements FearsService {

	/**
	 * 
	 */	
	private static final long serialVersionUID = -9186875057311859285L;
	private static Hashtable<String, String> table;

	@Override
	public void init() throws ServletException {
		Config config = new Config() {{ 
			domainModelPath = "/fears.dml";
			dbAlias = "//localhost:3306/fears"; 
			dbUsername="root";
			dbPassword = "fears";
		}};
		FenixFramework.initialize(config);
		table = new Hashtable<String, String>();

	}

	public static String getNickName(String user){
		String nick = user;
		String response = "";

		if(table.get(user)==null){

			URL url;
			try {
				url = new URL("https://fenix.ist.utl.pt//external/NameResolution.do?method=resolve&id="+
						user+"&username=fenixRemoteRequests&password=");

				URLConnection conn = url.openConnection();
				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),Charset.forName("UTF-8")));
				String line;			
				while ((line = rd.readLine()) != null) {
					response = response + line + "\n";
				}
				rd.close();

				nick = (String)new JSONObject(response).get("nickName"); 
				System.out.println("Nick:"+ nick);
				table.put(user, nick);

			} catch (MalformedURLException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return nick;	
		}
		else return table.get(user);
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

	protected void isLoggedIn() throws FearsException{
		if(getUserFromSession()==null){
			throw new RequiredLogin();
		}

	}

	protected void isAdmin() throws FearsException{
		isLoggedIn();

		if(!FearsApp.getFears().isAdmin(getUserFromSession()))
			throw new RequiredLogin();

	}

	public ViewFeatureDetailed vote(String projectID, String name, String sessionID) throws FearsException{
		isLoggedIn();

		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw  new NoProjectException(projectID);

		FeatureRequest f= p.getFeature(name);

		if(f==null)
			throw new NoFeatureException(projectID, name);

		if(!f.getState().equals(State.Novo)){
			throw new FearsException("So pode votar em sugestoes com o Estado Novo.");
		}

		f.vote(getUserFromSession().getVoter(p));
		return  f.getDetailedView(getUserFromSession().getVoter(p));
	}

	public void addFeature(String projectID, String name,
			String description, String sessionID) throws FearsException{
		isLoggedIn();

		Project p = FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new NoProjectException(projectID);

		p.addFeature(new FeatureRequest(name, description, getUserFromSession().getVoter(p), p.getInitialVotes() ));
	}



	public ViewFeatureDetailed getFeature(String projectID,
			String name, String sessionID) throws FearsException{


		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new NoProjectException(projectID);

		if(p.getFeature(name)==null)
			throw new NoFeatureException(projectID, name);

		if(getUserFromSession()==null)
			return p.getFeature(name).getDetailedView(null);

		return p.getFeature(name).getDetailedView(getUserFromSession().getVoter(p));

	}

	public ViewFeatureDetailed addComment(String projectID,
			String featureName, String comment, State newState, String sessionID) throws FearsException {
		isLoggedIn();

		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new NoProjectException(projectID);


		if(p.getFeature(featureName)==null)
			throw new NoFeatureException(projectID, featureName);

		FeatureRequest feature =p.getFeature(featureName);

		feature.addComment(comment, getUserFromSession().getVoter(p), newState);

		if(newState!=null){
			if(!p.isProjectAdmin(getUserFromSession()))
				isAdmin();

			//See if the old state is "New", and the new state any other.
			//Add 1 vote to all voters.
			if(feature.getState().equals(State.Novo)
					&& !newState.equals(State.Novo)){
				for(Voter v: feature.getVoterSet()){
					v.setVotesUsed(v.getVotesUsed()-1);
				}
			}

			//See if the old state is other than "New", and the new state is "New".
			//Remove 1 vote to all voters that have votes left.
			if(!feature.getState().equals(State.Novo)
					&& newState.equals(State.Novo)){	
				for(Voter v: feature.getVoterSet()){
					if(v.getVotesUsed() < p.getInitialVotes())
						v.setVotesUsed(v.getVotesUsed()+1);
				}
			}

			feature.setState(newState);
		}

		return p.getFeature(featureName).getDetailedView(getUserFromSession().getVoter(p));
	}

	public void addProject(String name, String description, int nvotes, String sessionID) throws FearsException{
		isAdmin();

		FearsApp.getFears().addProject(new Project(name, description, nvotes, getUserFromSession()), getUserFromSession());
	}

	public void editProject(String projectID, String name, String description, int nvotes, String sessionID) throws FearsException{
		isAdmin();

		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new NoProjectException(projectID);

		p.edit(name, description, nvotes);
	}

	public ViewProject[] getProjects(String sessionID) {
		return  FearsApp.getFears().getProjects();
	}

	public void deleteProject(String name, String sessionID) throws FearsException{
		isAdmin();

		if(FearsApp.getFears().getProject(name).getFeatureRequestCount()>0)
			throw new FearsException("Projecto n&atilde;o pode ser remoido, porque tem sugest&otilde;oes");

		FearsApp.getFears().deleteProject(name);
	}

	/*public ViewVoterResume login(String username, String password) throws FearsException{
		HttpSession session = this.getThreadLocalRequest().getSession();

		//Fingir que esta tudo bem.

		User temp = FearsApp.getFears().getUser(username);
		ViewVoterResume ret =  new ViewVoterResume(temp.getName(),  session.getId(), FearsApp.getFears().isAdmin(temp));
		session.setAttribute("fears_voter", ret);
		return ret;

	} */

	public ViewVoterResume validateSessionID(String sessionID) {
		HttpSession session = this.getThreadLocalRequest().getSession();
		ViewVoterResume temp = (ViewVoterResume)session.getAttribute("fears_voter");

		return temp;
	}

	public User getUserFromSession(){
		HttpSession session = this.getThreadLocalRequest().getSession();
		ViewVoterResume temp = (ViewVoterResume)session.getAttribute("fears_voter");
		if(temp==null)
			return null;
		return FearsApp.getFears().getUser(temp.getName());
	}

	public ViewFeatureDetailed removeVote(String projectID, String feature,
			String sessionID) throws FearsException {
		isLoggedIn();

		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new NoProjectException(projectID);

		FeatureRequest f= p.getFeature(feature);

		if(f==null)
			throw new NoFeatureException(projectID, feature);

		if(!f.getState().equals(State.Novo)){
			throw new FearsException("So pode retirar o voto de sugestoes com o Estado Novo.");
		}

		f.removeVote(getUserFromSession().getVoter(p));
		return  f.getDetailedView(getUserFromSession().getVoter(p));
	}

	public ViewAdmins getAdmins(String sessionID) throws FearsException {
		isAdmin();

		return FearsApp.getFears().getViewAdmins();
	}


	public ViewAdmins addAdmin(String userName, String sessionID) throws FearsException {
		isAdmin();

		FearsApp.getFears().addAdmin(FearsApp.getFears().getUser(userName));
		return FearsApp.getFears().getViewAdmins();
	}


	public ViewAdmins removeAdmin(String userName, String sessionID) throws FearsException {
		isAdmin();

		if(userName.equals(getUserFromSession().getName()))
			throw new FearsException("Nao se pode remover a si proprio de Administrador.");

		FearsApp.getFears().removeAdmin(FearsApp.getFears().getUser(userName));
		return FearsApp.getFears().getViewAdmins();
	}


	public List<ViewFeatureResume> search(String projectID, String search,
			int sort, int page, String filter, String sessionID) throws FearsException{
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new NoProjectException(projectID);

		if(getUserFromSession()==null)
			return p.search(search, sort, page, filter, null);		


		return p.search(search, sort, page, filter, getUserFromSession().getVoter(p));

	}

	public String getProjectName(String projectID) throws FearsException {
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new NoProjectException(projectID);

		return p.getName();
	}

	public ViewProject getProject(String projectID) throws FearsException {
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new NoProjectException(projectID);

		return p.getView();
	}

	public ViewAdmins getProjectAdmins(String projectID)throws FearsException{
		Project p =FearsApp.getFears().getProject(projectID);


		if(p==null)
			throw new NoProjectException(projectID);
		return p.getViewAdmins();

	}

	public ViewAdmins addProjectAdmin(String newAdmin, String projectID)throws FearsException{
		isAdmin();

		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new NoProjectException(projectID);

		p.addAdmin(FearsApp.getFears().getUser(newAdmin));

		return p.getViewAdmins();
	}

	public ViewAdmins removeProjectAdmin(String oldAdmin, String projectID)throws FearsException{
		isAdmin();

		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new NoProjectException(projectID);

		if(oldAdmin.equals(getUserFromSession().getName()))
			throw new FearsException("Nao se pode remover a si proprio de Administrador.");

		p.removeAdmin(FearsApp.getFears().getUser(oldAdmin));

		return p.getViewAdmins();
	}

	public ViewVoterDetailed getVoter(String projectID, String voterName,
			String sessionID) throws FearsException {
		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new NoProjectException(projectID);


		return FearsApp.getFears().getUser(voterName).getVoter(p).getView(sessionID);
	}

	public void logoff(String sessionID) throws FearsException{
		isLoggedIn();

		HttpSession session = this.getThreadLocalRequest().getSession();
		session.invalidate();
	}


	public ViewVoterResume getCurrentVoter(String projectID, String sessionID) throws FearsException {

		Project p =FearsApp.getFears().getProject(projectID);

		if(p==null)
			throw new NoProjectException(projectID);

		User u = getUserFromSession();

		if(u==null)
			return null;

		return u.getVoter(p).getCurrentVoterView(sessionID);
	}


	public ViewVoterResume CASlogin(String ticket, boolean admin, String sessionID)
	throws FearsException {
		HttpSession session = this.getThreadLocalRequest().getSession();

		String username=validateTicket(ticket, admin, sessionID);
		if(username!=null){
			username = username.toLowerCase();
			User temp = FearsApp.getFears().getUser(username);
			ViewVoterResume ret =  new ViewVoterResume(temp.getName(), getNickName(temp.getName()),  session.getId(), FearsApp.getFears().isAdmin(temp));
			session.setAttribute("fears_voter", ret);
			return ret;
		}else System.out.println("ERRO NO CAS....");
		return null;

	}

	public String validateTicket(String ticket, boolean admin, String sessionID){
		HttpSession session = this.getThreadLocalRequest().getSession();

		String user = null;
		String errorCode = null;
		String errorMessage = null;

		ServiceTicketValidator cas = new ServiceTicketValidator();
		/* instantiate a new ServiceTicketValidator */


		/* set its parameters */
		cas.setCasValidateUrl( FearsConfig.getCasUrl() + "serviceValidate");
		if(admin)
			cas.setService(FearsConfig.getFearsUrl() + "Admin.html");
		else
			cas.setService(FearsConfig.getFearsUrl() + "Fears.html");

		cas.setServiceTicket(ticket);

		try {
			cas.validate();
		} catch (IOException e) {
			System.out.println("Validate error:"+ e);
			e.printStackTrace();
		} catch (SAXException e) {
			System.out.println("SAXException:"+ e);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfigurationException:"+ e);
			e.printStackTrace();
		}

		if(cas.isAuthenticationSuccesful()) {
			user = cas.getUser();
		} else {
			System.out.println("CAS ERROR\n");
			errorCode = cas.getErrorCode();
			errorMessage = cas.getErrorMessage();
			System.out.println(errorCode +errorMessage );
			/* handle the error */
		}	
		return user;
	}
	

}

