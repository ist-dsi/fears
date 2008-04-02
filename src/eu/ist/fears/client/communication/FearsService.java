package eu.ist.fears.client.communication;

import com.google.gwt.user.client.rpc.RemoteService; 


public interface FearsService extends RemoteService  {
	
	public String[] vote(String feature);
	public void addFeature(String name, String description);
	public String[][] getFeatures();
	public String[] getFeature(String name);
		
}
