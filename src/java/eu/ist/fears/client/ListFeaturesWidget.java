package eu.ist.fears.client;


import java.util.List;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ist.fears.client.communication.Communication;
import eu.ist.fears.client.views.ViewFeatureResume;


public class ListFeaturesWidget extends Composite {

	private Communication _com;
	private VerticalPanel _sugPanel;
	private String _projectName;

	public ListFeaturesWidget(String projectName){
		_com= new Communication("service");
		_sugPanel = new VerticalPanel();
		_projectName = projectName;
		_sugPanel.setStyleName("featuresList");
		init();
		initWidget(_sugPanel);
	}
	
	private void init(){
		_sugPanel.clear();
		
		RootPanel featureListTemplate = RootPanel.get("ProjectlistFeatures");
		
		
		RootPanel pName = RootPanel.get("rlistFeatures:Project");
		pName.clear();
		pName.add(new Hyperlink(_projectName,"Project"+_projectName));

		RootPanel searchBox = RootPanel.get("rlistFeatures:searchBox");
		searchBox.clear();
		TextBox sBox = new TextBox();
		sBox.setVisibleLength(50);
		searchBox.add(sBox);
		
		RootPanel buttons = RootPanel.get("rlistFeatures:searchButton");
		buttons.clear();
		PushButton searchButton = new PushButton(new Image("button01.gif",0,0,105,32),new Image("button01.gif",-2,-2,105,32));
		buttons.add(searchButton);
		
		RootPanel buttons2 = RootPanel.get("rlistFeatures:createButton");
		buttons2.clear();
		PushButton addButton = new PushButton(new Image("button02.gif",0,0,135,32),new Image("button02.gif",-2,-2,135,32)); 
		buttons2.add(addButton);
		addButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				History.newItem("Project" + _projectName + "?" + "addFeature");
				
			}
		});
		
	    
	    RootPanel sort = RootPanel.get("rlistFeatures:Sort");
	    sort.clear();
	    ListBox lb = new ListBox();
	    lb.addItem("Ordenar por Votos");
	    lb.addItem("Ordenar por Data");
	    lb.setVisibleItemCount(1);
	    sort.add(lb);
	    
		featureListTemplate.setStyleName("kkcoisa");
	}

	public class FeatureResumeWidget extends Composite{

		VerticalPanel _featureContainer; 
		Label _name;
		Label _description;
		Label _votes;
		Label _nComments;
		Label _author;


		FeatureResumeWidget(ViewFeatureResume f, String projectName){

			_name = new Label(f.getName());
			_description= new Label(f.getDescription());
			_votes = new Label(new Integer(f.getVotes()).toString());
			_nComments = new Label(new Integer(f.getNComments()).toString());
			_author = new Label(f.getAuthor());

			_featureContainer = new VerticalPanel();
			
			
			RootPanel featureTemplate = RootPanel.get("rlistFeatures:featureHidden");
			featureTemplate.clear();
			
			RootPanel votes = RootPanel.get("rlistFeatures:votes");
			votes.clear();
			votes.add(_votes);
			
			RootPanel actionVote = RootPanel.get("rlistFeatures:actionvote");
			actionVote.clear();
			actionVote.add(new Label("Action Vote"));

			RootPanel fName = RootPanel.get("rlistFeatures:fname");
			fName.clear();
			fName.add(new Hyperlink(_name.getText(),"Project"+projectName+"?"+"viewFeature"+_name.getText()));
			
			RootPanel fNumber = RootPanel.get("rlistFeatures:number");
			fNumber.clear();
			fNumber.add(new Label("#1"));
			
			RootPanel fAuthor = RootPanel.get("rlistFeatures:author");
			fAuthor.clear();
			fAuthor.add(_author);
			
			RootPanel nComments = RootPanel.get("rlistFeatures:comments");
			nComments.clear();
			nComments.add(_nComments);
						
			RootPanel description = RootPanel.get("rlistFeatures:description");
			description.clear();
			description.add(_description);
			
			RootPanel features = RootPanel.get("rlistFeatures:Features");
			
			String feature = featureTemplate.toString();
			feature = feature.replaceAll("hiddenFeatureClass1", "notHidden");
			features.add(new HTML(feature));
			
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


		private class VoteButton implements ClickListener{

			public void onClick(Widget sender) {
				//_alert.setText("O teu voto em " + _name.getText() + " foi contabilizado.");
				_com.vote(_projectName, _name.getText(),  Cookies.getCookie("fears"), voteCB);
			}

		}

	}

	public void update(){
		_com.getFeatures(_projectName, Cookies.getCookie("fears"), getFeaturesCB);		
	}


	public void updateFeatures(List features){
		
		init();

		if(features==null || features.size() ==0 ){
			_sugPanel.add(new Label("Nao ha Sugestoes."));
			return;
		}
			
		 RootPanel.get("rlistFeatures:Features").clear();
		for(int i=0;i< features.size();i++){
			new FeatureResumeWidget((ViewFeatureResume)features.get(i), _projectName );
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
			updateFeatures((List)result);
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
