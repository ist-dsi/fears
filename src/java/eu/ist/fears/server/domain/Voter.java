package eu.ist.fears.server.domain;


import java.util.List;

import pt.ist.dmapl.AccessControlRole;
import pt.ist.dmapl.AccessControlUser;
import pt.ist.dmapl.UserFinder;

import eu.ist.fears.client.views.ViewFeatureResume;

public class Voter extends Voter_Base implements AccessControlUser {
    
    public Voter(String user){
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

    public List<ViewFeatureResume> getViewFeaturesCreated(Voter user){
        return FearsApp.getViewFeaturesResumes(getFeaturesCreatedSet(), user);
    }
    
    public  List<ViewFeatureResume> getViewFeaturesVoted(Voter user){
        return FearsApp.getViewFeaturesResumes(getFeaturesVotedSet(), user);
    }
    
    public String getName(){
        return getUser();
    }
    
    public boolean hasRole(Role r){
    	System.out.println("Role foi chamado...");
    	if(r.getRole().equals("admin")){
    		return FearsApp.getFears().isAdmin(this);
    	}else return false;
    			
    }

	public boolean hasRole(AccessControlRole arg0) {
		return hasRole(arg0);
	}
	
	public class UserFind implements UserFinder {

		public AccessControlUser getUserByUserId(String arg0) {
			return FearsApp.getFears().getVoter(arg0);
		}
	}
	
}
