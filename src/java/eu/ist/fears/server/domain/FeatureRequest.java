package eu.ist.fears.server.domain;

import java.util.ArrayList;
import java.util.List;

import eu.ist.fears.client.views.ViewComment;
import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewFeatureResume;
import eu.ist.fears.client.views.ViewUser;


public class FeatureRequest extends FeatureRequest_Base {
    
    public  FeatureRequest() {
        super();
    }
    
    public FeatureRequest(String name, String description, Voter voter){
    	setName(name);
    	setDescription(description);
    	addVoter(voter);
    	setAuthor(voter);
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
		
		addVoter(voter);
	}
	
	public void removeVote(Voter voter){
		removeVoter(voter);
	}
	
	
	public void addComment(String comment, Voter voter) {
		addComment(new Comment(comment, voter));
		
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
		
		List<ViewUser> voters = new ArrayList<ViewUser>();
		for(Voter v : getVoterSet() ){
			voters.add(new ViewUser(v.getUser().getName(),  null ));
			if(v.equals(user))
				userhasvoted=true;
		}
		return new ViewFeatureDetailed(getProject().getName(), getProject().getIdInternal(), getName(), getWebID(), userhasvoted ,  getDescription(), getAuthorName(), voters , comments );
	}
	
	public ViewFeatureResume getResumeView(Voter user) {
		
		
		boolean userhasvoted=false;
		
		List<ViewUser> voters = new ArrayList<ViewUser>();
		for(Voter v : getVoterSet() ){
			voters.add(new ViewUser(v.getUser().getName(),  null ));
			if(v.equals(user))
				userhasvoted=true;
		}
		return new ViewFeatureResume(getProject().getName(), getProject().getIdInternal(), getName(), getWebID(), userhasvoted ,  getDescription(), getVoterCount(), getCommentCount(), getAuthorName());
	}

    
}
