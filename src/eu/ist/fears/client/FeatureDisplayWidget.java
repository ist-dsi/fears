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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import eu.ist.fears.client.communication.Communication;

public class FeatureDisplayWidget  extends Composite{

	private Communication _com;
	private VerticalPanel _feature; 
	private Label _name;
	private Label _description;
	private Label _votes;
	private Label _msg;
	private HorizontalPanel _info;
	private Button _voteButton;
	private VerticalPanel _comments;
	private TextArea _comment;
	private Button _commentButton;


	FeatureDisplayWidget(String name){

		_com = new Communication("service");
		_com.getFeature(name, getFeatureCB);

		_name = new Label(name);
		_description= new Label();
		_votes = new Label();
		_feature = new VerticalPanel();
		_msg = new Label();
		_voteButton = new Button("Votar");
		_voteButton.addClickListener(new VoteButton());
		_info=new HorizontalPanel();
		_comments = new VerticalPanel();
		_comment= new TextArea();
		_commentButton = new Button("Adicionar Comentario");
		_commentButton.addClickListener(new CommentButton());

		_feature.setStyleName("featureDetailed");
		_feature.add(new HTML("<h2>"+_name.getText()+ "</h2>")); 
		_feature.add(_description);
		_feature.add(new Label(" ")); //Line Break
		_feature.add(_msg);
		_feature.add(_info);
		_info.add(new Label("Autor: ...  | N de Votos:  "));
		_feature.add(new Label(" ")); //Line Break
		_info.add(_votes);
		_feature.add(_voteButton);

		_feature.add(new Label(" ")); //Line Break
		_feature.add(_comments);
		_feature.add(new Label(" ")); //Line Break
		_comment.setVisibleLines(7);
		_comment.setCharacterWidth(40);
		_feature.add(_comment);
		_feature.add(_commentButton);


		initWidget(_feature);

	}

	private void updateFeature(String description, String votes, String[] comments){
		_description.setText(description);
		_votes.setText(votes);
		_comments.clear();
		_comments.add(new Label(comments.length-3 + " Comentarios:"));
		if(comments.length>3){
			for(int i=3;i<comments.length;i++){
				_comments.add(new Label(comments[i]));

			}
		}

	}

	private void updateVotes(String votes){
		_votes.setText(votes);
	}

	private class VoteButton implements ClickListener{

		public void onClick(Widget sender) {
			_msg.setText("O teu voto em " + _name.getText() + " foi contabilizado.");
			_com.vote(_name.getText(), voteCB);
		}

	}

	private class CommentButton implements ClickListener{

		public void onClick(Widget sender) {
			_msg.setText("O teu comentario foi inserido.");
			_com.addComment(_name.getText(),_comment.getText(), getFeatureCB);
		}

	}


	AsyncCallback voteCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			String[] feat = (String[])result;
			updateVotes(feat[2]);
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"));
		}
	};

	AsyncCallback getFeatureCB = new AsyncCallback() {
		public void onSuccess(Object result){ 

			String[] feature = (String[]) result;
			if(feature==null){
				_feature.clear();
				_feature.add(new Label("A sugestao nao existe."));	
			}else {
				updateFeature(feature[1],feature[2], feature);
			}

		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Isto não correu nada bem"));
		}
	};

}


