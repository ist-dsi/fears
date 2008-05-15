package eu.ist.fears.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import eu.ist.fears.client.views.ViewFeatureResume;
import eu.ist.fears.client.views.ViewProject;


public class FearsApp {

	private static FearsApp _fears=new FearsApp();
	private Hashtable<String,Project> _projects = new Hashtable<String,Project>();
	private Hashtable<String,Voter> _voters = new Hashtable<String,Voter>();
	
	
	private FearsApp(){
	}
	
	
	public static FearsApp getFears(){
		 return _fears;	
	}
	
	
	public Project getProject(String projectName){
		return _projects.get(projectName);
	}
	
	
	public void addProject(Project p, Voter voter){
		_projects.put(p.getName(), p);
	}
	
	
	public void deleteProject(String name){
		_projects.remove(name);
	}
	
	public Voter getVoter(String name){
		if(_voters.get(name)==null){
			Voter temp = new Voter(name);
			_voters.put(name, temp);
			return temp;
		}else
			return _voters.get(name);	
	}


	public ViewProject[] getProjects() {
		Collection<Project> projects = _projects.values();
		ViewProject[] res = new ViewProject[_projects.size()];
		
		int i=0;
		for(Project p : projects){
			res[i] = new ViewProject(p.getName(), p.getDescription(), p.getNFeatures(), p.getAuthor() );
			i++;
		}
		
		return res;
	}
	
	public static List<ViewFeatureResume> getViewFeaturesResumes(Collection<FeatureRequest> features){
		if( features.size()==0)
			return null;

		ArrayList<ViewFeatureResume> res = new ArrayList<ViewFeatureResume>();


		for(FeatureRequest f : features ){
			res.add(new ViewFeatureResume(f.getName(), f.getDescription(),
					f.getVotes(), f.getNComments(), f.getAuthor() ));
		}
		
		return res;
	}
	
}
