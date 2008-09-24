package eu.ist.fears.client;

import java.util.List;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ist.fears.client.communication.Communication;


public class CreateFeature extends Composite {

	protected Communication _com;
	protected VerticalPanel _sugPanel;
	protected TextBox _name; 
	protected TextArea _description; 
	protected String _projectID;
	protected HorizontalPanel _projectTitle;

	public CreateFeature(String projectID){
		_com= new Communication("service");
		_sugPanel = new VerticalPanel();
		Grid createFeatureTable = new Grid(2, 2);
		_projectID = projectID;
		_projectTitle = new HorizontalPanel();
		
		initWidget(_sugPanel);
		createFeatureTable.getCellFormatter().setStyleName(0, 0, "CreateFeatureCell");
		createFeatureTable.getCellFormatter().setStyleName(0, 1, "CreateFeatureCell");
		createFeatureTable.getCellFormatter().setStyleName(1, 0, "CreateFeatureCell");
		createFeatureTable.getCellFormatter().setStyleName(1, 1, "CreateFeatureCell");
		
		Fears.getPath().setFeatureChange("", _projectID, "Nova Sugest&atilde;o");

		
		_sugPanel.add(createFeatureTable);
		
		_name = new TextBox();
		_name.setVisibleLength(50);
		_name.setStyleName("titleTextBox");
		createFeatureTable.setWidget(0, 0,new HTML("T&iacute;tulo:"));
		createFeatureTable.setWidget(0, 1,(_name));
		
	
		_description= new TextArea();
		_description.setStyleName("descriptionTextBox");
		createFeatureTable.setWidget(1, 0, new HTML("Descri&ccedil;&atilde;o:&nbsp;"));
		createFeatureTable.setWidget(1, 1, _description);
		
		HorizontalPanel buttons = new HorizontalPanel();
		buttons.setStyleName("createButtons");
		
		
		PushButton _sendButton = new PushButton(new Image("button04.gif",0,0,105,32), new Image("button04.gif",-2,-2,105,32) );
		_sendButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				_com.addFeature(_projectID, _name.getText(),
						_description.getText(),  Cookies.getCookie("fears"), addSugestaoCB);
				
			}
		});
		
		PushButton _cancelButton = new PushButton(new Image("button05.gif",0,0,92,32),new Image("button05.gif",-2,-2,92,32));
		_cancelButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				History.newItem("Project" + _projectID );
			}
		});
		_sendButton.setStyleName("pright5");
		
		_sugPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		buttons.add(_sendButton);
		buttons.add(_cancelButton);
		_sugPanel.add(buttons);
		
		updateProjectName();
	}

	public void updateProjectName(){
		_com.getProjectName(_projectID, getProjectName);
	}
	
	protected void updateProjectName(String name){
		Fears.getPath().setFeatureChange(name, _projectID,  "Nova Sugest&atilde;o");
	}
	
	
	AsyncCallback addSugestaoCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			// do some UI stuff to show success
			History.newItem("Project" + _projectID );
			
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Nao foi possivel contactar o servidor."));
		}
	};

	AsyncCallback getProjectName = new AsyncCallback() {
		public void onSuccess(Object result){ 
			updateProjectName((String)result);
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Nao foi possivel contactar o servidor."));
		}
	};
	
}









