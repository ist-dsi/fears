package eu.ist.fears.client;


import java.util.Date;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import eu.ist.fears.client.admin.*;
import eu.ist.fears.client.communication.Communication;
import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewVoter;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Fears implements EntryPoint, HistoryListener  {



	protected DockPanel main;
	protected VerticalPanel contentBox; 
	protected VerticalPanel menu;
	protected HorizontalPanel topBox; 
	protected Admin admin;
	protected Communication _com;
	protected Label userName;
	protected boolean validCookie;


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		if (RootPanel.get("Admin") != null){
			return;			
		}


		_com = new Communication("service");
		RootPanel.get().setStyleName("body");

		main= new DockPanel();
		contentBox = new VerticalPanel();
		VerticalPanel menuBox = new VerticalPanel();
		topBox = new HorizontalPanel();

		menu = new VerticalPanel();

		RootPanel.get().add(main);
		main.add(topBox, DockPanel.NORTH);
		main.add(contentBox, DockPanel.CENTER);
		main.add(menuBox,DockPanel.WEST);

		main.setStyleName("main");
		topBox.setStyleName("top");
		contentBox.setStyleName("content");
		menuBox.setStyleName("menuBox");

		topBox.add(new Label("Username: "));
		userName = new Label("...");
		topBox.add(userName);
		topBox.add(new Label("Nº de Votos Restante: ..."));

		menuBox.add(menu);
		menu.setStyleName("menu");
		updateMenu("");

		History.addHistoryListener(this);

		onHistoryChanged(History.getToken());

	}	


	protected void updateUsername(String user){
		userName.setText(user);
	}

	public void updateMenu(String project){
		menu.clear();
		if(project==""){
			menu.add(new Hyperlink("Ver Lista de Projectos", "listProjects"));
		}else{
			menu.add(new Hyperlink("Ver Lista de Projectos", "listProjects"));
			menu.add(new HTML("<br>"));
			menu.add(new HTML("<b>" + project + "</b>"));
			menu.add(new Hyperlink("     -  Ver Sugestoes","Project" + project + "?" + "listFeatures"));
			menu.add(new Hyperlink("     -  Adicionar Sugestao","Project" + project + "?" + "addFeature"));


		}
		menu.add(new HTML("<br>"));
		menu.add(new HTML("<br>"));
		menu.add(new HTML("<a href=\"Admin.html\">Administracao</a>"));
		if(!validCookie){
			menu.add(new Hyperlink("Login","login"));
		}
	}

	public void listFeatures(String projectName){
		contentBox.clear();

		verifyLogin(false);

		ListFeaturesWidget features = new ListFeaturesWidget(projectName);

		//RootPanel.get().setTitle(projectName);
		updateMenu(projectName);
		features.update();
		contentBox.add(features);
	}

	public void addFeature(String projectName){
		contentBox.clear();
		//RootPanel.get().setTitle("Adicionar Sugestao a "+  projectName);

		if(!verifyLogin(true))
			return;

		updateMenu(projectName);
		contentBox.add(new CreateFeatureWidget(projectName));

	}

	public void viewFeature(String projectName, String featureName){
		contentBox.clear();

		verifyLogin(false);


		//RootPanel.get().setTitle(featureName);
		updateMenu(projectName);
		contentBox.add(new DisplayFeatureDetailedWidget(projectName, featureName));
	}

	public void viewListProjects(){
		contentBox.clear();

		verifyLogin(false);

		ListProjectsWidget projects = new ListProjectsWidget();

		//RootPanel.get().setTitle("Projectos");
		updateMenu("");
		projects.update();	
		contentBox.add(projects);
	}

	public void viewLogin(){
		contentBox.clear();

		verifyLogin(false);

		LoginWidget login = new LoginWidget(this);
		updateMenu("");
		contentBox.add(login);		
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
