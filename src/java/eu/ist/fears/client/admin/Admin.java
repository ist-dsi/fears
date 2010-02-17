package eu.ist.fears.client.admin;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ist.fears.client.Fears;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Admin extends Fears implements EntryPoint, ValueChangeHandler<String> {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

	if (RootPanel.get("Admin") == null) {
	    return;
	}

	init();
	History.addValueChangeHandler(this);
	History.fireCurrentHistoryState();
    }

    public boolean verifyAdmin() {
	content.clear();
	if (!verifyLogin(false)) {
	    content.clear();
	    Fears.setError(new HTML("Esta p&aacute;gina &eacute; exclusiva para Administradores.<br>"
		    + "Fa&ccedil;a login para continuar."));
	    // content.add(new HTML(""))
	    Fears.getHeader().update(false, true);
	    return false;
	} else {
	    if (!curretUser.isAdmin()) {
		Fears.setError(new HTML("Esta p&aacute;gina &eacute; exclusiva para Administradores."));
		Fears.getHeader().update(false, true);
		return false;
	    }
	}
	return true;
    }

    public void viewListProjects() {
	if (!verifyAdmin())
	    return;

	content.clear();
	Fears.getPath().setAdminProjects();

	ListProjectsWidget projects = new ListProjectsWidget();
	projects.update();
	content.add(projects);

    }

    public void viewEditAdmins() {
	if (!verifyAdmin())
	    return;

	ListAdmins admins = new ListAdmins(null);
	content.add(admins);

    }

    public void viewCreateProject() {
	if (!verifyAdmin())
	    return;

	CreateProject c = new CreateProject();
	content.add(c);
    }

    public void viewEditProject(String projectID) {
	if (!verifyAdmin())
	    return;

	EditProject e = new EditProject(projectID);
	content.add(e);
    }

    public void viewEditAdmins(String projectID) {
	if (!verifyAdmin())
	    return;

	ListAdmins admins = new ListAdmins(projectID);
	content.add(admins);

    }


    public void onValueChange(ValueChangeEvent<String> event) {
	if (RootPanel.get("Admin") == null) {
	    return;
	}

	header.update(false, true);
	Fears.parseURL(event.getValue(), this);
    }

}
