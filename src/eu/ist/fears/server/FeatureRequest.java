package eu.ist.fears.server;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


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
	
	public void vote(){
		++_votes;
	}
	
	public String getName(){
		return _name;
	}

	public void addComment(String comment) {
		_comments.add(new Comment(comment));
	}

	public String[] toStringsWithComments() {
		List<String> feat = new ArrayList<String>();
		feat.add(_name);
		feat.add(_description);
		feat.add(new Integer(_votes).toString());
		
		for(Comment c : _comments){
			feat.add(c.getComment());
		}
		String[] res= new String[1];
	
		return feat.toArray(res);
	}

}
