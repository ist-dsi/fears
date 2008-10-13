package eu.ist.fears.client;


import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.client.admin.Admin;
import eu.ist.fears.client.common.communication.Communication;
import eu.ist.fears.client.common.views.ViewVoterResume;
import eu.ist.fears.client.interfaceweb.Header;
import eu.ist.fears.client.interfaceweb.Path;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Fears implements EntryPoint, HistoryListener  {


	protected static Communication _com;
	protected VerticalPanel frameBox;
	protected VerticalPanel frame;
	protected VerticalPanel content;
	protected static Header header; 
	protected static DialogBox  popup;
	protected static Path path;
	protected static ViewVoterResume _curretUser;
	public static boolean validCookie;


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		if (RootPanel.get("Admin") != null){
			return;			
		}

		init();

		History.addHistoryListener(this);
		History.fireCurrentHistoryState();
	}	

	public void init(){
		_com = new Communication("service");

		frameBox =  new VerticalPanel();
		frame = new VerticalPanel();
		content = new VerticalPanel();
		content.setStyleName("width100");
		path = new Path();
		_curretUser=new ViewVoterResume("guest","");
		header = new Header("guest",validCookie, false);
		RootPanel.get().setStyleName("centered");
		RootPanel.get().add(header);
		frameBox.setStyleName("frameBox");
		frame.setStyleName("frame");
		RootPanel.get().add(frameBox); 
		frameBox.add(frame);
		frame.add(path);
		frame.add(content);

	}

	public static boolean isAdminPage(){
			return RootPanel.get("Admin")!= null;
	}

	
	public static Path getPath(){
		return path;
	}
	
	public static Header getHeader(){
		return header;
	}
	
	public static void setCurrentUser(ViewVoterResume v){
		_curretUser=v;
	}
	
	public static boolean isLogedIn(){
		return validCookie;
	}

	public static String getUsername(){
		return _curretUser.getName();
	}
	
	public static int getVotesLeft(){
		return _curretUser.getVotesLeft();
	}

	public void listFeatures(String projectName,String filter){
		content.clear();

		verifyLogin(false);

		ListFeatures features = new ListFeatures(projectName, filter);

		features.update();
		content.add(features);
	}

	public void addFeature(String projectName){
		content.clear();

		if(!verifyLogin(true)){
		content.add(new HTML("Por favor fa&ccedil;a login para continuar"));
		return;
		}

		content.add(new CreateFeature(projectName));

	}

	public void viewFeature(String projectName, String featureID){
		content.clear();

		verifyLogin(false);

		content.add(new DisplayFeatureDetailed(projectName, featureID));
	}
	
	public void viewVoter(String projectID, String voterName){
		content.clear();
		verifyLogin(false);
		content.add(new DisplayVoter(projectID, voterName));
	}

	public void viewListProjects(){
		content.clear();

		verifyLogin(false);

		ListProjects projects = new ListProjects();

		projects.update();	
		content.add(projects);
	}

	public void viewLogin(){
		//content.clear();

		verifyLogin(false);

	    popup =  new DialogBox(false,false);
	    VerticalPanel dialogContents = new VerticalPanel();
	    dialogContents.setSpacing(0);
	    popup.setWidget(dialogContents);
	    
		Login login = new Login(this);
		dialogContents.add(login);
		dialogContents.add(new HTML("<iframe src=\"https://login.ist.utl.pt\" width=\"507px\" height=\"450px\"> <\\iframe>"));
		login.setStyleName("loginWindow");
		popup.setSize("400px", "400px");
		popup.setPopupPosition(500, 50);
		popup.show();
	}
	
	public void viewLogout(){
		
		validCookie=false;
		header.update(false, isAdminPage());
		_com.logoff(Cookies.getCookie("fears"), null);
		_curretUser.setName("guest");
		Cookies.removeCookie("fears");
		History.back();
		
	}
	
	
	

	public void setCookie(String value, String userName){
		final long DURATION = 1000 * 60 * 60 * 1; //duration remembering login, 1 hour
		Date expires = new Date(System.currentTimeMillis() + DURATION);
		Cookies.setCookie("fears", value, expires, null, "/", false);
		_curretUser.setName(userName);	
		validCookie=true;
	}


	protected boolean verifyLogin(boolean tryToLogin){

		if(validCookie){
			return true;
		}	

		String sessionID = Cookies.getCookie("fears");
		if(sessionID == null){
			if(tryToLogin)
				viewLogin();
			return false;
		}

		_com.validateSessionID(sessionID, new ValidateSession(this, tryToLogin));
		return false;

	}

	
	
	public void onHistoryChanged(String historyToken) {
		if (RootPanel.get("Admin") != null){
			return;			
		}

		header.update(false,false);
		parseURL(historyToken, this);
	}


	public static void parseURL(String url, Fears f){
		// This method is called whenever the application's history changes. Set
		// the label to reflect the current history token.

		if(url.length()==0){
			f.viewListProjects();
		}

		if(url.startsWith("listProjects")){
			f.viewListProjects();
		}else if(url.startsWith("login")){
			f.viewLogin();
		}else if(url.startsWith("Project")){
			projectParse(url.substring("Project".length()), f);	
		}if(url.startsWith("admins")){
			if(f instanceof Admin)
			((Admin)f).viewChangeAdmins();
		}else if(url.startsWith("logout")){
			f.viewLogout();
		}

	}

	private static void projectParse(String string, Fears f){
		int parseAt =  string.indexOf('?');
		int parseB =  string.indexOf("%3F");
		String projectID;
		String parse;

		//Estamos no Caso: #ProjectXPTO  
		if(parseAt==-1 && parseB==-1 ){
			projectID=string;
			/* getCurrentUser, to update Votes*/ 
			header.update(projectID);
			f.listFeatures(projectID,"");
			return;	
		}

		
		if(parseAt!=-1){
			projectID = string.substring(0,parseAt);
			parse = string.substring(parseAt+1);

		}else{
			projectID = string.substring(0,parseB);
			parse = string.substring(parseB+3);
		}

		/* getCurrentUser, to update Votes*/ 
		header.update(projectID);
		
		if("listFeatures".equals(parse)){
			f.listFeatures(projectID,"");	
		}else if("addFeature".equals(parse)){
			f.addFeature(projectID);
		}else if(parse.startsWith("viewFeature")){
			f.viewFeature(projectID, parse.substring("viewFeature".length()));
		}else if(parse.startsWith("viewUser")){
			f.viewVoter(projectID, parse.substring("viewUser".length()));
		}
		else if(parse.startsWith("filter")){
			f.listFeatures(projectID, parse.substring("filter".length()));
		}

	}

	protected class ValidateSession implements AsyncCallback{
		Fears _f;
		boolean _trytoLogin;

		public ValidateSession(Fears f, boolean trytoLogin){
			_f=f;
			_trytoLogin=trytoLogin;
		}

		public void onSuccess(Object result){
			ViewVoterResume voter = (ViewVoterResume) result;
			validCookie= true;
			setCurrentUser(voter); //TODO: Acrescentado recentemente, ver efeitos...
			_f.onHistoryChanged(History.getToken());
		}

		public void onFailure(Throwable caught) {
			try {
				throw caught;
			} catch (Throwable e) {
				//RootPanel.get().add(new Label("A sessao nao e valida."));
			}
			if(_trytoLogin)
				viewLogin();

		}
	};
	

}
