package eu.ist.fears.server;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import eu.ist.fears.client.views.ViewComment;
import eu.ist.fears.client.views.ViewFeatureDetailed;


public class FeatureRequest {
			
	private String _name;
	private String _description;
	private int _votes;
	private List<Comment> _comments;
	
	
	public FeatureRequest(String name, String description){
		_name=name;
		_description=description;
		_votes=1;
		_comments = new ArrayList<Comment>();
	}
	
	
	public String[] toStrings(){
		return new String[] {_name, _description,new Integer(_votes).toString() };	
	}
	
	
	public int getVotes(){
		return _votes;
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

	public void vote(){
		++_votes;
	}
	
	public void addComment(String comment) {
		_comments.add(new Comment(comment));
	}


	public ViewFeatureDetailed getDetailedView() {
		List<ViewComment> comments = new ArrayList<ViewComment>();
		for(Comment c : _comments ){
			comments.add(c.getView());
		}
		
		return new ViewFeatureDetailed(_name, _description, _votes, comments );
	}

}
