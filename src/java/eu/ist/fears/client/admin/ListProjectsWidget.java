package eu.ist.fears.client.admin;

import java.util.List;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;

import eu.ist.fears.client.Fears;
import eu.ist.fears.client.common.communication.Communication;
import eu.ist.fears.client.common.exceptions.ExceptionsTreatment;
import eu.ist.fears.client.common.views.ViewProject;
import eu.ist.fears.client.interfaceweb.ProjectWidget;

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
	}

	private void displayCreateProject(){
		
		Hyperlink createProjectButton  = new Hyperlink("Criar Projecto","createProject");
		_projPanel.add(createProjectButton);
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
			_projPanel.add(new ProjectWidgetAdmin(projects.get(i)));
		}
		
		displayCreateProject();

	}
	
	public class ProjectWidgetAdmin extends ProjectWidget{

		Button _removeButton;
		Label _author;
		DisclosurePanel _removePanel;
		String _id;

		
		ProjectWidgetAdmin(ViewProject p){
			super(p);
			_admin = new HorizontalPanel();
			_project.add(_admin);
			_admin.setStyleName("removeMeta");
			_author = new Label(p.getAuthorNick());
			_removeButton = new Button("Sim");
			_removeButton.addClickListener(new RemoveButton());
			_id=new String(new Integer(p.getwebID()).toString());
			
			VerticalPanel changeOrder = new VerticalPanel();
			changeOrder.setStyleName("changeOrderButtons");
			
			PushButton up = new PushButton(new Image("up.png",0,0,10,10));
			up.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					_com.projectUp(_id, Cookies.getCookie("fears"),updateCB);
				}
			});
			up.setStyleName("orderButton");
			PushButton down = new PushButton(new Image("down.png",0,0,10,10));
			down.addClickListener(new ClickListener(){
				public void onClick(Widget sender) {
					_com.projectDown(_id, Cookies.getCookie("fears"),updateCB);
				}
			});
			down.setStyleName("orderButton");
			
			changeOrder.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
			changeOrder.add(up);
			changeOrder.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
			changeOrder.add(down);
			_admin.add(new HTML("Ordem: " + new Integer(p.getListOrder()).toString() + "&nbsp;&nbsp;"));
			_admin.add(changeOrder);
			_admin.add(new HTML("&nbsp;|&nbsp;"));
			_admin.add(_author);
			_admin.add(new HTML("&nbsp;|&nbsp;"));
			_admin.add(new Hyperlink("Editar Projecto","Project"+p.getwebID()+"&"+"edit"));
			_admin.add(new HTML("&nbsp;|&nbsp;"));
			_admin.add(new Hyperlink("Editar Administradores","Project"+p.getwebID()+"&"+"adminEdit"));
			_admin.add(new HTML("&nbsp;|&nbsp;"));
			_removePanel = new DisclosurePanel();
			_removePanel.setHeader(new HTML("Remover"));
			
			HorizontalPanel removeExpanded = new HorizontalPanel();
			removeExpanded.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
			removeExpanded.setStyleName("removeConfirm");
			if(p.getNFeatures()==0){
				removeExpanded.add(new Label("Deseja mesmo apagar este projecto?"));
			removeExpanded.add(_removeButton);
			Button _noButton = new Button("N&atilde;o");
			_noButton.addClickListener(new CloseButton());
			removeExpanded.add(_noButton);
			}else {
				removeExpanded.add(new HTML("N&atilde;o pode apagar projectos com sugest&otilde;es."));
			}
			
			_removePanel.setContent(removeExpanded);
			_admin.add(_removePanel);
			
			
		}


		private class RemoveButton implements ClickListener{

			public void onClick(Widget sender) {
				_com.deleteProject(_id, Cookies.getCookie("fears"), updateCB);
			}

		}
		
		private class CloseButton implements ClickListener{
			public void onClick(Widget sender) {
				_removePanel.setOpen(false);
			}

		}

	}
	
	AsyncCallback updateCB = new ExceptionsTreatment() {
		public void onSuccess(Object result){ 
			update();	
		}		
	};
	
	AsyncCallback getProjectsCB = new ExceptionsTreatment() {
		public void onSuccess(Object result){ 
			updateProjects((List<ViewProject>) result);
		}		
	};

}
