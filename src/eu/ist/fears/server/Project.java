package eu.ist.fears.server;


import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;


public class Project {

	private String _name;
	private String _description;
	private Map<String,FeatureRequest> _features;

	
	public Project(String name, String description){
		_name = name;
		_description = description;
		_features = new Hashtable<String,FeatureRequest>();
	}

	
	public String getName() {
		return _name;
	}


	public String getDescription() {
		return _description;
	}
	
	
	public  FeatureRequest getFeature(String nome){
		return _features.get(nome);
	}
	
	public String[][] getFeatures(){
		if( _features.size()==0)
			return null;
		
		String[][] res = new String[_features.size()][3];
		Iterator<FeatureRequest>  i = _features.values().iterator();
		int j=0;
		while(i.hasNext()){
			res[j]=i.next().toStrings();
			j++;
		}
		return res;
	}
	

	public  void addFeature(FeatureRequest s){
		if(_features.get(s.getName())==null)
			_features.put(s.getName(), s);
	}

}
