package eu.ist.fears.client;



import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ist.fears.client.communication.Communication;


public class ListFeaturesWidget extends Composite {
	
	Communication _com;
	private VerticalPanel _sugPanel;
	private HashMap _sugWidgets;

	public ListFeaturesWidget(){
		_com= new Communication("service");
		_sugPanel = new VerticalPanel();
		initWidget(_sugPanel);
		_sugWidgets = new HashMap();
	}

	private class SugestaoWidget extends Composite{
		
		VerticalPanel _sugestao; 
		Label _nome;
		Label _descricao;
		Label _votos;
		Label _msg;
		Button _botaoVoto;
		Label _alert;
		
		SugestaoWidget(String nome, String descricao, String votos){
			
			_nome = new Label(nome);
			_descricao= new Label(descricao);
			_votos = new Label(votos);
			_sugestao = new VerticalPanel();
			_msg = new Label();
			_botaoVoto = new Button("Votar");
			_botaoVoto.addClickListener(new BotaoVoto());
			_alert = new Label();
			
			_sugestao.setStyleName("sugestao");
			_sugestao.add( new Label("Nome: " + _nome.getText())); 
			_sugestao.add( new Label("Sugestao:"));
			_sugestao.add(_descricao);
			_sugestao.add(_votos);
			_sugestao.add(_botaoVoto);
			_sugestao.add(_alert);
			_sugestao.add(_msg);
			initWidget(_sugestao);
		}
		
		public void updateValues(String nome,String  descricao, String votos ){
			_nome.setText(nome);
			_descricao.setText(descricao);
			_votos.setText(votos);	
		}
		
		public void updateVotos(String votos){
			_votos.setText(votos);
		}
		
		
		private class BotaoVoto implements ClickListener{
		
			public void onClick(Widget sender) {
				_msg.setText("O teu voto em " + _nome.getText() + " foi contabilizado.");
				_com.vote(_nome.getText(), votarCB);
			}

		}

	}

	public void init(){
		_com.getFeatures(getSugestoesCB);		
	}
	
	public void test(){

		_com.addFeature("sug1", "blalha vhajbv ahsha", addSugestaoCB);
		_com.addFeature("sug2", "vh   ajbv ah  sha blal   ha ", addSugestaoCB);
		_com.addFeature("sug3", ".. .. .. ...   .... ", addSugestaoCB);
		_com.getFeatures(getSugestoesCB);
	}



	public void actualizaSugestoes(String[][] sugestoes){
		
		if(sugestoes==null)
			return;

		for(int i=0;i< sugestoes.length;i++){
			SugestaoWidget sugestao = (SugestaoWidget) _sugWidgets.get(sugestoes[i][0]);
			
			//Sugestao já está no painel, vamos actualiza-la.
			if(sugestao!=null){
				sugestao.updateValues(sugestoes[i][0], sugestoes[i][1], sugestoes[i][2]);
			}
			else{ //A sugestao ainda nao existia, vamos cria-la e mostra-la.
				_sugWidgets.put(sugestoes[i][0],
						new SugestaoWidget(sugestoes[i][0], sugestoes[i][1], sugestoes[i][2]));
				_sugPanel.add((SugestaoWidget) _sugWidgets.get(sugestoes[i][0]));
			}
			
		}

	}

	AsyncCallback addSugestaoCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			// do some UI stuff to show success
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"),0,30);
		}
	};

	AsyncCallback getSugestoesCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			actualizaSugestoes((String[][])result);
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("A cena das sugestoes não correu bem"),0,30);
		}
	};

	AsyncCallback sugestaoCB = new AsyncCallback() {
		public void onSuccess(Object result){ 

		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"),0,30);
		}
	};

	AsyncCallback votarCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			String[] sug = (String[])result;
			SugestaoWidget s= ((SugestaoWidget)_sugWidgets.get(sug[0]));
			s.updateVotos(sug[2]);

		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"),0,30);
		}
	};

}
