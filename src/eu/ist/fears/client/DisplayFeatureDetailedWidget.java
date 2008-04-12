package eu.ist.fears.client;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import eu.ist.fears.client.communication.Communication;
import eu.ist.fears.client.views.ViewComment;
import eu.ist.fears.client.views.ViewFeatureDetailed;

public class DisplayFeatureDetailedWidget  extends Composite{

	private Communication _com;
	private VerticalPanel _feature; 
	private Label _name;
	private Label _description;
	private Label _votes;
	private Label _alert;
	private HorizontalPanel _info;
	private Button _voteButton;
	private VerticalPanel _comments;
	private TextArea _commentTextArea;
	private Button _commentButton;


	DisplayFeatureDetailedWidget(String name){

		_com = new Communication("service");
		_com.getFeature(name, getFeatureCB);

		_name = new Label(name);
		_description= new Label();
		_votes = new Label();
		_feature = new VerticalPanel();
		_alert = new Label();
		_voteButton = new Button("Votar");
		_voteButton.addClickListener(new VoteButton());
		_info=new HorizontalPanel();
		_comments = new VerticalPanel();
		_commentTextArea= new TextArea();
		_commentButton = new Button("Adicionar Comentario");
		_commentButton.addClickListener(new CommentButton());

		_feature.setStyleName("featureDetailed");
		_feature.add(new HTML("<h2>"+_name.getText()+ "</h2>")); 
		_feature.add(_description);
		_feature.add(new Label(" ")); //Line Break
		_feature.add(_info);
		_info.add(new Label("Autor: ...  | N de Votos:  "));
		_info.add(_votes);	
		_feature.add(_voteButton);
		_feature.add(_alert);	
		
		_feature.add(new Label(" ")); //Line Break
		_feature.add(_comments);
		_feature.add(new Label(" ")); //Line Break
		_commentTextArea.setVisibleLines(7);
		_commentTextArea.setCharacterWidth(40);
		_feature.add(_commentTextArea);
		_feature.add(_commentButton);


		initWidget(_feature);

	}

	private void updateFeature(ViewFeatureDetailed f){
		_description.setText(f.getDescription());
		_votes.setText(new Integer(f.getVotes()).toString());
		_comments.clear();
		_comments.add(new Label(f.getComments().size() + " Comentarios:"));
		if(f.getComments().size()>0)
			for(int i=0; i<f.getComments().size();i++){
				_comments.add(new Label(" ")); //Line Break
				_comments.add(new Label(((ViewComment)f.getComments().get(i)).getComment()));
			}
	}

	private void update(){
		_com.getFeature(_name.getText(), getFeatureCB);
	}


	private class VoteButton implements ClickListener{

		public void onClick(Widget sender) {
			_alert.setText("O teu voto em " + _name.getText() + " foi contabilizado.");
			_com.vote(_name.getText(), voteCB);
		}

	}

	private class CommentButton implements ClickListener{

		public void onClick(Widget sender) {
			_alert.setText("O teu comentario foi inserido.");
			_com.addComment(_name.getText(),_commentTextArea.getText(), getFeatureCB);
			_commentTextArea.setText("");
		}

	}


	AsyncCallback voteCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			update();
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"));
		}
	};

	AsyncCallback getFeatureCB = new AsyncCallback() {
		public void onSuccess(Object result){ 

			ViewFeatureDetailed feature = (ViewFeatureDetailed) result;
			if(feature==null){
				_feature.clear();
				_feature.add(new Label("A sugestao nao existe."));	
			}else {
				updateFeature(feature);
			}

		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"));
		}
	};

}


