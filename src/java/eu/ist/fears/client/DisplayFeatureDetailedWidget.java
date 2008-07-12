package eu.ist.fears.client;


import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import eu.ist.fears.client.communication.Communication;
import eu.ist.fears.client.views.ViewComment;
import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewVoter;

public class DisplayFeatureDetailedWidget  extends Composite{

	private Communication _com;
	private VerticalPanel _feature; 
	private String _projectName;
	private Label _name;
	private Label _description;
	private Label _votes;
	private Button _voteButton;
	private TextArea _commentTextArea;
	private PushButton _commentButton;
	private Label _voters;
	private Label _author;
	private Label _ncomments;

	public DisplayFeatureDetailedWidget(String projectName, String featureName){

		_com = new Communication("service");
		_projectName = projectName;

		_name = new Label(featureName);
		_description= new Label();
		_votes = new Label("votes");
		_feature = new VerticalPanel();
		_voteButton = new Button("Votar");
		_voteButton.addClickListener(new VoteButton());
		_author = new Label("");
		_voters= new Label("");
		_ncomments = new Label("");
		_commentTextArea= new TextArea();
		_commentButton = new PushButton(new Image("button03.gif",0,0,105,32), new Image("button03.gif",-2,-2,105,32));
		_commentButton.addClickListener(new CommentButton());
		_commentTextArea.setVisibleLines(5);
		_commentTextArea.setCharacterWidth(50);
		RootPanel featureTemplate = RootPanel.get("featureDisplay");
		
		RootPanel pName = RootPanel.get("rFeatDisplay:Project");
		pName.clear();
		pName.add(new Hyperlink(projectName,"Project"+projectName));
		
		RootPanel fName = RootPanel.get("rFeatDisplay:FeatureName");
		fName.clear();
		fName.add(new Hyperlink(featureName,"Project"+projectName+"?"+"viewFeature"+featureName));
		
		RootPanel back = RootPanel.get("rFeatDisplay:Back");
		back.clear();
		back.add(new Hyperlink("<< Back","Project"+projectName));
		
		RootPanel votesT = RootPanel.get("rFeatDisplay:NVotes");
		votesT.clear();
		votesT.add(_votes);
		
		RootPanel actionVote = RootPanel.get("rFeatDisplay:ActionVote");
		actionVote.clear();
		actionVote.add(new Label("Action Vote"));
		
		RootPanel title = RootPanel.get("rFeatDisplay:Title");
		title.clear();
		title.add(new Hyperlink(featureName,"Project"+projectName+"?"+"viewFeature"+featureName));
		
		RootPanel number = RootPanel.get("rFeatDisplay:Number");
		number.clear();
		number.add(new Label("#1"));
		
		RootPanel author = RootPanel.get("rFeatDisplay:Author");
		author.clear();
		author.add(_author);
		
		RootPanel nComments = RootPanel.get("rFeatDisplay:NComments");
		nComments.clear();
		nComments.add(_ncomments);
		
		RootPanel description = RootPanel.get("rFeatDisplay:Description");
		description.clear();
		description.add(_description);
		
		if(Fears.isLogedIn()){
		RootPanel newComment = RootPanel.get("rFeatDisplay:comment");
		newComment.clear();
		newComment.add(_commentTextArea);
		
		RootPanel commentButton = RootPanel.get("rFeatDisplay:commentButton");
		commentButton.clear();
		commentButton.add(_commentButton);
		}
		
		_com.getFeature(projectName, featureName , Cookies.getCookie("fears"), getFeatureCB);

		initWidget(_feature);

		featureTemplate.setStyleName("kkcoisa");
	}

	private void updateFeature(ViewFeatureDetailed f){
		_description.setText(f.getDescription());
		_votes.setText(new Integer(f.getVotes()).toString());
		_author.setText(f.getAuthor());
		_voters.setText("");

		/*if(f.getVoters().size()>0)
			for(int i=0; i<f.getVoters().size();i++){
				if(i!=0)_voters.setText(_voters.getText() + ", "); 
				_voters.setText(_voters.getText() + ((ViewVoter)f.getVoters().get(i)).getName()); 
			} */

		_ncomments.setText(new Integer(f.getComments().size()).toString());
		RootPanel comments = RootPanel.get("rFeatDisplay:Comments");
		comments.clear();
		Label comment;
		Label header;
		if(f.getComments().size()>0)
			for(int i=0; i<f.getComments().size();i++){
				comment = new Label();
				comment.setStyleName("comment");
				header = new Label("header");
				header.setStyleName("metaComment");
				comment = new HTML("<div class=\"comment\">" + header.getElement().toString() + "</div>" );
				Label commentText = new Label(((ViewComment)f.getComments().get(i)).getComment());
				commentText.setStyleName("commentText");
				comment = new HTML(comment.getElement().toString() + commentText );
				comments.add(comment);
			} 
	}

	private void update(){
		_com.getFeature(_projectName, _name.getText(), Cookies.getCookie("fears"), getFeatureCB);
	}


	private class VoteButton implements ClickListener{

		public void onClick(Widget sender) {
			//_alert.setText("O teu voto em " + _name.getText() + " foi contabilizado.");
			_com.vote(_projectName, _name.getText(), Cookies.getCookie("fears"), voteCB);
		}

	}

	private class CommentButton implements ClickListener{

		public void onClick(Widget sender) {
			//_alert.setText("O teu comentario foi inserido.");
			_com.addComment(_projectName, _name.getText(),
					_commentTextArea.getText(), Cookies.getCookie("fears"), getFeatureCB);
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
			_feature.clear();
			_feature.add((new Label("A Sugestao " + _name.getText() + " nao foi encontrada.")));
			try {
				throw caught;
			} catch(RuntimeException e){
				_feature.add(new Label("Erro:"+e.getMessage()));

			} catch (Throwable e) {
				_feature.add(new Label(e.getMessage()));
			}

		}
	};

}


