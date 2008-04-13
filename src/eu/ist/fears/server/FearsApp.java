package eu.ist.fears.server;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import eu.ist.fears.client.views.ViewProject;


public class FearsApp {

	private static FearsApp _fears=new FearsApp();
	private Hashtable<String,Project> _projects = new Hashtable<String,Project>();
	
	
	private FearsApp(){
	}
	
	
	public static FearsApp getFears(){
		 return _fears;	
	}
	
	
	public Project getProject(String projectName){
		return _projects.get(projectName);
	}
	
	
	public void addProject(Project p){
		_projects.put(p.getName(), p);
	}
	
	
	public void deleteProject(String name){
		_projects.remove(name);
	}


	public ViewProject[] getProjects() {
		Collection<Project> projects = _projects.values();
		ViewProject[] res = new ViewProject[_projects.size()];
		
		int i=0;
		for(Project p : projects){
			res[i] = new ViewProject(p.getName(), p.getDescription(), p.getNFeatures() );
			i++;
		}
		
		return res;
	}
	
}
