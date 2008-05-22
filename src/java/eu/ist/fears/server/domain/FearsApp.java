package eu.ist.fears.server.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eu.ist.fears.client.views.ViewFeatureResume;
import eu.ist.fears.client.views.ViewProject;
import pt.ist.fenixframework.pstm.Transaction;

public class FearsApp extends FearsApp_Base {
    
    public FearsApp() {
        super();
    }

    public static FearsApp getFears(){
        return (FearsApp)Transaction.getDomainObject(FearsApp.class.getName(), 1);
    }

    public Project getProject(String projectName){
    	for(Project p :  getProjectSet()){
    		if(p.getName().equals(projectName))
    			return p;
    	}
    	
    	return null;
	}
	
	
	public void addProject(Project p, Voter voter){
		addProject(p);
	}
	
	
	public void deleteProject(String name){
		//removeProject(name);
	}
	
	public Voter getVoter(String name){
	/*	if(_voters.get(name)==null){
			Voter temp = new Voter(name);
			_voters.put(name, temp);
			return temp;
		}else
			return _voters.get(name);	*/
		return null;
	}


	public ViewProject[] getProjects() {
		ViewProject[] res = new ViewProject[getProjectCount()];
		
		int i=0;
		for(Project p : getProjectSet()){
			res[i] = new ViewProject(p.getName(), p.getDescription(), p.getNFeatures(), p.getAuthor().getUser() );
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
					f.getVotes(), f.getNComments(), f.getAuthor().getUser() ));
		}
		
		return res;
	}


}
