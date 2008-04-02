package eu.ist.fears.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import eu.ist.fears.client.communication.FearsService;



public class FearsServiceImpl extends RemoteServiceServlet implements FearsService {

	/**
	 * 
	 */	
	private static final long serialVersionUID = -9186875057311859285L;

	public String[] vote(String nome){
		FeatureRequest s=FeatureRequest.getFeature(nome);
		if(s!=null){
			s.vote();
			return s.toStrings();
		}
		else{
			throw new RuntimeException("Nao existe essa sugestao: " + nome);
		}
	}
	
	public void addFeature(String name, String description){
		FeatureRequest.addFeature(new FeatureRequest(name, description));
	}
	
	public String[][] getFeatures(){
		return FeatureRequest.getFeatures();
	}
	
	public String[] getFeature(String name){
		return FeatureRequest.getFeature(name).toStrings();
	}
	
}