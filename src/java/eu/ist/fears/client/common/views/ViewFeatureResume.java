package eu.ist.fears.client.common.views;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;


public class ViewFeatureResume implements IsSerializable{
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4452593206230245849L;
	protected String _projectName;
	protected int _projectID;
	protected String _name;
	protected int _featureID;
	protected String _state;
	protected boolean _userHasVoted;
	protected String _description;
	protected int _votes;
	protected int _nComments;
	protected String _author;
	
	static public String StateNew = "Novo";
	static public String StatePlanned = "Planeado";
	static public String StateImplement = "Em implementa&ccedil;&atilde;o";
	static public String StateFinish = "Completo";
	
	
	public  ViewFeatureResume(){
		
	}
	
	public  ViewFeatureResume(String projectName, int projectID, String name, int featureID, String state, boolean userHasvoted, String description,
			int votes, int nComments, String author){
		_projectName = projectName;
		_projectID = projectID;
		_name= name;
		_featureID=featureID;
		_state=state;
		_userHasVoted = userHasvoted;
		int length= description.length();
		if(length>200){
			_description= description.substring(0, 200) + "(...)";
		}else _description= description;
		
		_votes=votes;
		_nComments= nComments;
		_author = author;
	}
	
	public String getProjectName(){
		return _projectName;
		
	}
	
	public int getProjectID(){
		return _projectID;
		
	}
	
	public String getName() {
		return _name;
	}
	
	public int getFeatureID(){
		return _featureID;
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
	
	public boolean userHasVoted(){
		return _userHasVoted;
	}
	
	public String getState(){
		return _state;
		
	}
	
	
	
}
