package eu.ist.fears.server.domain;


import java.util.List;

import eu.ist.fears.client.views.ViewFeatureResume;

public class Voter extends Voter_Base {
    
    public  Voter() {
        super();
    }
 
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

	public List<ViewFeatureResume> getViewFeaturesCreated(){
		return FearsApp.getViewFeaturesResumes(getFeaturesCreatedSet());
	}

	public  List<ViewFeatureResume> getViewFeaturesVoted(){
		return FearsApp.getViewFeaturesResumes(getFeaturesVotedSet());
	}

	public String getName(){
		return getUser();
	}
    
}
