package eu.ist.fears.client.interfaceweb;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.client.Fears;
import eu.ist.fears.common.State;
import eu.ist.fears.common.views.ViewComment;
import eu.ist.fears.common.views.ViewFeatureDetailed;
import eu.ist.fears.common.views.ViewVoterResume;

public class FeatureDetailedWidget extends FeatureResumeWidget {

    protected VerticalPanel comments;
    protected TextArea commentTextArea;
    protected ListBox lb;
    protected PushButton commentButton;
    protected VerticalPanel newCommentBox;
    protected VerticalPanel votesAndVoters;
    protected HTML votesLeft;
    protected DisclosurePanel voters;
    protected VerticalPanel errors;

    public FeatureDetailedWidget(ViewFeatureDetailed f, AsyncCallback<Object> cb) {
	super(f, cb);
	
	voters = new DisclosurePanel();
	votesAndVoters = new VerticalPanel();
	comments = new VerticalPanel();
	newCommentBox = new VerticalPanel();
	commentTextArea = new TextArea();
	commentTextArea.setStyleName("commentTextArea");
	lb = new ListBox();
	errors = new VerticalPanel();
	errors.setStyleName("error");
	errors.addStyleDependentName("comment");

	commentButton = new PushButton(new Image("button03.gif", 0, 0, 105, 32), new Image("button03.gif", -2, -2, 105, 32));
	commentButton.setStyleName("commentButton");
	comments.setStyleName("width100");
	votesAndVoters.setStyleName("votesAndVoters");
	feature.add(votesAndVoters);
	feature.add(comments);

	newCommentBox.setStyleName("newComment");

	updateStateChooser(f);

	commentButton.addClickHandler(new CommentButton());

	votesLeft = new HTML();

	votesLeft.setStyleName("votersMeta");
	votesAndVoters.add(votesLeft);
	votesAndVoters.add(voters);

	updateVoters(f);
	updateComments(f);
    }

    public void update(ViewFeatureDetailed f) {
	super.update(f, true);
	updateStateChooser(f);
	updateVoters(f);
	updateComments(f);
    }

    public void updateUserInfo() {
	super.updateUserInfo();
	updateVotesLeft();
    }

    public void updateVotesLeft() {
	if (Fears.isLogedIn()) {
	    votesLeft.setHTML("Tem " + Fears.getVotesLeft() + " votos disponiveis.");
	}
    }

    public void updateStateChooser(ViewFeatureDetailed f) {
	newCommentBox.clear();
	if (Fears.isLogedIn()) {
	    HorizontalPanel stateChooser = new HorizontalPanel();
	    Label stateLabel = new Label("Mudar Estado:");
	    stateLabel.setStyleName("commentButton");
	    stateChooser.add(stateLabel);
	    lb.setStyleName("commentStateChooser");

	    stateChooser.add(lb);

	    lb.clear();

	    lb.addItem("");
	    for (State s : State.values()) {
		if (!s.toString().equals(f.getState().toString())) {
		    lb.addItem(s.getListBoxHTML(), s.toString());
		}
	    }
	    newCommentBox.add(commentTextArea);
	    if (Fears.isAdminUser() || f.isProjectAdmin())
		newCommentBox.add(stateChooser);
	    newCommentBox.add(commentButton);
	    newCommentBox.add(errors);
	}
    }

    public void updateVoters(ViewFeatureDetailed f) {
	votesLeft.setHTML("");
	voters.setHeader(new Label(""));
	voters.setContent(new Label(""));

	if (Fears.isLogedIn()) {
	    votesLeft.setHTML("Tem " + Fears.getVotesLeft() + " votos disponiveis.");
	}

	if (f.getVoters() != null && f.getVoters().size() > 0) {
	    voters.setHeader(new HTML("Ver " + f.getVoters().size() + " votantes &raquo;"));
	    voters.getHeader().setStyleName("showVoters");
	    VerticalPanel votersExpanded = new VerticalPanel();
	    int i = 0;
	    for (ViewVoterResume v : f.getVoters()) {
		votersExpanded.add(new Hyperlink(v.getNick(), "Project" + projectID + "&" + "viewUser" + v.getOID()));
		// if(i!= f.getVoters().size()-1)
		// votersExpanded.add(new HTML(",&nbsp;"));
		i++;
	    }
	    voters.setContent(votersExpanded);
	    voters.getContent().setStyleName("votersMeta");

	} else {
	    voters.setContent(new HTML("Nao h&aacute; vontantes."));
	    voters.setOpen(true);
	}

    }

    public void updateComments(ViewFeatureDetailed f) {
	VerticalPanel comment;
	HorizontalPanel header;

	comments.clear();
	Label commentTitle = new HTML("Coment&aacute;rios:");
	commentTitle.setStylePrimaryName("commentTitle");
	comments.add(commentTitle);

	if (f.getComments().size() > 0) {
	    ViewComment actual;
	    for (int i = f.getComments().size() - 1; i >= 0; i--) {
		actual = f.getComments().get(i);
		header = new HorizontalPanel();
		HorizontalPanel text = new HorizontalPanel();
		header.add(text);
		header.setStyleName("commentItem");
		text.add(new HTML("Por:&nbsp;"));
		text.add(new Hyperlink(actual.getAuthorNick(), "Project" + projectID + "&" + "viewUser"
			+ (f.getComments().get(i)).getAuthorOID()));
		text.add(new HTML("&nbsp; em:&nbsp;" + actual.getCreatedDate()));
		if (actual.getNewState() != null) {
		    text.add(new HTML("&nbsp;|&nbsp;Estado mudou de&nbsp;" + actual.getOldState().getHTML() + "&nbsp;para&nbsp;"
			    + actual.getNewState().getHTML()));
		}

		text.setStyleName("meta");
		HTML commentText = new HTML(actual.getComment());
		commentText.setStyleName("commentItem");
		comment = new VerticalPanel();
		comment.setWidth("100%");
		comment.add(header);
		comment.add(commentText);
		comment.setStyleName("comment");
		comments.add(comment);
	    }

	} else {
	    HTML info = new HTML("Nao existem coment&aacute;rios.");
	    info.setStyleName("commentButton");
	    comments.add(info);
	}

	comments.add(newCommentBox);
    }

    private class CommentButton implements ClickHandler {

	public void onClick(ClickEvent sender) {
	    State state = null;
	    for (State s : State.values()) {
		if (s.toString().equals(lb.getValue(lb.getSelectedIndex())))
		    state = s;
	    }

	    errors.clear();
	    if (commentTextArea.getText().length() == 0) {
		errors.add(new HTML("Erro:"));
		errors.add(new HTML("Tem de preencher o coment&aacute;rio."));
		return;
	    }

	    com.addComment(projectID, featureID, commentTextArea.getText(), state, Cookies.getCookie("fears"), cb);
	    commentTextArea.setText("");
	}

    }

}
