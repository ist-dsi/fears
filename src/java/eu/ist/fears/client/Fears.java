package eu.ist.fears.client;


import java.util.Date;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import eu.ist.fears.client.admin.*;
import eu.ist.fears.client.communication.Communication;
import eu.ist.fears.client.views.ViewVoter;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Fears implements EntryPoint, HistoryListener  {



	protected VerticalPanel main;
	protected VerticalPanel contentBox; 
	protected VerticalPanel menu;
	protected HorizontalPanel topBox; 
	protected Admin admin;
	protected Communication _com;
	protected Label userName;
	protected static boolean validCookie;


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		if (RootPanel.get("Admin") != null){
			return;			
		}

		_com = new Communication("service");

		main= new VerticalPanel();
		contentBox = new VerticalPanel();
		menu = new VerticalPanel();
		userName = new Label("guest");
		RootPanel.get("rUsername").clear();
		RootPanel.get("rUsername").add(userName);
		
		

		RootPanel.get().add(main);
		main.setStyleName("centered");
		
		main.add(contentBox); 
		main.add(menu);

		
		History.addHistoryListener(this);

		onHistoryChanged(History.getToken());
		
	}	


	protected void updateUsername(String user){
		userName.setText(user);
	}
	

	public void listFeatures(String projectName){
		contentBox.clear();

		verifyLogin(false);

		ListFeaturesWidget features = new ListFeaturesWidget(projectName);

		features.update();
		contentBox.add(features);
	}

	public void addFeature(String projectName){
		contentBox.clear();

		if(!verifyLogin(true))
			return;

		contentBox.add(new CreateFeatureWidget(projectName));

	}

	public void viewFeature(String projectName, String featureName){
		contentBox.clear();

		verifyLogin(false);


		contentBox.add(new DisplayFeatureDetailedWidget(projectName, featureName));
	}

	public void viewListProjects(){
		contentBox.clear();

		verifyLogin(false);

		ListProjectsWidget projects = new ListProjectsWidget();

		projects.update();	
		contentBox.add(projects);
	}

	public void viewLogin(){
		contentBox.clear();

		verifyLogin(false);

		LoginWidget login = new LoginWidget(this);
		contentBox.add(login);		
	}

	public static boolean isLogedIn(){
		return validCookie;
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
		
		hideAll();
		updateSessionLink();
		
		parseURL(historyToken, this);
	}
	
	public void updateSessionLink(){
		RootPanel sessionLink = RootPanel.get("rSession");
		sessionLink.clear();
		if(!isLogedIn()){
			sessionLink.add(new Hyperlink("Login","login"));
		}else{
			sessionLink.add(new Hyperlink("Logout","logout"));
		}
	}

	public void hideAll(){
		RootPanel newSug = RootPanel.get("newSug");
		newSug.setStyleName("hidden");
		
		RootPanel featureTemplate = RootPanel.get("featureDisplay");
		featureTemplate.setStyleName("hidden");
		
		RootPanel featureListTemplate = RootPanel.get("ProjectlistFeatures");
		featureListTemplate.setStyleName("hidden");
		
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
			ViewVoter voter = (ViewVoter) result;
			_f.updateUsername(voter.getName());
			validCookie= true;
			_f.onHistoryChanged(History.getToken());
		}

		public void onFailure(Throwable caught) {
			try {
				throw caught;
			} catch (Throwable e) {

			}
			if(_trytoLogin)
				viewLogin();

		}
	};

}
