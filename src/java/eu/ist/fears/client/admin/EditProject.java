package eu.ist.fears.client.admin;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import eu.ist.fears.client.Fears;
import eu.ist.fears.common.exceptions.ExceptionsTreatment;
import eu.ist.fears.client.interfaceweb.CreateEditProjectFeature;
import eu.ist.fears.common.views.ViewProject;


public class EditProject extends CreateEditProjectFeature {


	protected TextBox _name; 
	protected TextArea _description; 
	protected TextBox _nInitialVotes;
	protected HorizontalPanel _projectTitle;
	protected String _projectID;

	public EditProject(String projectID){
		super(3);
		_projectTitle = new HorizontalPanel();
		_projectID=projectID;

		_name = new TextBox();
		_name.setVisibleLength(50);
		_name.setStyleName("titleTextBox");
		_table.setWidget(0, 0,new HTML("T&iacute;tulo:"));
		_table.setWidget(0, 1,(_name));

		_description= new TextArea();
		_description.setStyleName("descriptionTextBox");
		_table.setWidget(1, 0, new HTML("Descri&ccedil;&atilde;o:&nbsp;"));
		_table.setWidget(1, 1, _description);

		_nInitialVotes = new TextBox();
		_nInitialVotes.setVisibleLength(4);
		_nInitialVotes.setStyleName("titleTextBox");
		_table.setWidget(2, 0,new HTML("Votos:"));
		_table.setWidget(2, 1,(_nInitialVotes));

		PushButton _sendButton = new PushButton(new Image("button04.gif",0,0,105,32), new Image("button04.gif",-2,-2,105,32) );
		_sendButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				_errorPanel.clear();

				boolean error = false;

				if(_name.getText().length() == 0){	
					_errorPanel.add(new HTML("Tem de preencher o nome do projecto."));
					error=true;
				}
				try{
					if(_nInitialVotes.getText().length() == 0 || new Integer(_nInitialVotes.getText()) < 0 ){
						_errorPanel.add(new HTML("O numero de votos do projecto tem que ser maior ou igual a zero."));
						error=true;
					}
				}catch(NumberFormatException e){
					_errorPanel.add(new HTML("O numero de votos tem que ser um numero inteiro."));
					error=true;
				}
				if(_description.getText().length() == 0){
					_errorPanel.add(new HTML("Tem de preencher a descri&ccedil;&atilde;o do projecto."));		
					error=true;
				}

				if(error)
					return;
			
				_com.editProject(_projectID, _name.getText(), _description.getText(), 
						new Integer(_nInitialVotes.getText()).intValue() ,Cookies.getCookie("fears"), addProjectCB);

		}
	});

		PushButton _cancelButton = new PushButton(new Image("button05.gif",0,0,92,32),new Image("button05.gif",-2,-2,92,32));
		_cancelButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				History.newItem("");
			}
		});
		_sendButton.setStyleName("pright5");

		_buttons.add(_sendButton);
		_buttons.add(_cancelButton);

		updateProject();
}
	
	public void updateProject(){
		_com.getProject(_projectID, getProject);
	}

	protected void updateProject(ViewProject p){
		_name.setText(p.getName());
		_description.setText(p.getDescription());
		_nInitialVotes.setText(new Integer(p.getInitialVotes()).toString());
		Fears.getPath().setEditProject(p.getName(), _projectID);
	}
	
	AsyncCallback getProject = new ExceptionsTreatment() {
		public void onSuccess(Object result){ 
			updateProject((ViewProject)result);
		}

	};
	
	AsyncCallback addProjectCB = new ExceptionsTreatment() {
		public void onSuccess(Object result){ 
			// do some UI stuff to show success
			History.newItem("");	
		}
	};

}









