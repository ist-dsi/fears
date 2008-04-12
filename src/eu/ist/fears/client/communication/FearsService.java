package eu.ist.fears.client.communication;

import com.google.gwt.user.client.rpc.RemoteService; 

import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewFeatureResume;


public interface FearsService extends RemoteService  {
	
	public void vote(String feature);
	public void addFeature(String name, String description);
	public ViewFeatureResume[] getFeatures();
	public ViewFeatureDetailed getFeature(String name);
	public ViewFeatureDetailed addComment(String featureName, String comment);
		
}
