package eu.ist.fears.server.domain;

import java.util.Collection;


public class Project extends Project_Base {
    
	
	
    public Project(String name, int projNumber, String description, Voter voter){
        setName(name);
        setWebID(projNumber);
        setDescription(description);
        setAuthor(voter);
        setFeaturesIncrementID(0);
    }


    public String getAuthorName() {
        return getAuthor().getUser();
    }

    public FeatureRequest getFeature(String featureID){
    	int featID;
		try{
			featID= new Integer(featureID).intValue();
		}catch(Throwable t){
			System.out.println("ID:" + featureID);
			throw new RuntimeException("Nao existe esse projecto: " + featureID);
		}
    	
        for(FeatureRequest f : getFeatureRequestSet()){
            if(f.getWebID()==featID)	
                return f;
        }
        return null;
    }
	
    public int getNFeatures(){
        return getFeatureRequestCount();
    }
    
    public Collection<FeatureRequest> getFeatures(){
        return getFeatureRequestSet();
    }
    
    
    public  void addFeature(FeatureRequest f){
        addFeatureRequest(f);
        setFeaturesIncrementID(getFeaturesIncrementID()+1);
        f.setWebID(getFeaturesIncrementID());
    }
}
