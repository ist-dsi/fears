package eu.ist.fears.server;

import java.util.ArrayList;
import java.util.List;

import eu.ist.fears.client.views.ViewFeatureResume;
import eu.ist.fears.client.views.ViewVoter;

public class Voter {

	private String _user;
	private List<FeatureRequest> _featuresCreated;
	private List<FeatureRequest> _featuresVoted;

	public Voter(String user){
		_user=user;
		_featuresCreated = new ArrayList<FeatureRequest>();
		_featuresVoted = new ArrayList<FeatureRequest>();
	}

	public void addCreatedFeature(FeatureRequest f){
		_featuresCreated.add(f);
	}

	public void addVotedFeature(FeatureRequest f){
		_featuresVoted.add(f);
	}

	public List<ViewFeatureResume> getFeaturesCreated(){
		return FearsApp.getViewFeaturesResumes(_featuresCreated);
	}

	public  List<ViewFeatureResume> getFeaturesVoted(){
		return FearsApp.getViewFeaturesResumes(_featuresVoted);
	}

	public String getName(){
		return _user;
	}

}
