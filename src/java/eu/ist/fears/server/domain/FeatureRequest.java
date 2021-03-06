package eu.ist.fears.server.domain;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import eu.ist.fears.common.DateFormat;
import eu.ist.fears.common.State;
import eu.ist.fears.common.exceptions.ActionNotExecuted;
import eu.ist.fears.common.exceptions.FearsException;
import eu.ist.fears.common.views.ViewComment;
import eu.ist.fears.common.views.ViewFeatureDetailed;
import eu.ist.fears.common.views.ViewFeatureResume;
import eu.ist.fears.common.views.ViewVoterResume;
import eu.ist.fears.server.FearsServiceImpl;

public class FeatureRequest extends FeatureRequest_Base {

    public FeatureRequest() {
	super();
    }

    public FeatureRequest(String name, String description, Voter voter, int projectInitialVotes) {
	setName(name);
	// Remove all \r inserted by IE browser.
	description = description.replaceAll("\r", "");
	// Clean HTML Code
	description = description.replaceAll("\\<.*?\\>", "");
	// Put <br> on \n
	int count = 0;
	for (int i = 0; i < description.length(); i++, count++) {
	    if (description.charAt(i) == '\n')
		description = description.subSequence(0, i) + "<br>" + description.subSequence(i + 1, description.length());
	}
	setDescription(description);
	setAuthor(voter);
	setState(State.Novo);
	if (voter.getVotesUsed() < projectInitialVotes) {
	    voter.setVotesUsed(voter.getVotesUsed() + 1);
	    addVoter(voter);
	}

	DateTime date = new DateTime();
	setCreatedTime(date);
	setLastModification(date);
    }

    public int getVotes() {
	return getVoterCount();
    }

    public int getNComments() {
	return getCommentCount();

    }

    public void vote(Voter voter) throws FearsException {
	if (hasVoter(voter)) {
	    throw new ActionNotExecuted("Utilizador ja votou.");
	}

	if (voter.getVotesUsed() >= getProject().getInitialVotes()) {
	    throw new ActionNotExecuted("Utilizador nao tem votos.");
	}

	voter.setVotesUsed(voter.getVotesUsed() + 1);
	addVoter(voter);
    }

    public void removeVote(Voter voter) throws FearsException {
	if (!hasVoter(voter))
	    throw new ActionNotExecuted("Utilizador nao tem voto.");

	voter.setVotesUsed(voter.getVotesUsed() - 1);
	removeVoter(voter);
    }

    public void addComment(String comment, Voter voter, State newState) {
	setLastModification(new DateTime());
	addComment(new Comment(comment, voter, newState, getState()));
    }

    public String getAuthorName() {
	return getAuthor().getUser().getName();
    }

    public ViewFeatureDetailed getDetailedView(Voter voter) {
	List<ViewComment> comments = new ArrayList<ViewComment>();
	for (Comment c : getCommentSet()) {
	    comments.add(c.getView());
	}

	boolean userhasvoted = false;

	List<ViewVoterResume> voters = new ArrayList<ViewVoterResume>();
	for (Voter v : getVoterSet()) {
	    voters.add(new ViewVoterResume(v.getUser().getName(), FearsServiceImpl.getNickName(v.getUser().getName()), new Long(v
		    .getUser().getOid()).toString(), false));
	    if (v.equals(voter))
		userhasvoted = true;
	}

	if (voter != null)
	    return new ViewFeatureDetailed(getProject().getName(), getProject().getIdInternal(), getName(), getWebID(),
		    getState(), userhasvoted, getDescription(), FearsServiceImpl.getNickName(getAuthorName()), new Long(
			    getAuthor().getUser().getOid()).toString(), getCreatedTime().toString(
			    DateTimeFormat.forPattern(DateFormat.DEFAULT_FORMAT)), getProject().isProjectAdmin(voter.getUser()),
		    voters, comments);
	else
	    return new ViewFeatureDetailed(getProject().getName(), getProject().getIdInternal(), getName(), getWebID(),
		    getState(), userhasvoted, getDescription(), FearsServiceImpl.getNickName(getAuthorName()), new Long(
			    getAuthor().getUser().getOid()).toString(), getCreatedTime().toString(
			    DateTimeFormat.forPattern(DateFormat.DEFAULT_FORMAT)), false, voters, comments);
    }

    public ViewFeatureResume getResumeView(Voter user) {

	boolean userhasvoted = false;

	List<ViewVoterResume> voters = new ArrayList<ViewVoterResume>();
	for (Voter v : getVoterSet()) {
	    voters.add(new ViewVoterResume(v.getUser().getName(), FearsServiceImpl.getNickName(v.getUser().getName()), new Long(v
		    .getUser().getOid()).toString(), false));
	    if (v.equals(user))
		userhasvoted = true;
	}
	return new ViewFeatureResume(getProject().getName(), getProject().getIdInternal(), getName(), getWebID(), getState(),
		userhasvoted, getDescription(), getVoterCount(), getCommentCount(),
		FearsServiceImpl.getNickName(getAuthorName()), new Long(getAuthor().getUser().getOid()).toString(),
		getCreatedTime().toString(DateTimeFormat.forPattern(DateFormat.DEFAULT_FORMAT)));
    }

}
