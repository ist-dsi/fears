package eu.ist.fears.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import eu.ist.fears.client.communication.Communication;
import eu.ist.fears.client.views.ViewProject;

public class ListProjectsWidget extends Composite{

	private Communication _com;
	private VerticalPanel _projPanel;

	public ListProjectsWidget(){

		_com= new Communication("service");
		_projPanel = new VerticalPanel();
		_projPanel.setStyleName("projectsList");

		init();
		initWidget(_projPanel);
	}

	private void init(){
		_projPanel.clear();
		_projPanel.add( new HTML("<h1>Projectos</h1>"));
	}

	private class ProjectWidget extends Composite{

		VerticalPanel _project; 
		VerticalPanel _projectContainer;
		Label _name;
		Label _description;
		Label _nFeatures;
		Label _alert;
		Label _author;
		HorizontalPanel _info;
		Button _removeButton;

		ProjectWidget(ViewProject p){
			_name = new Label(p.getName());
			_description= new Label(p.getDescription());
			_nFeatures = new Label(new Integer(p.getNFeatures()).toString());
			 _author= new Label(p.getAuthor());

			_alert = new Label();
			_info=new HorizontalPanel();

			_project = new VerticalPanel();
			_projectContainer = new VerticalPanel();

			_projectContainer.add(_project);
			_projectContainer.setStyleName("project");

			_project.add(new Hyperlink("<b>"+_name.getText() + "</b>", true, "Project" + _name.getText())); 
			_project.add(_description);
			_project.add(new HTML("<br>")); //Line Break
			_project.add(_info);
			_project.add(_alert);
			
			HorizontalPanel row = new HorizontalPanel();
			_info.add(row);
			row.add(new Label("Autor:  "));
			row.add(_author);
			row.add(new Label(" |  N de Feature Requests:  "));
			row.add(_nFeatures);			
			initWidget(_projectContainer);
		}

	}


	public void update(){
		_com.getProjects(Cookies.getCookie("fears"), getProjectsCB);	
	}

	protected void updateProjects(ViewProject[] projects) {
		
		init();

		if(projects==null || projects.length ==0){
			_projPanel.add(new Label("Nao ha Projectos"));
		}
		

		for(int i=0;i< projects.length;i++){
			_projPanel.add(new ProjectWidget(projects[i]));
			_projPanel.add(new HTML("<br>")); //Line Break
		}

	}


	AsyncCallback getProjectsCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			updateProjects((ViewProject[]) result);
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Não correu bem"));
		}
	};

	AsyncCallback updateCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			update();	
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"));
		}
	};

}
