package eu.ist.fears.client.views;

import java.io.Serializable;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;


public class ViewFeatureResume implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4220452812727161930L;
	
	protected String _projectName;
	protected String _name;
	protected String _description;
	protected int _votes;
	protected int _nComments;
	protected String _author;
	
	public  ViewFeatureResume(){
		
	}
	
	public  ViewFeatureResume(String projectName, String name, String description,
			int votes, int nComments, String author){
		_projectName = projectName;
		_name= name;
		_description=description;
		_votes=votes;
		_nComments= nComments;
		_author = author;
	}
	
	public String getProjectName(){
		return _projectName;
		
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
	
	public int getNComments() {
		return _nComments;
	}
	
	
	public String getAuthor() {
		return _author;
	}
	
	
}
