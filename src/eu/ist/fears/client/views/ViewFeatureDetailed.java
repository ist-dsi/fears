package eu.ist.fears.client.views;

import java.io.Serializable;
import java.util.List;

public class ViewFeatureDetailed implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9130167518653434831L;
	
	
	String _name;
	private String _description;
	private int _votes;
	
	/**
	   * This field is a List that must always contain ViewComment.
	   * 
	   * @gwt.typeArgs <eu.ist.fears.client.views.ViewComment>
	   */
	private List _comments;
	
	public ViewFeatureDetailed(){
		
	}
	
	public  ViewFeatureDetailed(String name, String description, int votes, List comments ) {
		_name= name;
		_description=description;
		_votes=votes;
		_comments= comments;
	}
	
	public String getName() {
		return _name;
	}
	public String getDescription() {
		return _description;
	}
	public int getVotes() {
		return _votes;
	}
	public List getComments() {
		return _comments;
	}
	

}
