package eu.ist.fears.client.admin;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.RootPanel;
import eu.ist.fears.client.Fears;



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

		init();


		History.addHistoryListener(this);

		History.fireCurrentHistoryState();
	}	



	public void viewListProjects(){

		content.clear();
		if(!verifyLogin(true))
			return;

		ListProjectsWidget projects = new ListProjectsWidget();
		projects.update();	
		content.add(projects);

	}
	
	public void viewChangeAdmins(){
		content.clear();
		if(!verifyLogin(true))
			return;
		
		ListAdmins admins = new ListAdmins();
		admins.update();
		content.add(admins);
		
	}


	public void onHistoryChanged(String historyToken) {
		// This method is called whenever the application's history changes. Set
		// the label to reflect the current history token.

		if (RootPanel.get("Admin")== null){
			return;			
		}

		header.update(true);
		Fears.parseURL(historyToken, this);

	}

}
