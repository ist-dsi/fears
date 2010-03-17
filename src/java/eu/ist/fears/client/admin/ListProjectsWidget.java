package eu.ist.fears.client.admin;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.client.interfaceweb.ProjectWidget;
import eu.ist.fears.common.FearsAsyncCallback;
import eu.ist.fears.common.communication.Communication;
import eu.ist.fears.common.views.ViewProject;

public class ListProjectsWidget extends Composite {

    private Communication com;
    private VerticalPanel projPanel;

    public ListProjectsWidget() {

	com = new Communication("service");
	projPanel = new VerticalPanel();
	projPanel.setStyleName("projectsList");

	init();
	initWidget(projPanel);

    }

    private void init() {
	projPanel.clear();
    }

    private void displayCreateProject() {

	Hyperlink createProjectButton = new Hyperlink("Criar Projecto", "createProject");
	projPanel.add(createProjectButton);
    }

    public void update() {
	com.getProjects(Cookies.getCookie("fears"), getProjectsCB);
    }

    protected void updateProjects(List<ViewProject> projects) {

	init();

	if (projects == null || projects.size() == 0) {
	    projPanel.add(new Label("Nao ha Projectos"));
	}

	for (int i = 0; i < projects.size(); i++) {
	    projPanel.add(new ProjectWidgetAdmin(projects.get(i)));
	}

	displayCreateProject();

    }

    public class ProjectWidgetAdmin extends ProjectWidget {

	private Button removeButton;
	private Label author;
	private DisclosurePanel removePanel;
	private String id;

	ProjectWidgetAdmin(ViewProject p) {
	    super(p);
	    admin = new HorizontalPanel();
	    project.add(admin);
	    admin.setStyleName("removeMeta");
	    author = new Label(p.getAuthorNick());
	    removeButton = new Button("Sim");
	    removeButton.addClickHandler(new RemoveButton());
	    id = new String(new Integer(p.getwebID()).toString());

	    VerticalPanel changeOrder = new VerticalPanel();
	    changeOrder.setStyleName("changeOrderButtons");

	    PushButton up = new PushButton(new Image("up.png", 0, 0, 10, 10));
	    up.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent sender) {
		    com.projectUp(id, Cookies.getCookie("fears"), updateCB);
		}
	    });
	    up.setStyleName("orderButton");
	    PushButton down = new PushButton(new Image("down.png", 0, 0, 10, 10));
	    down.addClickHandler(new ClickHandler() {
		public void onClick(ClickEvent sender) {
		    com.projectDown(id, Cookies.getCookie("fears"), updateCB);
		}
	    });
	    down.setStyleName("orderButton");

	    changeOrder.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
	    changeOrder.add(up);
	    changeOrder.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
	    changeOrder.add(down);
	    admin.add(new HTML("Ordem: " + new Integer(p.getListOrder()).toString() + "&nbsp;&nbsp;"));
	    admin.add(changeOrder);
	    admin.add(new HTML("&nbsp;|&nbsp;"));
	    admin.add(author);
	    admin.add(new HTML("&nbsp;|&nbsp;"));
	    admin.add(new Hyperlink("Editar Projecto", "Project" + p.getwebID() + "&" + "edit"));
	    admin.add(new HTML("&nbsp;|&nbsp;"));
	    admin.add(new Hyperlink("Editar Administradores", "Project" + p.getwebID() + "&" + "adminEdit"));
	    admin.add(new HTML("&nbsp;|&nbsp;"));
	    removePanel = new DisclosurePanel();
	    removePanel.setHeader(new HTML("Remover"));

	    HorizontalPanel removeExpanded = new HorizontalPanel();
	    removeExpanded.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
	    removeExpanded.setStyleName("removeConfirm");
	    if (p.getNFeatures() == 0) {
		removeExpanded.add(new Label("Deseja mesmo apagar este projecto?"));
		removeExpanded.add(removeButton);
		Button noButton = new Button("N&atilde;o");
		noButton.addClickHandler(new CloseButton());
		removeExpanded.add(noButton);
	    } else {
		removeExpanded.add(new HTML("N&atilde;o pode apagar projectos com sugest&otilde;es."));
	    }

	    removePanel.setContent(removeExpanded);
	    admin.add(removePanel);

	}

	private class RemoveButton implements ClickHandler {

	    public void onClick(ClickEvent sender) {
		com.deleteProject(id, Cookies.getCookie("fears"), updateCB);
	    }

	}

	private class CloseButton implements ClickHandler {
	    public void onClick(ClickEvent sender) {
		removePanel.setOpen(false);
	    }

	}

    }

    AsyncCallback<Object> updateCB = new FearsAsyncCallback<Object>() {
	public void onSuccess(Object result) {
	    update();
	}
    };

    AsyncCallback<Object> getProjectsCB = new FearsAsyncCallback<Object>() {
	@SuppressWarnings("unchecked")
	public void onSuccess(Object result) {
	    updateProjects((List<ViewProject>) result);
	}
    };

}
