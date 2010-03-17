package eu.ist.fears.client;

import java.util.List;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.client.interfaceweb.ProjectWidget;
import eu.ist.fears.common.FearsAsyncCallback;
import eu.ist.fears.common.communication.Communication;
import eu.ist.fears.common.views.ViewProject;

public class ListProjects extends Composite {

    private Communication com;
    private VerticalPanel projPanel;

    public ListProjects() {

	com = new Communication("service");
	projPanel = new VerticalPanel();
	projPanel.setStyleName("projectsList");
	init();
	projPanel.add(new Label("A carregar lista de Projectos..."));
	initWidget(projPanel);
    }

    private void init() {
	projPanel.clear();
	Label projectsTitle = new Label("Projectos");
	projectsTitle.setStyleName("listProjectsTitle");
	projPanel.add(projectsTitle);
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
	    projPanel.add(new ProjectWidget(projects.get(i)));
	}

    }

    AsyncCallback<Object> getProjectsCB = new FearsAsyncCallback<Object>() {
	@SuppressWarnings("unchecked")
	public void onSuccess(Object result) {
	    updateProjects((List<ViewProject>) result);
	}
    };

    AsyncCallback<Object> updateCB = new FearsAsyncCallback<Object>() {
	public void onSuccess(Object result) {
	    update();
	}
    };

}
