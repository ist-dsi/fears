package eu.ist.fears.client.views;

import java.io.Serializable;

public class ViewComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8719491807239228411L;

	private String _comment;
	
	public ViewComment(){
		
	}
	
	public ViewComment(String c ){
		_comment =c;
	}
	
	public String getComment(){
		return _comment;
	}
	
	

}
