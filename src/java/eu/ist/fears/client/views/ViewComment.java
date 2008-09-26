package eu.ist.fears.client.views;

import java.io.Serializable;

public class ViewComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8719491807239228411L;

	protected String _comment;
	protected String _author;
	protected String _newState;
	
	public ViewComment(){
		
	}
	
	public ViewComment(String c,String a, String newState ){
		_comment=c;
		_author=a;
		_newState=newState;
	}
	
	public String getComment(){
		return _comment;
	}
	
	public String getAuthor(){
		return _author;
		
	}
	
	public String getNewState(){
		return _newState;
		
	}
	
	

}
