package eu.ist.fears.client.interfaceweb;


import com.google.gwt.user.client.Cookies;
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
import eu.ist.fears.client.Fears;
import eu.ist.fears.client.communication.Communication;
import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewFeatureResume;

public class FeatureResumeWidget  extends Composite{

	protected Communication _com;
	protected VerticalPanel _feature;
	protected HorizontalPanel _featureResume; 
	protected VerticalPanel _mainBox;
	protected String _projectName;
	protected String _projectID;
	protected Label _name;
	protected Label _author;
	protected Label _description;
	protected Label _votes;
	protected Button _voteButton;
	protected Label _ncomments;
	protected AsyncCallback _cb;
	protected ClickListener _removeVote;
	protected ClickListener _vote;

	public FeatureResumeWidget(ViewFeatureResume f, AsyncCallback cb){

		_com = new Communication("service");
		_projectName = f.getProjectName();
		_projectID = new Integer(f.getProjectID()).toString();
		if(cb==null)
			_cb=voteOnListCB;
		else _cb=cb;
		_feature = new VerticalPanel();
		VerticalPanel voteBox = new VerticalPanel();
		VerticalPanel vote = new VerticalPanel();
		HorizontalPanel info = new HorizontalPanel();
		_ncomments = new Label(new Integer(f.getNComments()).toString());
		_mainBox = new VerticalPanel();
		_name = new Label(f.getName());
		_description= new Label(f.getDescription());
		_votes = new Label(new Integer(f.getVotes()).toString());
		_featureResume = new HorizontalPanel();
		_voteButton = new Button();
		_author = new Label(f.getAuthor());
		_removeVote = new RemoveVoteButton();
		_vote = new VoteButton();
		
		_feature.add(_featureResume);
		
		voteBox.setStyleName("voteBox");
		_featureResume.add(voteBox);
		_mainBox.setStyleName("mainBox");
		_featureResume.add(_mainBox);
		
		vote.setStyleName("vote");
		voteBox.add(vote);
		
		_votes.setStyleName("NumberVotes");
		vote.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		vote.add(_votes);
		
		if(Fears.isLogedIn()){
			
			
			_voteButton.setStyleName("ActionVotes");
			if(f.userHasVoted()){
				_voteButton.setText("Tirar Voto");
				_voteButton.addClickListener(_removeVote);
			}else {
				_voteButton.setText("Votar");
				_voteButton.addClickListener(_vote);
			}
			vote.add(_voteButton);
		}
		
		HorizontalPanel title = new HorizontalPanel();
		title.setStyleName("featureTitle");
		title.add(new Hyperlink(f.getName(),"Project"+_projectID+"?"+"viewFeature"+f.getName()));
		_mainBox.add(title);
		
		
		info.setStyleName("meta");
		_mainBox.add(info);
		
		
		info.add(new HTML("#1 | Por:&nbsp;"));
		info.add(_author);
		info.add(new HTML("&nbsp;|&nbsp;"));
		info.add(_ncomments);
		info.add(new HTML("&nbsp;coment&aacute;rios"));
		
		_mainBox.add(_description);
		initWidget(_feature);

	}

	public void update(ViewFeatureResume f, boolean updateDescription){
		_voteButton.removeClickListener(_removeVote);
		_voteButton.removeClickListener(_vote);
		if(f.userHasVoted()){
			_voteButton.setText("Tirar Voto");
			_voteButton.addClickListener(_removeVote);
		}else {
			_voteButton.setText("Votar");
			_voteButton.addClickListener(_vote);
		}
		
		if(updateDescription)
			_description.setText(f.getDescription());
		
		_votes.setText(new Integer(f.getVotes()).toString());
		_author.setText(f.getAuthor());
		//_voters.setText("");
		
		
		

		/*if(f.getVoters().size()>0)
			for(int i=0; i<f.getVoters().size();i++){
				if(i!=0)_voters.setText(_voters.getText() + ", "); 
				_voters.setText(_voters.getText() + ((ViewVoter)f.getVoters().get(i)).getName()); 
			} */

		_ncomments.setText(new Integer(f.getNComments()).toString());
		
	}

	protected class VoteButton implements ClickListener{

		public void onClick(Widget sender) {
			//_alert.setText("O teu voto em " + _name.getText() + " foi contabilizado.");
			_com.vote(_projectID, _name.getText(), Cookies.getCookie("fears"), _cb);
		}

	}
	
	protected class RemoveVoteButton implements ClickListener{

		public void onClick(Widget sender) {
			//_alert.setText("O teu voto em " + _name.getText() + " foi contabilizado.");
			_com.removeVote(_projectID, _name.getText(), Cookies.getCookie("fears"), _cb);
		}

	}
	
	protected AsyncCallback  voteOnListCB = new AsyncCallback(){

		
		public void onFailure(Throwable arg0) {
			RootPanel.get().add(new Label("O Voto nao correu bem."));
			
		}

		public void onSuccess(Object result) {
			ViewFeatureDetailed feature = (ViewFeatureDetailed) result;
			if(result==null){
				//Erro
			} else {
			FeatureResumeWidget.this.update(feature, false);
			}
		}
		
		
	};
	
	
}


