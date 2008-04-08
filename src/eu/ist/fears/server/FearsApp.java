package eu.ist.fears.server;

import java.util.Hashtable;


public class FearsApp {

	private static FearsApp _fears=new FearsApp();
	private Hashtable<String,Project> _projects = new Hashtable<String,Project>();
	
	
	private FearsApp(){
	}
	
	
	public static FearsApp getFears(){
		 return _fears;	
	}
	
	
	public Project getProject(String projectName){
		//TODO: Tirar esta martelada, de usar sempre o mesmo projecto.
		if(_projects.size()==0){
			_projects.put("default", new Project("default","projecto por defeito do Fears."));
		}
		return _projects.get("default");
	}
	
	
	public void addProject(Project p){
		_projects.put(p.getName(), p);
	}
	
	
	public void deleteProject(String name){
		_projects.remove(_projects.get(name));
		
	}
	
}
