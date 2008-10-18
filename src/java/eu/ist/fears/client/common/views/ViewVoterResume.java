package eu.ist.fears.client.common.views;

import java.io.Serializable;



public class ViewVoterResume implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5527088349945022648L;
	
	protected String _user;
	protected String _sessionID;
	protected int _votesLeft;
	protected boolean _isAdmin;

	public ViewVoterResume(){}
	
	public ViewVoterResume(String name, String sessionID, boolean isAdmin){
		_user=name;
		_sessionID = sessionID;
		_isAdmin=isAdmin;
	}
	
	public ViewVoterResume(String name, String sessionID, int votesLeft, boolean isAdmin){
		_user=name;
		_sessionID = sessionID;
		_votesLeft=votesLeft;
		_isAdmin= isAdmin;
	}
	
	
	public String getName(){
		return _user;
	}
	
	public String getSessionID(){
		return _sessionID;
	}
	
	public int getVotesLeft(){
		return _votesLeft;
	}
	
	public boolean isAdmin(){
		return _isAdmin;
	}
	
	public void setName(String user){
		_user=user;
	}

}
