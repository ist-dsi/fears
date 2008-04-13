package eu.ist.fears.client.views;

import java.io.Serializable;


public class ViewFeatureResume implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4220452812727161930L;
	
	private String _name;
	private String _description;
	private int _votes;
	private int _nComments;
	
	public  ViewFeatureResume(){
		
	}
	
	public  ViewFeatureResume(String name, String description, int votes, int nComments ) {
		_name= name;
		_description=description;
		_votes=votes;
		_nComments= nComments;
		
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
	
	
	
	
	

}
