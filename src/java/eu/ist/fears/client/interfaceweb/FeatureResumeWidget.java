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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ist.fears.client.Fears;
import eu.ist.fears.common.State;
import eu.ist.fears.common.communication.Communication;
import eu.ist.fears.common.views.ViewFeatureDetailed;
import eu.ist.fears.common.views.ViewFeatureResume;

public class FeatureResumeWidget  extends Composite{

	protected Communication _com;
	protected VerticalPanel _feature;
	protected HorizontalPanel _featureResume; 
	protected State _state;
	protected HTML _stateLabel;
	protected VerticalPanel _mainBox;
	protected String _projectID;
	protected String _featureID;
	protected String _featureName;
	protected boolean _userHasVoted;
	protected HTML _description;
	protected Label _votes;
	protected Button _voteButton;
	protected Label _ncomments;
	protected AsyncCallback _cb;
	protected ClickListener _removeVote;
	protected ClickListener _vote;
	protected ViewFeatureResume _viewFeature;

	public FeatureResumeWidget(ViewFeatureResume f, AsyncCallback cb){

		_com = new Communication("service");
		_projectID = new Integer(f.getProjectID()).toString();
		_cb=cb;
		_feature = new VerticalPanel();
		VerticalPanel voteBox = new VerticalPanel();
		VerticalPanel vote = new VerticalPanel();
		HorizontalPanel info = new HorizontalPanel();
		_featureID= new String(new Integer(f.getFeatureID()).toString());
		_ncomments = new Label(new Integer(f.getNComments()).toString());
		_featureName = f.getName();
		_mainBox = new VerticalPanel();
		_description= new HTML(f.getDescription());
		_votes = new Label(new Integer(f.getVotes()).toString());
		_featureResume = new HorizontalPanel();
		_voteButton = new Button();
		_removeVote = new RemoveVoteButton();
		_vote = new VoteButton();
		_userHasVoted=f.userHasVoted();
		_state = f.getState();
		_viewFeature=f;

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

		if(Fears.isLogedIn() && _state.equals(State.Novo)){
			_voteButton.setStyleName("ActionVotes");
			if(f.userHasVoted()){
				_voteButton.setText("Tirar Voto");
				_voteButton.addClickListener(_removeVote);

			}else {
				if(Fears.getVotesLeft()<=0);
				else {
					_voteButton.setText("Votar");
					_voteButton.addClickListener(_vote);
				}
			}

		}else{
			_voteButton.setVisible(false);
		}
		vote.add(_voteButton);

		HorizontalPanel title = new HorizontalPanel();
		Hyperlink link = new Hyperlink(_featureName,"Project"+_projectID+"&"+"viewFeature"+f.getFeatureID());
		link.setStyleName("featureTitle");
		title.add(link);
		_stateLabel = new HTML(f.getState().getHTML());
		_stateLabel.setStyleName(f.getState().toString());
		title.add(_stateLabel);
		_mainBox.add(title);



		info.setStyleName("meta");
		_mainBox.add(info);

		info.add(new HTML("#"+_featureID+" | Por:&nbsp;"));
		info.add(new Hyperlink(f.getAuthorNick(),"Project"+_projectID+"&"+"viewUser"+f.getAuthorOID()));
		info.add(new HTML("&nbsp;em:&nbsp;" + f.getCreatedDate() + "&nbsp;|&nbsp;"));
		info.add(_ncomments);
		info.add(new HTML("&nbsp;coment&aacute;rios"));


		_description.setStyleName("featureDescription");
		_mainBox.add(_description);
		initWidget(_feature);

	}

	public String getWebID(){
		return _featureID;
	}

	public String getFeatureName(){
		return _featureName;
	}
	
	public void updateUserInfo(){
		if(Fears.isLogedIn() && _state.equals(State.Novo) ){
			_voteButton.setStyleName("ActionVotes");
			_voteButton.setVisible(true);
			_voteButton.removeClickListener(_removeVote);
			_voteButton.removeClickListener(_vote);
			if(_userHasVoted){
				_voteButton.setText("Tirar Voto");
				_voteButton.addClickListener(_removeVote);
			}else {
				if(Fears.getVotesLeft()>0){
					_voteButton.setText("Votar");
					_voteButton.addClickListener(_vote);
				}else { _voteButton.setVisible(false); }
			}
		} else  { _voteButton.setVisible(false); }

	}

	public void update(ViewFeatureDetailed f, boolean updateDescription){

		_state= f.getState();
		_stateLabel.setHTML(f.getState().getHTML());
		_stateLabel.setStyleName(f.getState().toString());
		_userHasVoted=f.userHasVoted();
		_viewFeature=f;

		if(updateDescription)
			_description.setHTML(f.getDescription());

		_votes.setText(new Integer(f.getVotes()).toString());
		
		

		_ncomments.setText(new Integer(f.getNComments()).toString());

		updateUserInfo();

	}

	protected class VoteButton implements ClickListener{

		public void onClick(Widget sender) {
			_com.vote(_projectID, _featureID, Cookies.getCookie("fears"), _cb);
		}

	}

	protected class RemoveVoteButton implements ClickListener{

		public void onClick(Widget sender) {
			_com.removeVote(_projectID, _featureID, Cookies.getCookie("fears"), _cb);
		}

	}

}


