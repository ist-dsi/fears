package eu.ist.fears.server.domain;


import java.util.List;

import eu.ist.fears.client.common.communication.FearsService;
import eu.ist.fears.client.common.views.ViewFeatureResume;
import eu.ist.fears.client.common.views.ViewVoterDetailed;
import eu.ist.fears.client.common.views.ViewVoterResume;
import eu.ist.fears.server.FearsServiceImpl;

public class Voter extends Voter_Base {
    
    public Voter(User user){
        setUser(user);
    }

    public List<ViewFeatureResume> getViewFeaturesCreated(){
        return FearsApp.getViewFeaturesResumes(getFeaturesCreatedSet(), this);
    }
    
    public  List<ViewFeatureResume> getViewFeaturesVoted(){
        return FearsApp.getViewFeaturesResumes(getFeaturesVotedSet(), this);
    }
    
    public User getUser(){
    	return super.getUser();
    }
    
    public boolean hasRole(Role r){
    	System.out.println("Role foi chamado...");
    	if(r.getRole().equals("admin")){
    		return FearsApp.getFears().isAdmin(this.getUser());
    	}else return false;
    			
    }
	
	/*public class UserFind implements UserFinder {

		public AccessControlUser getUserByUserId(String arg0) {
			return FearsApp.getFears().getUser(arg0);
		}
	}*/
	
	public ViewVoterDetailed getView(String sessionID){
    	return new ViewVoterDetailed(getUser().getName(),FearsServiceImpl.getNickName(getUser().getName()) , sessionID, getViewFeaturesCreated(), getViewFeaturesVoted());
    	
    }
	
	public ViewVoterResume getCurrentVoterView(String sessionID){
		int votesLeft;
		if(getProject().getInitialVotes() - getVotesUsed()>=0)
			votesLeft=getProject().getInitialVotes() - getVotesUsed();
			else votesLeft=0;
			
		return new ViewVoterResume(getUser().getUsername(),FearsServiceImpl.getNickName(getUser().getUsername())  ,sessionID, votesLeft, FearsApp.getFears().isAdmin(getUser()));
		
	}
	

}
