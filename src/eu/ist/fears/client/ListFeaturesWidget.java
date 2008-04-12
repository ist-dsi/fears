package eu.ist.fears.client;


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
import eu.ist.fears.client.views.ViewFeatureResume;


public class ListFeaturesWidget extends Composite {

	Communication _com;
	private VerticalPanel _sugPanel;
	//private HashMap _sugWidgets;

	public ListFeaturesWidget(){
		_com= new Communication("service");
		_sugPanel = new VerticalPanel();
		initWidget(_sugPanel);
		_sugPanel.setStyleName("featuresList");
		_sugPanel.add( new HTML("<h1>Sugestoes</h1>"));
	}

	private class FeatureResumeWidget extends Composite{

		VerticalPanel _feature; 
		VerticalPanel _featureContainer; 
		Label _name;
		Label _description;
		Label _votes;
		Label _nComments;
		Label _alert;
		HorizontalPanel _info;
		Button _voteButton;


		FeatureResumeWidget(ViewFeatureResume f){

			_name = new Label(f.getName());
			_description= new Label(f.getDescription());
			_votes = new Label(new Integer(f.getVotes()).toString());
			_nComments = new Label(new Integer(f.getNComments()).toString());

			_feature = new VerticalPanel();
			_featureContainer = new VerticalPanel();
			_featureContainer.add(_feature);
			_alert = new Label();
			_voteButton = new Button("Votar");
			_voteButton.addClickListener(new BotaoVoto());
			_info=new HorizontalPanel();

			_featureContainer.setStyleName("featureResume");
			_feature.add(new Hyperlink("<b>"+_name.getText()+ "</b>",true, "viewFeature" + _name.getText() )); 
			_feature.add(_description);
			_feature.add(new Label(" ")); //Line Break
			_feature.add(_info);
			_feature.add(new Label(" ")); //Line Break
			_feature.add(_voteButton);
			_feature.add(_alert);
			_info.add(new Label("Autor: ...   |  N de Votos:  "));
			_info.add(_votes);
			_info.add(new Label("  |  N de Comentarios:  "));
			_info.add(_nComments);

			initWidget(_featureContainer);
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
				_alert.setText("O teu voto em " + _name.getText() + " foi contabilizado.");
				_com.vote(_name.getText(), voteCB);
			}

		}

	}

	public void update(){
		_com.getFeatures(getFeaturesCB);		
	}

	public void test(){

		_com.addFeature("Sug1", "blalha vhajbv ahsha fgf dg  fg d gfg fghf fhd", addFeatureCB);
		_com.addFeature("Sug2", "vh   ajbv ah  sha blal   fhdh fgh gh  gfh f  ha ", addFeatureCB);
		_com.addFeature("Sug3", ".. .. .. ...   .... hgfghf  hhfgf fh ghf", addFeatureCB);
		_com.getFeatures(getFeaturesCB);
	}



	public void actualizaSugestoes(ViewFeatureResume[] features){

		if(features==null)
			return;

		_sugPanel.clear();

		for(int i=0;i< features.length;i++){
			_sugPanel.add(new FeatureResumeWidget(features[i]));
		}
	}

	AsyncCallback addFeatureCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			// do some UI stuff to show success
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"));
		}
	}; 

	AsyncCallback getFeaturesCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			actualizaSugestoes((ViewFeatureResume[])result);
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("A cena das sugestoes não correu bem"));
		}
	};


	AsyncCallback voteCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			update();	
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"));
		}
	};

}
