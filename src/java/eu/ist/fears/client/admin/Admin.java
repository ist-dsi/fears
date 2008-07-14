package eu.ist.fears.client.admin;


import com.google.gwt.core.client.EntryPoint;
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


import eu.ist.fears.client.CreateFeatureWidget;
import eu.ist.fears.client.DisplayFeatureDetailedWidget;
import eu.ist.fears.client.Fears;
import eu.ist.fears.client.ListFeaturesWidget;
import eu.ist.fears.client.communication.Communication;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Admin extends Fears implements EntryPoint, HistoryListener  {


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		if (RootPanel.get("Admin")== null){
			return;			
		}
		
		_com = new Communication("service");
		RootPanel.get().setStyleName("body");

		main= new VerticalPanel();
		contentBox = new VerticalPanel();
		
		menu = new VerticalPanel();

		RootPanel.get().add(main);
		main.setStyleName("centered");
		
		main.add(contentBox);
		main.add(menu);

		userName = new Label("guest");
		RootPanel.get("rUsername").clear();
		RootPanel.get("rUsername").add(userName);	
		

		History.addHistoryListener(this);
		
		onHistoryChanged(History.getToken());
	}	
	

	
	public void viewListProjects(){
		
		contentBox.clear();
		if(!verifyLogin(true))
			return;
		
		AdminListProjectsWidget projects = new AdminListProjectsWidget();
		//RootPanel.get().setTitle("Projectos");
		projects.update();	
		contentBox.add(projects);
		
	}


	public void onHistoryChanged(String historyToken) {
		// This method is called whenever the application's history changes. Set
		// the label to reflect the current history token.
		
		if (RootPanel.get("Admin")== null){
			return;			
		}
		
		hideAll();
		
		Fears.parseURL(historyToken, this);

	}

}
