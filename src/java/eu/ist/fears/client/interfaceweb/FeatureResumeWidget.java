package eu.ist.fears.client.interfaceweb;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.client.Fears;
import eu.ist.fears.common.State;
import eu.ist.fears.common.communication.Communication;
import eu.ist.fears.common.views.ViewFeatureDetailed;
import eu.ist.fears.common.views.ViewFeatureResume;

public class FeatureResumeWidget extends Composite {

    protected Communication com;
    protected VerticalPanel feature;
    protected HorizontalPanel featureResume;
    protected State state;
    protected HTML stateLabel;
    protected VerticalPanel mainBox;
    protected String projectID;
    protected String featureID;
    protected String featureName;
    protected boolean userHasVoted;
    protected HTML description;
    protected Label votes;
    protected Button voteButton;
    protected Label ncomments;
    protected AsyncCallback<Object> cb;
    protected ClickHandler removeVote;
    protected ClickHandler vote;
    protected HandlerRegistration remoteVoteReg;
    protected HandlerRegistration voteReg;
    protected ViewFeatureResume viewFeature;

    public FeatureResumeWidget(ViewFeatureResume f, AsyncCallback<Object> cb) {

	com = new Communication("service");
	projectID = new Integer(f.getProjectID()).toString();
	this.cb = cb;
	feature = new VerticalPanel();
	VerticalPanel voteBox = new VerticalPanel();
	VerticalPanel vote = new VerticalPanel();
	HorizontalPanel info = new HorizontalPanel();
	featureID = new String(new Integer(f.getFeatureID()).toString());
	ncomments = new Label(new Integer(f.getNComments()).toString());
	featureName = f.getName();
	mainBox = new VerticalPanel();
	description = new HTML(f.getDescription());
	votes = new Label(new Integer(f.getVotes()).toString());
	featureResume = new HorizontalPanel();
	voteButton = new Button();
	removeVote = new RemoveVoteButton();
	this.vote = new VoteButton();
	userHasVoted = f.userHasVoted();
	state = f.getState();
	viewFeature = f;

	feature.add(featureResume);

	voteBox.setStyleName("voteBox");
	featureResume.add(voteBox);
	mainBox.setStyleName("mainBox");
	featureResume.add(mainBox);

	vote.setStyleName("vote");
	voteBox.add(vote);

	votes.setStyleName("NumberVotes");
	vote.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
	vote.add(votes);

	if (Fears.isLogedIn() && state.equals(State.Novo)) {
	    voteButton.setStyleName("ActionVotes");
	    if (f.userHasVoted()) {
		voteButton.setText("Tirar Voto");
		remoteVoteReg = voteButton.addClickHandler(removeVote);

	    } else {
		if (Fears.getVotesLeft() <= 0)
		    ;
		else {
		    voteButton.setText("Votar");
		    voteButton.addClickHandler(this.vote);
		}
	    }

	} else {
	    voteButton.setVisible(false);
	}
	vote.add(voteButton);

	HorizontalPanel title = new HorizontalPanel();
	Hyperlink link = new Hyperlink(featureName, "Project" + projectID + "&" + "viewFeature" + f.getFeatureID());
	link.setStyleName("featureTitle");
	title.add(link);
	stateLabel = new HTML(f.getState().getHTML());
	stateLabel.setStyleName(f.getState().toString());
	title.add(stateLabel);
	mainBox.add(title);

	info.setStyleName("meta");
	mainBox.add(info);

	info.add(new HTML("#" + featureID + " | Por:&nbsp;"));
	info.add(new Hyperlink(f.getAuthorNick(), "Project" + projectID + "&" + "viewUser" + f.getAuthorOID()));
	info.add(new HTML("&nbsp;em:&nbsp;" + f.getCreatedDate() + "&nbsp;|&nbsp;"));
	info.add(ncomments);
	info.add(new HTML("&nbsp;coment&aacute;rios"));

	description.setStyleName("featureDescription");
	mainBox.add(description);
	initWidget(feature);

    }

    public String getWebID() {
	return featureID;
    }

    public String getFeatureName() {
	return featureName;
    }

    public void updateUserInfo() {
	if (Fears.isLogedIn() && state.equals(State.Novo)) {
	    voteButton.setStyleName("ActionVotes");
	    voteButton.setVisible(true);
	    remoteVoteReg.removeHandler();
	    voteReg.removeHandler();
	    if (userHasVoted) {
		voteButton.setText("Tirar Voto");
		remoteVoteReg = voteButton.addClickHandler(removeVote);
	    } else {
		if (Fears.getVotesLeft() > 0) {
		    voteButton.setText("Votar");
		    voteReg = voteButton.addClickHandler(vote);
		} else {
		    voteButton.setVisible(false);
		}
	    }
	} else {
	    voteButton.setVisible(false);
	}

    }

    public void update(ViewFeatureDetailed f, boolean updateDescription) {

	state = f.getState();
	stateLabel.setHTML(f.getState().getHTML());
	stateLabel.setStyleName(f.getState().toString());
	userHasVoted = f.userHasVoted();
	viewFeature = f;

	if (updateDescription)
	    description.setHTML(f.getDescription());

	votes.setText(new Integer(f.getVotes()).toString());

	ncomments.setText(new Integer(f.getNComments()).toString());

	updateUserInfo();

    }

    protected class VoteButton implements ClickHandler {

	public void onClick(ClickEvent sender) {
	    com.vote(projectID, featureID, Cookies.getCookie("fears"), cb);
	}

    }

    protected class RemoveVoteButton implements ClickHandler {

	public void onClick(ClickEvent sender) {
	    com.removeVote(projectID, featureID, Cookies.getCookie("fears"), cb);
	}

    }

}
