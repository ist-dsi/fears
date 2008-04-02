package eu.ist.fears.server;


import java.util.Hashtable;
import java.util.Iterator;


public class FeatureRequest {
	
	
		
	private static Hashtable<String,FeatureRequest> _features;
	private String _name;
	private String _description;
	private int _votes;
	
	public static void addFeature(FeatureRequest s){
		if(_features==null){
			_features = new Hashtable<String,FeatureRequest>();
		}
		_features.put(s.getName(), s);
	}
	
	public static FeatureRequest getFeature(String nome){
		if(_features==null){
			return null;
		}
		
		return _features.get(nome);
	}
	
	
	public static String[][] getFeatures(){
		if( _features==null || _features.size()==0)
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
	
	public String[] toStrings(){
		return new String[] {_name, _description,new Integer(_votes).toString() };	
	}
	
	FeatureRequest(String name, String description){
		_name=name;
		_description=description;
		_votes=1;
	}
	
	public int getVotes(){
		return _votes;
	}
	
	public void vote(){
		++_votes;
	}
	
	public String getName(){
		return _name;
	}

}
