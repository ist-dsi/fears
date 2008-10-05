package eu.ist.fears.client.common.views;

import java.io.Serializable;

import eu.ist.fears.client.common.State;

public class ViewComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8719491807239228411L;

	protected String _comment;
	protected String _author;
	protected State _newState;
	
	public ViewComment(){
		
	}
	
	public ViewComment(String c,String a, State newState ){
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
	
	public State getNewState(){
		return _newState;
		
	}
	
	

}
