package eu.ist.fears.client.views;

import java.io.Serializable;

public class ViewComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8719491807239228411L;

	private String _comment;
	private String _author;
	
	public ViewComment(){
		
	}
	
	public ViewComment(String c,String a ){
		_comment=c;
		_author=a;
	}
	
	public String getComment(){
		return _comment;
	}
	
	public String getAuthor(){
		return _author;
		
	}
	
	

}
