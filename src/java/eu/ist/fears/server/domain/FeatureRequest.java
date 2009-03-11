package eu.ist.fears.server.domain;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import eu.ist.fears.client.common.DateFormat;
import eu.ist.fears.client.common.State;
import eu.ist.fears.client.common.exceptions.ActionNotExecuted;
import eu.ist.fears.client.common.exceptions.FearsException;
import eu.ist.fears.client.common.views.ViewComment;
import eu.ist.fears.client.common.views.ViewFeatureDetailed;
import eu.ist.fears.client.common.views.ViewFeatureResume;
import eu.ist.fears.client.common.views.ViewVoterResume;


public class FeatureRequest extends FeatureRequest_Base {

	public  FeatureRequest() {
		super();
	}

	public FeatureRequest(String name, String description, Voter voter, int projectInitialVotes){
		setName(name);
		//Remove all \r inserted by IE browser.
		setDescription(description.replaceAll("\r",""));

		setAuthor(voter);
		setState(State.Novo);
		if(voter.getVotesUsed() < projectInitialVotes){
			voter.setVotesUsed(voter.getVotesUsed()+1);
			addVoter(voter);
		}
		
		setCreatedTime(new DateTime());
	}


	public int getVotes(){
		return getVoterCount();
	}



	public int getNComments(){
		return  getCommentCount();

	}

	public void vote(Voter voter)throws FearsException{
		if( hasVoter(voter)){
			throw new ActionNotExecuted("Utilizador ja votou.");
		}

		if(voter.getVotesUsed() >= getProject().getInitialVotes()){
			throw new ActionNotExecuted("Utilizador nao tem votos.");
		}

		voter.setVotesUsed(voter.getVotesUsed()+1);
		addVoter(voter);
	}

	public void removeVote(Voter voter) throws FearsException{
		if(!hasVoter(voter))
			throw new ActionNotExecuted("Utilizador nao tem voto.");

		voter.setVotesUsed(voter.getVotesUsed()-1);
		removeVoter(voter);
	}


	public void addComment(String comment, Voter voter, State newState) {
		addComment(new Comment(comment, voter, newState, getState()));

	}

	public String getAuthorName(){
		return getAuthor().getUser().getName();
	}



	public ViewFeatureDetailed getDetailedView(Voter voter) {
		List<ViewComment> comments = new ArrayList<ViewComment>();
		for(Comment c : getCommentSet() ){
			comments.add(c.getView());
		}

		boolean userhasvoted=false;

		List<ViewVoterResume> voters = new ArrayList<ViewVoterResume>();
		for(Voter v : getVoterSet() ){
			voters.add(new ViewVoterResume(v.getUser().getName(),  null, false ));
			if(v.equals(voter))
				userhasvoted=true;
		}
		
		if(voter!=null)
		return new ViewFeatureDetailed(getProject().getName(), getProject().getIdInternal(), getName(), getWebID(), getState() ,userhasvoted ,  getDescription(), getAuthorName(), getCreatedTime().toString(DateTimeFormat.forPattern(DateFormat.DEFAULT_FORMAT)), getProject().isProjectAdmin(voter.getUser()), voters , comments );
		else return new ViewFeatureDetailed(getProject().getName(), getProject().getIdInternal(), getName(), getWebID(), getState() ,userhasvoted ,  getDescription(), getAuthorName(), getCreatedTime().toString(DateTimeFormat.forPattern(DateFormat.DEFAULT_FORMAT)), false, voters , comments );
	}

	public ViewFeatureResume getResumeView(Voter user) {


		boolean userhasvoted=false;

		List<ViewVoterResume> voters = new ArrayList<ViewVoterResume>();
		for(Voter v : getVoterSet() ){
			voters.add(new ViewVoterResume(v.getUser().getName(),  null, false ));
			if(v.equals(user))
				userhasvoted=true;
		}
		return new ViewFeatureResume(getProject().getName(), getProject().getIdInternal(), getName(), getWebID(), getState(), userhasvoted ,  getDescription(), getVoterCount(), getCommentCount(), getAuthorName(), getCreatedTime().toString(DateTimeFormat.forPattern(DateFormat.DEFAULT_FORMAT)) );
	}


}
