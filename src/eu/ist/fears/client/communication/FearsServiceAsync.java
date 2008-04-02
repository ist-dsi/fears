package eu.ist.fears.client.communication;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FearsServiceAsync {
	
	public void vote(String feature,  AsyncCallback callback);
	public void addFeature(String name, String description,  AsyncCallback callback);
	public void getFeatures(AsyncCallback callback);
	public void getFeature(String name, AsyncCallback callback);
		
}
