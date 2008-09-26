package eu.ist.fears.client.views;

import java.io.Serializable;



public class ViewUserResume implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5527088349945022648L;
	
	private String _user;
	private String _sessionID;

	public ViewUserResume(){}
	
	public ViewUserResume(String name, String sessionID){
		_user=name;
		_sessionID = sessionID;
	}
	
	
	public String getName(){
		return _user;
	}
	
	public String getSessionID(){
		return _sessionID;
	}
	
	

}
