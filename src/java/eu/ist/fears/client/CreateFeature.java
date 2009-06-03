package eu.ist.fears.client;


import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


import eu.ist.fears.client.common.communication.Communication;
import eu.ist.fears.client.common.exceptions.ExceptionsTreatment;
import eu.ist.fears.client.interfaceweb.CreateEditProjectFeature;

public class CreateFeature extends CreateEditProjectFeature {


	protected TextBox _name; 
	protected TextArea _description; 
	protected String _projectID;
	protected HorizontalPanel _projectTitle;
	protected HTML _goodPracticesLong;
	Button _displayPracticesButton;

	public CreateFeature(String projectID){
		super(2);
		_projectID = projectID;
		_projectTitle = new HorizontalPanel();
		_com.userCreatedFeature(Cookies.getCookie("fears"), getCreatedFeatureCB);
		
		Fears.getPath().setFeatureChange("", _projectID, "Nova Sugestão");
		
		_name = new TextBox();
		_name.setVisibleLength(50);
		_name.setStyleName("titleTextBox");
		_table.setWidget(0, 0,new HTML("T&iacute;tulo:"));
		_table.setWidget(0, 1,(_name));
		
	
		_description= new TextArea();
		_description.setStyleName("descriptionTextBox");
		_table.setWidget(1, 0, new HTML("Descri&ccedil;&atilde;o:&nbsp;"));
		_table.setWidget(1, 1, _description);
		
		PushButton _sendButton = new PushButton(new Image("button04.gif",0,0,105,32), new Image("button04.gif",-2,-2,105,32) );
		_sendButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				_errorPanel.clear();

				if(_name.getText().length() == 0 || _description.getText().length() == 0){
					_errorPanel.add(new HTML("Erro:"));
					if(_name.getText().length() == 0)
						_errorPanel.add(new HTML("Tem de preencher o nome da sugest&atilde;o."));
					if(_description.getText().length() == 0)
						_errorPanel.add(new HTML("Tem de preencher a descri&ccedil;&atilde;o da sugest&atilde;o."));		


					return;
				}
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
		
		_buttons.add(_sendButton);
		_buttons.add(_cancelButton);
		
		FlowPanel goodPracticesPanel = new FlowPanel();
		goodPracticesPanel.add(new HTML(Fears.getgoodPracticesHead()+"."));
		_goodPracticesLong= new HTML(Fears.getgoodPracticesLong());
		_goodPracticesLong.setVisible(false);
		_displayPracticesButton = new Button("Ler Mais");
		_displayPracticesButton.addClickListener( new ClickListener(){
			public void onClick(Widget sender) {
				if(!_goodPracticesLong.isVisible()){
					_goodPracticesLong.setVisible(true);
					_displayPracticesButton.setText("Esconder");
				}else {
					_goodPracticesLong.setVisible(false);
					_displayPracticesButton.setText("Ler Mais");
				}
			
			}
		});
		_displayPracticesButton.setStyleName("moreButton");
		goodPracticesPanel.add(_displayPracticesButton);
		goodPracticesPanel.add(_goodPracticesLong);
		
		_goodPractices.add(goodPracticesPanel);
		
		updateProjectName();
	}

	public void updateProjectName(){
		_com.getProjectName(_projectID, getProjectName);
	}
	
	protected void updateProjectName(String name){
		Fears.getPath().setFeatureChange(name, _projectID,  "Nova Sugestão");
	}
	
	protected void getCreatedFeature(boolean created){
		if(!created){
			_goodPracticesLong.setVisible(true);
			_displayPracticesButton.setText("Esconder");
		}
	}
	
	
	AsyncCallback addSugestaoCB = new ExceptionsTreatment() {
		public void onSuccess(Object result){ 
			// do some UI stuff to show success
			History.newItem("Project" + _projectID );	
		}
	};

	AsyncCallback getProjectName = new ExceptionsTreatment() {
		public void onSuccess(Object result){ 
			updateProjectName((String)result);
		}

	};
	
	AsyncCallback getCreatedFeatureCB = new ExceptionsTreatment() {
		public void onSuccess(Object result){ 
			getCreatedFeature((Boolean)result);
		}

	};
	
}









