package eu.ist.fears.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ist.fears.client.communication.Communication;


public class CreateFeatureWidget extends Composite {

	Communication _com;
	private VerticalPanel _sugPanel;
	private TextBox _nome; 
	private TextArea _descricao; 
	private Button _botaoEnviar;

	public CreateFeatureWidget(){
		_com= new Communication("service");
		_sugPanel = new VerticalPanel();
		initWidget(_sugPanel);
		_nome = new TextBox();
		_descricao= new TextArea();
		_botaoEnviar = new Button("Enviar");
		
		
		_sugPanel.add(new Label("Nome da Sugestão:"));
		_sugPanel.add(_nome);
		_sugPanel.add(new Label("Descrição da Sugestão:"));
		_descricao.setVisibleLines(7);
		_descricao.setCharacterWidth(40);
		_sugPanel.add(_descricao);
		_sugPanel.add(_botaoEnviar);

		_botaoEnviar.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				_com.addFeature(_nome.getText(), _descricao.getText(), addSugestaoCB);
				
			}
		});
	}

	AsyncCallback addSugestaoCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			// do some UI stuff to show success
			
			Fears.viewFeatures();
			
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"),0,30);
		}
	};

}









