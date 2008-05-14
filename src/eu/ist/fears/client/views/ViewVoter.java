package eu.ist.fears.client.views;

import java.io.Serializable;
import java.util.List;


public class ViewVoter implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5527088349945022648L;
	
	private String _user;
	
	/**
	   * This field is a List that must always contain ViewFeatureResume.
	   * 
	   * @gwt.typeArgs <eu.ist.fears.client.views.ViewFeatureResume>
	   */
	private List _features;
	private String _sessionID;

	public ViewVoter(){}
	
	public ViewVoter(String name, List features, String sessionID){
		_user=name;
		_features = features;
		_sessionID = sessionID;
	}
	
	
	public String getName(){
		return _user;
	}
	
	public String getSessionID(){
		return _sessionID;
	}
	
	public List getFeatures(){
		return _features;
	}
	

}
