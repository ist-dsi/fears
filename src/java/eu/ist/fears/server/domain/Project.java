package eu.ist.fears.server.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import eu.ist.fears.client.common.views.ViewFeatureResume;
import eu.ist.fears.client.common.views.ViewProject;

public class Project extends Project_Base {



	public Project(String name, String description, int nvotes, User user){
		setName(name);
		Voter v =new Voter(user);
		setDescription(description);
		setAuthor(user);
		setFeaturesIncrementID(0);
		setInitialVotes(nvotes);
		addVoter(v);
		
	}


	public String getAuthorName() {
		return getAuthor().getName();
	}

	public FeatureRequest getFeature(String featureID){
		int featID;
		try{
			featID= new Integer(featureID).intValue();
		}catch(Throwable t){
			System.out.println("ID:" + featureID);
			throw new RuntimeException("Nao existe esse projecto: " + featureID);
		}

		for(FeatureRequest f : getFeatureRequestSet()){
			if(f.getWebID()==featID)	
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


	public  void addFeature(FeatureRequest f){
		addFeatureRequest(f);
		setFeaturesIncrementID(getFeaturesIncrementID()+1);
		f.setWebID(getFeaturesIncrementID());
	}

	public List<ViewFeatureResume> search(String search, String sort, int page, String filter, Voter v){
		StringTokenizer s = new StringTokenizer(search," ");
		List<ViewFeatureResume> ret = new ArrayList<ViewFeatureResume>();

		if(this.getFeatureRequestCount()==0)
			return ret;
		
		if(!filter.isEmpty()){
			return sortAndPage(filter(filter, v), sort, page);
		}
				
		//Make the list with the features that have the first token
		if(s.countTokens()>0){
			String first = s.nextToken();
			for(FeatureRequest f : getFeatureRequestSet()){
				if(f.getDescription().contains(first) || f.getName().contains(first))
					ret.add( f.getResumeView(v));
			}
		}else {//Return All
			ret.addAll( FearsApp.getViewFeaturesResumes(getFeatures(), v));
				return sortAndPage(ret , sort, page); 
		}
				
		//Remove from the list the features that don't have the other tokens.
		while(s.hasMoreTokens()){
			String token = s.nextToken();
			int i=0;
			while(i<ret.size()){
				if(!ret.get(i).getDescription().contains(token) && !ret.get(i).getName().contains(token)){
					ret.remove(i);
					i--;
				}
					
				i++;
			}
		}
		return sortAndPage(ret,sort,page);

	}


	private List<ViewFeatureResume> filter(String filter, Voter v) {
		List<ViewFeatureResume> ret = new ArrayList<ViewFeatureResume>();
		
		
		for(FeatureRequest f : getFeatureRequestSet()){
			if(f.getState().toString().equals(filter))
				ret.add(f.getResumeView(v));
		}
		
		return ret;
	}


	private List<ViewFeatureResume> sortAndPage(List<ViewFeatureResume> ret,
			String sort, int page) {
	
		
		
		//Sort For Date:
		if("Ordenar por Data".equals(sort)){
			Collections.sort(ret, new DateComparator());
		}else Collections.sort(ret, new VoteComparator()); //Sort For Votes:
		
		//Page:
		
		
		return ret;
	}

	public void addVoter(Voter v){
		v.setVotesUsed(0);
		super.addVoter(v);
	}
	
	public ViewProject getView(){
		return new ViewProject(getName(), getIdInternal(), getDescription(), getNFeatures(), getAuthorName(), getInitialVotes());
		
		
	}
	
	protected class VoteComparator implements Comparator<ViewFeatureResume>{

		public int compare(ViewFeatureResume o1, ViewFeatureResume o2) {
			return o2.getVotes()-o1.getVotes();
		}
	}
	
	protected class DateComparator implements Comparator<ViewFeatureResume>{

		public int compare(ViewFeatureResume o1, ViewFeatureResume o2) {
			return o2.getFeatureID()-o1.getFeatureID();
		}
	}

	public void edit(String name, String description,
			int nvotes) {
		setName(name);
		setDescription(description);
		setInitialVotes(nvotes);
		
	}
	
	
}
