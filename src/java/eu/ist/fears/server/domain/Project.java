package eu.ist.fears.server.domain;

import java.util.Collection;


public class Project extends Project_Base {
    
	
	
    public Project(String name, int projNumber, String description, Voter voter){
        setName(name);
        setWebID(projNumber);
        setDescription(description);
        setAuthor(voter);
    }


    public String getAuthorName() {
        return getAuthor().getUser();
    }

    public FeatureRequest getFeature(String nome){
        for(FeatureRequest f : getFeatureRequestSet()){
            if(f.getName().equals(nome))	
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
    
    
    public  void addFeature(FeatureRequest s){
        addFeatureRequest(s);
    }
}
