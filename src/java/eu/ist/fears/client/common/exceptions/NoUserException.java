package eu.ist.fears.client.common.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NoUserException extends FearsException implements IsSerializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5219981592415619789L;
	
	protected String _projectID;
	protected String _userID;
	
	public NoUserException(){}
	
	public NoUserException(String projectID, String userID) {
		initCause(this);
		_userID=userID;
		_projectID=projectID;
	}

	public String getProjectID(){
		return _projectID;
	}
	
	public String getUserID(){
		return _userID;
	}
	

}
