package eu.ist.fears.common.views;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;


public class ViewVoterDetailed extends ViewVoterResume implements IsSerializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5835833915085305137L;
	List<ViewFeatureResume> _created;
	List<ViewFeatureResume> _voted;
	String _projectID;
	String _projectName;
	String _oid;
	
	private ViewVoterDetailed(){
		
	}
	
	public ViewVoterDetailed(String name, String nick, String oid, List<ViewFeatureResume> created, List<ViewFeatureResume> voted, String projID, String projName){
		super(name, nick,oid, false);
		_created=created;
		_voted=voted;
		_projectID=projID;
		_projectName=projName;
	}


	public List<ViewFeatureResume> getCreatedFeatures() {
		return _created;
	}


	public List<ViewFeatureResume> getVotedFeatures(){
		return _voted;
	}
	
	public String getProjectID(){
	    return _projectID;
	}
	
	public String getProjectName(){
        return _projectName;
    }

}
