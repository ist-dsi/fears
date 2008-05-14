package eu.ist.fears.client.views;

import java.io.Serializable;
import java.util.List;

public class ViewFeatureDetailed implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9130167518653434831L;
	
	
	private String _name;
	private String _description;
	
	/**
	   * This field is a List that must always contain ViewVoter.
	   * 
	   * @gwt.typeArgs <eu.ist.fears.client.views.ViewVoter>
	   */
	private List _voters;
	
	/**
	   * This field is a List that must always contain ViewComment.
	   * 
	   * @gwt.typeArgs <eu.ist.fears.client.views.ViewComment>
	   */
	private List _comments;
	private String _author;
	
	public ViewFeatureDetailed(){
		
	}
	
	public  ViewFeatureDetailed(String name, String description, String author,  List voters, List comments) {
		_name= name;
		_description=description;
		_comments= comments;
		_voters=voters;
		_author = author;
	}
	
	public String getName() {
		return _name;
	}
	
	public String getDescription() {
		return _description;
	}
	
	public String getAuthor() {
		return _author;
	}
	
	public int getVotes() {
		return _voters.size();
	}
	
	public List getComments() {
		return _comments;
	}
	
	public List getVoters() {
		return _voters;
	}

}
