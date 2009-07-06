package eu.ist.fears.common.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NoFeatureException extends FearsException implements IsSerializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5913900065751622271L;
	protected String _featureID;
	protected String _projID;
	
	public NoFeatureException(){}

	public NoFeatureException(String projID, String featureID) {
		_projID=projID;
		_featureID=featureID;
	}

	public String getFeatureID(){
		return _featureID;
	}
	
	public String getProjectID(){
		return _projID;
	}

}
