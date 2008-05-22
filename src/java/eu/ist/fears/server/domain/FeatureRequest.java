package eu.ist.fears.server.domain;

import java.util.ArrayList;
import java.util.List;

import eu.ist.fears.client.views.ViewComment;
import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewVoter;


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
	

	
	public String getName(){
		return getName();
	}
	
	public String getDescription(){
		return getDescription();
		
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
		addComment(new Comment(comment));
		
	}

	public String getAuthorName(){
		return getAuthor().getUser();
	}
	
	

	public ViewFeatureDetailed getDetailedView() {
		List<ViewComment> comments = new ArrayList<ViewComment>();
		for(Comment c : getCommentSet() ){
			comments.add(c.getView());
		}
		
		List<ViewVoter> voters = new ArrayList<ViewVoter>();
		for(Voter v : getVoterSet() ){
			voters.add(new ViewVoter(v.getUser(), v.getFeaturesCreated(), null ));
		}
		
		return new ViewFeatureDetailed(getName(),  getDescription(), getAuthorName(), voters , comments );
	}

    
}
