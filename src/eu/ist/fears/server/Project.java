package eu.ist.fears.server;


import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import eu.ist.fears.client.views.ViewFeatureResume;


public class Project {

	private String _name;
	private String _description;
	private Map<String,FeatureRequest> _features;
	private Voter _author;


	public Project(String name, String description, Voter voter){
		_name = name;
		_description = description;
		_features = new Hashtable<String,FeatureRequest>();
		_author = voter;
	}


	public String getName() {
		return _name;
	}

	public String getAuthor() {
		return _author.getName();
	}

	public String getDescription() {
		return _description;
	}


	public FeatureRequest getFeature(String nome){
		return _features.get(nome);
	}
	
	public int getNFeatures(){
		return _features.size();
	}

	public Collection<FeatureRequest> getFeatures(){
		return _features.values();
		
	}


	public  void addFeature(FeatureRequest s){
		if(_features.get(s.getName())==null)
			_features.put(s.getName(), s);
	}

}
