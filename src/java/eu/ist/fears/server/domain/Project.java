package eu.ist.fears.server.domain;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;


public class Project extends Project_Base {
    
    public  Project() {
        super();
    }
    
    public Project(String name, String description, Voter voter){
		setName(name);
		setDescription(description);
		setAuthor(voter);
	}


	public String getName() {
		return getName();
	}

	public String getAuthorName() {
		return getAuthor().getUser();
	}

	public String getDescription() {
		return getDescription();
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
