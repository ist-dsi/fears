package eu.ist.fears.client.common.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;


public class NoProjectException extends FearsException implements IsSerializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6362589945948932611L;
	
	protected String _projID;
	
	public NoProjectException(){}
	
	public NoProjectException(String projectID) {
		_projID=projectID;
	}

	public String getProjectID(){
		return _projID;
	}
	
}
