package eu.ist.fears.client.views;

import java.io.Serializable;
import java.util.List;

public class ViewFeatureDetailed extends ViewFeatureResume implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9130167518653434831L;
	
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
	
	public ViewFeatureDetailed(){
		
	}
	
	public  ViewFeatureDetailed(String projectName, int projectID,  String name, boolean userHasvoted, String description, String author,  List voters, List comments) {
		_projectName = projectName;
		_projectID = projectID;
		_name= name;
		_userHasVoted = userHasvoted;
		_description=description;
		_votes = voters.size();
		_nComments = comments.size();
		_comments= comments;
		_voters=voters;
		_author = author;
	}	
	
	public List getComments() {
		return _comments;
	}
	
	public List getVoters() {
		return _voters;
	}

}
