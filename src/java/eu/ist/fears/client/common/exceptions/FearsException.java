package eu.ist.fears.client.common.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FearsException extends Exception implements IsSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1218590696230205271L;
	String _error;

	public FearsException(){}

	public FearsException(String error){
		_error=error;	
	}

	public String getError(){
		return _error;
	}



}
