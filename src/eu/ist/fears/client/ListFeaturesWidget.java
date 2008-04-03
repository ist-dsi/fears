package eu.ist.fears.client;



import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
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
		_sugPanel.setStyleName("featuresList");
		_sugWidgets = new HashMap();
		_sugPanel.add( new HTML("<h1>Sugestoes</h1><br>"));
	}

	private class FeatureResumeWidget extends Composite{
		
		VerticalPanel _feature; 
		Label _name;
		Label _description;
		Label _votes;
		Label _msg;
		HorizontalPanel _info;
		Button _voteButton;
		
		
		FeatureResumeWidget(String nome, String descricao, String votos){
			
			_name = new Label(nome);
			_description= new Label(descricao);
			_votes = new Label(votos);
			_feature = new VerticalPanel();
			_msg = new Label();
			_voteButton = new Button("Votar");
			_voteButton.addClickListener(new BotaoVoto());
			_info=new HorizontalPanel();
			
			
			_feature.setStyleName("featureResume");
			_feature.add(new Hyperlink("<h3>"+_name.getText()+ "</h3>",true, "viewFeature" + _name.getText() )); 
			_feature.add(_description);
			_feature.add(_voteButton);
			_feature.add(_msg);
			_feature.add(_info);
			_info.add(new Label("Autor: ...  | N de Votos:  "));
			_info.add(_votes);
					
			initWidget(_feature);
		}
		
		public void updateValues(String nome,String  descricao, String votos ){
			_name.setText(nome);
			_description.setText(descricao);
			_votes.setText(votos);	
		}
		
		public void updateVotos(String votos){
			_votes.setText(votos);
		}
		
		
		private class BotaoVoto implements ClickListener{
		
			public void onClick(Widget sender) {
				_msg.setText("O teu voto em " + _name.getText() + " foi contabilizado.");
				_com.vote(_name.getText(), voteCB);
			}

		}

	}

	public void init(){
		_com.getFeatures(getFeaturesCB);		
	}
	
	public void test(){

		_com.addFeature("Sug1", "blalha vhajbv ahsha fgf dg  fg d gfg fghf fhd", addFeatureCB);
		_com.addFeature("Sug2", "vh   ajbv ah  sha blal   fhdh fgh gh  gfh f  ha ", addFeatureCB);
		_com.addFeature("Sug3", ".. .. .. ...   .... hgfghf  hhfgf fh ghf", addFeatureCB);
		_com.getFeatures(getFeaturesCB);
	}



	public void actualizaSugestoes(String[][] sugestoes){
		
		if(sugestoes==null)
			return;

		for(int i=0;i< sugestoes.length;i++){
			FeatureResumeWidget sugestao = (FeatureResumeWidget) _sugWidgets.get(sugestoes[i][0]);
			
			//Sugestao já está no painel, vamos actualiza-la.
			if(sugestao!=null){
				sugestao.updateValues(sugestoes[i][0], sugestoes[i][1], sugestoes[i][2]);
			}
			else{ //A sugestao ainda nao existia, vamos cria-la e mostra-la.
				_sugWidgets.put(sugestoes[i][0],
						new FeatureResumeWidget(sugestoes[i][0], sugestoes[i][1], sugestoes[i][2]));
				_sugPanel.add((FeatureResumeWidget) _sugWidgets.get(sugestoes[i][0]));
			}
			
		}

	}

	AsyncCallback addFeatureCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			// do some UI stuff to show success
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"),0,30);
		}
	};

	AsyncCallback getFeaturesCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			actualizaSugestoes((String[][])result);
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("A cena das sugestoes não correu bem"));
		}
	};

	AsyncCallback featureCB = new AsyncCallback() {
		public void onSuccess(Object result){ 

		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"));
		}
	};

	AsyncCallback voteCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			String[] feat = (String[])result;
			FeatureResumeWidget s= ((FeatureResumeWidget)_sugWidgets.get(feat[0]));
			s.updateVotos(feat[2]);

		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"));
		}
	};

}
