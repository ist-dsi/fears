package eu.ist.fears.client;


import java.util.Date;

import com.google.gwt.core.client.EntryPoint;


import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ist.fears.client.admin.Admin;
import eu.ist.fears.client.communication.Communication;
import eu.ist.fears.client.interfaceweb.Header;
import eu.ist.fears.client.interfaceweb.Path;
import eu.ist.fears.client.views.ViewUser;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Fears implements EntryPoint, HistoryListener  {


	protected static Communication _com;
	protected VerticalPanel frameBox;
	protected VerticalPanel frame;
	protected VerticalPanel content;
	protected Header header; 
	protected static Path path;
	protected static Label userName;
	protected static boolean validCookie;


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
		userName = new Label("guest");
		header = new Header(userName.getText(),validCookie, false);
		RootPanel.get().setStyleName("centered");
		RootPanel.get().add(header);
		frameBox.setStyleName("frameBox");
		frame.setStyleName("frame");
		RootPanel.get().add(frameBox); 
		frameBox.add(frame);
		frame.add(path);
		frame.add(content);

	}


	protected void updateUsername(String user){
		userName.setText(user);
	}
	
	public static Path getPath(){
		return path;
	}

	public void listFeatures(String projectName){
		content.clear();

		verifyLogin(false);

		ListFeatures features = new ListFeatures(projectName);

		features.update();
		content.add(features);
	}

	public void addFeature(String projectName){
		content.clear();

		if(!verifyLogin(true))
			return;

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
		content.clear();

		verifyLogin(false);

		Login login = new Login(this);
		content.add(login);		
	}
	

	public static boolean isLogedIn(){
		return validCookie;
	}

	public static String getUsername(){
		return userName.getText();
	}

	public void setCookie(String value, String userName){
		final long DURATION = 1000 * 60 * 60 * 1; //duration remembering login, 1 hour
		Date expires = new Date(System.currentTimeMillis() + DURATION);
		Cookies.setCookie("fears", value, expires, null, "/", false);
		updateUsername(userName);		
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

		header.update(false);
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
		}

	}

	private static void projectParse(String string, Fears f){
		int parseAt =  string.indexOf('?');
		int parseB =  string.indexOf("%3F");
		String projectName;
		String parse;

		//Estamos no Caso: #ProjectXPTO  
		if(parseAt==-1 && parseB==-1 ){
			projectName=string;
			f.listFeatures(projectName);
			return;	
		}

		if(parseAt!=-1){
			projectName = string.substring(0,parseAt);
			parse = string.substring(parseAt+1);

		}else{
			projectName = string.substring(0,parseB);
			parse = string.substring(parseB+3);
		}

		if("listFeatures".equals(parse)){
			f.listFeatures(projectName);	
		}else if("addFeature".equals(parse)){
			f.addFeature(projectName);
		}else if(parse.startsWith("viewFeature")){
			f.viewFeature(projectName, parse.substring("viewFeature".length()));
		}else if(parse.startsWith("viewUser")){
			f.viewVoter(projectName, parse.substring("viewUser".length()));
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
			ViewUser voter = (ViewUser) result;
			_f.updateUsername(voter.getName());
			validCookie= true;
			_f.onHistoryChanged(History.getToken());
		}

		public void onFailure(Throwable caught) {
			try {
				throw caught;
			} catch (Throwable e) {
				RootPanel.get().add(new Label("Nao foi possivel contactar o servidor."));
			}
			if(_trytoLogin)
				viewLogin();

		}
	};

}
