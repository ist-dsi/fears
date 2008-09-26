package eu.ist.fears.client.views;

import java.io.Serializable;
import java.util.List;

public class ViewFeatureDetailed extends ViewFeatureResume implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9130167518653434831L;
	
	
	private List<eu.ist.fears.client.views.ViewUserResume> _voters;
	
	
	private List<eu.ist.fears.client.views.ViewComment> _comments;
	
	public ViewFeatureDetailed(){
		
	}
	
	public  ViewFeatureDetailed(String projectName, int projectID,  String name, int featureID, String state, boolean userHasvoted, String description, String author,  List<eu.ist.fears.client.views.ViewUserResume> voters, List<eu.ist.fears.client.views.ViewComment> comments) {
		_projectName = projectName;
		_projectID = projectID;
		_name= name;
		_featureID= featureID;
		_state=state;
		_userHasVoted = userHasvoted;
		_description=description;
		_votes = voters.size();
		_nComments = comments.size();
		_comments= comments;
		_voters=voters;
		_author = author;
		
	}	
	
	public List<eu.ist.fears.client.views.ViewComment> getComments() {
		return _comments;
	}
	
	public List<eu.ist.fears.client.views.ViewUserResume> getVoters() {
		return _voters;
	}

}
