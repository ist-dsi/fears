package eu.ist.fears.client;

import java.util.List;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.client.common.communication.Communication;
import eu.ist.fears.client.common.exceptions.ExceptionsTreatment;
import eu.ist.fears.client.common.views.ViewProject;
import eu.ist.fears.client.interfaceweb.ProjectWidget;

public class ListProjects extends Composite{

	private Communication _com;
	private VerticalPanel _projPanel;

	public ListProjects(){

		_com= new Communication("service");
		_projPanel = new VerticalPanel();
		_projPanel.setStyleName("projectsList");
		init();
		_projPanel.add(new Label("A carregar lista de Projectos..."));
		initWidget(_projPanel);
	}

	private void init(){
		_projPanel.clear();
		Label projectsTitle = new Label("Projectos");
		projectsTitle.setStyleName("listProjectsTitle");
		_projPanel.add(projectsTitle);
	}


	public void update(){
		_com.getProjects(Cookies.getCookie("fears"), getProjectsCB);	
	}

	protected void updateProjects(List<ViewProject> projects) {

		init();

		if(projects==null || projects.size() ==0){
			_projPanel.add(new Label("Nao ha Projectos"));
		}


		for(int i=0;i< projects.size();i++){
			_projPanel.add(new ProjectWidget(projects.get(i)));
		}

	}


	AsyncCallback getProjectsCB = new ExceptionsTreatment() {
		public void onSuccess(Object result){ 
			updateProjects((List<ViewProject>) result);
		}
	};

	AsyncCallback updateCB = new ExceptionsTreatment() {
		public void onSuccess(Object result){ 
			update();	
		}
	};

}
