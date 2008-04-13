package eu.ist.fears.client.communication;

import com.google.gwt.user.client.rpc.RemoteService; 

import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewFeatureResume;
import eu.ist.fears.client.views.ViewProject;


public interface FearsService extends RemoteService  {
	
	public void vote(String projectName, String feature);
	public void addFeature(String projectName, String name, String description);
	public ViewFeatureResume[] getFeatures(String projectName);
	public ViewFeatureDetailed getFeature(String projectName, String featuremName);
	public ViewFeatureDetailed addComment(String projectName, String featureName, String comment);
	
	public void addProject(String name, String description);
	public ViewProject[] getProjects();
	public void deleteProject(String name);
		
}
