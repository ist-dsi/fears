package eu.ist.fears.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
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
	private Button _sendButton;
	private String _projectName;

	public CreateFeatureWidget(String projectName){
		_com= new Communication("service");
		_sugPanel = new VerticalPanel();
		_projectName = projectName;
		initWidget(_sugPanel);
		_sugPanel.setStyleName("createFeature");
		_sugPanel.add( new HTML("<h1>Criar Sugestao</h1><br>"));
		_name = new TextBox();
		_description= new TextArea();
		_sendButton = new Button("Enviar");
		
		
		_sugPanel.add(new Label("Nome da Sugestão:"));
		_sugPanel.add(_name);
		_sugPanel.add(new Label("Descrição da Sugestão:"));
		_description.setVisibleLines(7);
		_description.setCharacterWidth(40);
		_sugPanel.add(_description);
		_sugPanel.add(_sendButton);

		_sendButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				_com.addFeature(_projectName, _name.getText(),
						_description.getText(),  Cookies.getCookie("fears"), addSugestaoCB);
				
			}
		});
	}

	AsyncCallback addSugestaoCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			// do some UI stuff to show success
			
			History.newItem("Project" + _projectName );
			//Fears.listFeatures(_projectName);
			
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"),0,30);
		}
	};

}









