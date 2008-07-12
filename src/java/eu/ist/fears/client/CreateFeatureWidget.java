package eu.ist.fears.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ist.fears.client.communication.Communication;


public class CreateFeatureWidget extends Composite {

	private Communication _com;
	private VerticalPanel _sugPanel;
	private TextBox _name; 
	private TextArea _description; 
	private String _projectName;

	public CreateFeatureWidget(String projectName){
		_com= new Communication("service");
		_sugPanel = new VerticalPanel();
		_projectName = projectName;
		initWidget(_sugPanel);
		_name = new TextBox();
		_description= new TextArea();
		
		_description.setVisibleLines(7);
		_description.setCharacterWidth(40);
		
		RootPanel newSugTemplate = RootPanel.get("newSug");
		
		RootPanel pName = RootPanel.get("rNewSug:Project");
		pName.clear();
		pName.add(new Hyperlink(projectName,"Project"+projectName));
		
		RootPanel titleTextBox =  RootPanel.get("rNewSug:Title");
		titleTextBox.clear();
		titleTextBox.add(_name);
		_name.setStyleName("titleTextBox");
		
		
		RootPanel descriptionTextBox =  RootPanel.get("rNewSug:Description");
		descriptionTextBox.clear();
		descriptionTextBox.add(_description);
		_description.setStyleName("descriptionTextBox");
		
		
		RootPanel buttons =  RootPanel.get("rNewSug:newFeatureButtons");
		buttons.clear();
		PushButton _sendButton = new PushButton(new Image("button04.gif",0,0,105,32), new Image("button04.gif",-2,-2,105,32) );
		buttons.setStyleName("button");
		_sendButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				_com.addFeature(_projectName, _name.getText(),
						_description.getText(),  Cookies.getCookie("fears"), addSugestaoCB);
				
			}
		});
		
		PushButton _cancelButton = new PushButton(new Image("button05.gif",0,0,92,32),new Image("button05.gif",-2,-2,92,32));
		_cancelButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				History.newItem("Project" + _projectName );
			}
		});
		_cancelButton.setStyleName("pleft5");
		
		buttons.add(_sendButton);
		buttons.add(_cancelButton);

		newSugTemplate.setStyleName("umkk");
	}

	
	
	AsyncCallback addSugestaoCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			// do some UI stuff to show success
			History.newItem("Project" + _projectName );
			
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Erro ao criar sugestao:\n" + caught.getMessage()));
		}
	};

}









