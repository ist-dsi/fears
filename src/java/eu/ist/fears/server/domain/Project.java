package eu.ist.fears.server.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import eu.ist.fears.client.views.ViewFeatureResume;


public class Project extends Project_Base {



	public Project(String name, int projNumber, String description, Voter voter){
		setName(name);
		setWebID(projNumber);
		setDescription(description);
		setAuthor(voter);
		setFeaturesIncrementID(0);
	}


	public String getAuthorName() {
		return getAuthor().getUser();
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

	public List<ViewFeatureResume> search(String search, String sort, int page, Voter v){
		StringTokenizer s = new StringTokenizer(search," ");
		List<ViewFeatureResume> ret = new ArrayList<ViewFeatureResume>();

		
		//Make the list with the features that have the first token
		if(s.countTokens()>0){
			String first = s.nextToken();
			for(FeatureRequest f : getFeatureRequestSet()){
				if(f.getDescription().contains(first) || f.getName().contains(first))
					ret.add(f.getDetailedView(v));
			}
		}else return FearsApp.getViewFeaturesResumes(getFeatures(),v); //Return All

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


	private List<ViewFeatureResume> sortAndPage(List<ViewFeatureResume> ret,
			String sort, int page) {
	
		//Sort:
		
		//Sort For Votes:
		
		//Page:
		return ret;
	}

	
	
	protected class VoteComparator implements Comparator<Integer>{

		public int compare(Integer o1, Integer o2){
			return o1-o2;
		}
	}
	
	
	
	
}
