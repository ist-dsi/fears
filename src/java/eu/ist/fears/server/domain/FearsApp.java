package eu.ist.fears.server.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import eu.ist.fears.client.views.ViewFeatureResume;
import eu.ist.fears.client.views.ViewProject;
import pt.ist.fenixframework.pstm.Transaction;

public class FearsApp extends FearsApp_Base {


	public static FearsApp getFears(){
		return (FearsApp)Transaction.getDomainObject(FearsApp.class.getName(), 1);
	}

	public Project getProject(String projectID){
		int projID;
		try{
			projID= new Integer(projectID).intValue();
		}catch(Throwable t){
			System.out.println("ID:" + projectID);
			throw new RuntimeException("Nao existe esse projecto: " + projectID);
		}
		
		for(Project p :  getProjectSet()){
			if(p.getWebID()==(projID))
				return p;
		}

		return null;
	}


	public void addProject(Project p, Voter voter){
		addProject(p);
	}


	public void deleteProject(String name){
		for(Project p : getProjectSet()){
			if(p.getName().equals(name))
				removeProject(p);			
		}				
	}		


	public Voter getVoter(String name){
		for(Voter v : getVoterSet()){
			if(v.getName().equals(name))
				return v;					
		}
		
		Voter temp = new Voter(name);
		addVoter(temp);
		return temp;
	
	}


	public ViewProject[] getProjects() {
		ViewProject[] res = new ViewProject[getProjectCount()];

		int i=0;
		for(Project p : getProjectSet()){
			res[i] = new ViewProject(p.getName(), p.getWebID(),  p.getDescription(), p.getNFeatures(), p.getAuthor().getUser() );
			i++;
		}

		return res;
	}

	public static List<ViewFeatureResume> getViewFeaturesResumes(Collection<FeatureRequest> features, Voter user){
		if( features.size()==0)
			return null;

		ArrayList<ViewFeatureResume> res = new ArrayList<ViewFeatureResume>();

		boolean userHasvoted;
		
		for(FeatureRequest f : features ){
			userHasvoted=false;
			for(Voter v : f.getVoterSet()){
				if(v.equals(user))
					userHasvoted=true;
			}
			res.add(new ViewFeatureResume(f.getProject().getName(), f.getProject().getWebID(), f.getName(),userHasvoted , f.getDescription(),
					f.getVotes(), f.getNComments(), f.getAuthor().getUser() ));
		}

		return res;
	}


}
