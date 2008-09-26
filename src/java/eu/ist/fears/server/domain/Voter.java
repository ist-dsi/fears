package eu.ist.fears.server.domain;


import java.util.List;

import pt.ist.dmapl.AccessControlRole;
import pt.ist.dmapl.AccessControlUser;
import pt.ist.dmapl.UserFinder;

import eu.ist.fears.client.views.ViewFeatureResume;
import eu.ist.fears.client.views.ViewUserDetailed;

public class Voter extends Voter_Base implements AccessControlUser {
    
    public Voter(User user){
        setUser(user);
    }

    public void addCreatedFeature(FeatureRequest f){
        if(! getFeaturesCreated().contains(f))
            addFeaturesCreated(f);
    }

    public void addVotedFeature(FeatureRequest f){
        if(!getFeaturesVoted().contains(f))
            addFeaturesVoted(f);
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

	public boolean hasRole(AccessControlRole arg0) {
		return hasRole(arg0);
	}
	
	/*public class UserFind implements UserFinder {

		public AccessControlUser getUserByUserId(String arg0) {
			return FearsApp.getFears().getUser(arg0);
		}
	}*/
	
	public ViewUserDetailed getView(String sessionID){
    	return new ViewUserDetailed(getUser().getName(), sessionID, getViewFeaturesCreated(), getViewFeaturesVoted());
    	
    	
    }
	
}
