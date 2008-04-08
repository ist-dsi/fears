package eu.ist.fears.server;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import eu.ist.fears.client.communication.FearsService;



public class FearsServiceImpl extends RemoteServiceServlet implements FearsService {

	/**
	 * 
	 */	
	private static final long serialVersionUID = -9186875057311859285L;

	public String[] vote(String nome){
		FeatureRequest s=FearsApp.getFears().getProject("qualquer um").getFeature(nome);
		if(s!=null){
			s.vote();
			return s.toStrings();
		}
		else{
			throw new RuntimeException("Nao existe essa sugestao: " + nome);
		}
	}
	
	public void addFeature(String name, String description){
		FearsApp.getFears().getProject("qualquer um").addFeature(new FeatureRequest(name, description));
	}
	
	public String[][] getFeatures(){
		return FearsApp.getFears().getProject("qualquer um").getFeatures();
	}
	
	public String[] getFeature(String name){
		if(FearsApp.getFears().getProject("qualquer um").getFeature(name)!=null)
		return FearsApp.getFears().getProject("qualquer um").getFeature(name).toStringsWithComments();
		else return null;
	}

	public String[] addComment(String featureName, String comment) {
		if(FearsApp.getFears().getProject("qualquer um").getFeature(featureName)!=null){
			FearsApp.getFears().getProject("qualquer um").getFeature(featureName).addComment(comment);
			return FearsApp.getFears().getProject("qualquer um").getFeature(featureName).toStringsWithComments();
			
		}else return null;
	}
	
}