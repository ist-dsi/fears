package eu.ist.fears.client.views;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;


public class ViewUserDetailed extends ViewUserResume implements IsSerializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5835833915085305137L;
	List<ViewFeatureResume> _created;
	List<ViewFeatureResume> _voted;
	
	private ViewUserDetailed(){
		
	}
	
	public ViewUserDetailed(String name, String sessionID, List<ViewFeatureResume> created, List<ViewFeatureResume> voted){
		super(name, sessionID);
		_created=created;
		_voted=voted;
	}


	public List<ViewFeatureResume> getCreatedFeatures() {
		return _created;
	}


	public List<ViewFeatureResume> getVotedFeatures(){
		return _voted;
	}

}
