package eu.ist.fears.server;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import eu.ist.fears.client.views.ViewComment;
import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewVoter;


public class FeatureRequest {
			
	private String _name;
	private String _description;
	private List<Voter> _voters;
	private List<Comment> _comments;
	private Voter _author;
	
	
	public FeatureRequest(String name, String description, Voter voter){
		_name=name;
		_description=description;
		_voters= new ArrayList<Voter>();
		_voters.add(voter);
		voter.addCreatedFeature(this);
		voter.addVotedFeature(this);
		_author=voter;
		_comments = new ArrayList<Comment>();
	}
	
	
	public int getVotes(){
		return _voters.size();
	}
	

	
	public String getName(){
		return _name;
	}
	
	public String getDescription(){
		return _description;
		
	}
	
	public int getNComments(){
		return  _comments.size();
		
	}

	public void vote(Voter voter){
		if(_voters.contains(voter))
			return;
		
		voter.addVotedFeature(this);
		_voters.add(voter);
	}
	
	public void removeVote(Voter voter){
		_voters.remove(voter);
	}
	
	
	public void addComment(String comment, Voter voter) {
		_comments.add(new Comment(comment));
	}

	public String getAuthor(){
		return _author.getName();
	}
	
	

	public ViewFeatureDetailed getDetailedView() {
		List<ViewComment> comments = new ArrayList<ViewComment>();
		for(Comment c : _comments ){
			comments.add(c.getView());
		}
		
		List<ViewVoter> voters = new ArrayList<ViewVoter>();
		for(Voter v : _voters ){
			voters.add(new ViewVoter(v.getName(), v.getFeaturesCreated(), null ));
		}
		
		return new ViewFeatureDetailed(_name,  _description, _author.getName(), voters , comments );
	}

}
