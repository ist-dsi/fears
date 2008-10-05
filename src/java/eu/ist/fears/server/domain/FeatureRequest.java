package eu.ist.fears.server.domain;

import java.util.ArrayList;
import java.util.List;

import eu.ist.fears.client.common.State;
import eu.ist.fears.client.common.views.ViewComment;
import eu.ist.fears.client.common.views.ViewFeatureDetailed;
import eu.ist.fears.client.common.views.ViewFeatureResume;
import eu.ist.fears.client.common.views.ViewVoterResume;


public class FeatureRequest extends FeatureRequest_Base {
    
    public  FeatureRequest() {
        super();
    }
    
    public FeatureRequest(String name, String description, Voter voter){
    	setName(name);
    	setDescription(description);
    	addVoter(voter);
    	setAuthor(voter);
    	setState(State.Novo);
    	voter.setVotesLeft(voter.getVotesLeft()-1);
	}
	
	
	public int getVotes(){
		return getVoterCount();
	}
	

	
	public int getNComments(){
		return  getCommentCount();
		
	}

	public void vote(Voter voter){
		if( hasVoter(voter))
			return;
		
		voter.setVotesLeft(voter.getVotesLeft()-1);
		addVoter(voter);
	}
	
	public void removeVote(Voter voter){
		if(!hasVoter(voter))
			return;
		
		voter.setVotesLeft(voter.getVotesLeft()+1);
		removeVoter(voter);
	}
	
	
	public void addComment(String comment, Voter voter, State newState) {
		addComment(new Comment(comment, voter, newState));
		
	}

	public String getAuthorName(){
		return getAuthor().getUser().getName();
	}
	
	

	public ViewFeatureDetailed getDetailedView(Voter user) {
		List<ViewComment> comments = new ArrayList<ViewComment>();
		for(Comment c : getCommentSet() ){
			comments.add(c.getView());
		}
		
		boolean userhasvoted=false;
		
		List<ViewVoterResume> voters = new ArrayList<ViewVoterResume>();
		for(Voter v : getVoterSet() ){
			voters.add(new ViewVoterResume(v.getUser().getName(),  null ));
			if(v.equals(user))
				userhasvoted=true;
		}
		return new ViewFeatureDetailed(getProject().getName(), getProject().getIdInternal(), getName(), getWebID(), getState() ,userhasvoted ,  getDescription(), getAuthorName(), voters , comments );
	}
	
	public ViewFeatureResume getResumeView(Voter user) {
		
		
		boolean userhasvoted=false;
		
		List<ViewVoterResume> voters = new ArrayList<ViewVoterResume>();
		for(Voter v : getVoterSet() ){
			voters.add(new ViewVoterResume(v.getUser().getName(),  null ));
			if(v.equals(user))
				userhasvoted=true;
		}
		return new ViewFeatureResume(getProject().getName(), getProject().getIdInternal(), getName(), getWebID(), getState(), userhasvoted ,  getDescription(), getVoterCount(), getCommentCount(), getAuthorName());
	}

    
}
