package eu.ist.fears.server.domain;


import java.util.List;

import eu.ist.fears.client.views.ViewFeatureResume;

public class Voter extends Voter_Base {
    
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
    	if(r.getRole().equals("admin")){
    		return FearsApp.getFears().isAdmin(this);
    	}else return false;
    			
    }
    
}
