package eu.ist.fears.server;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import eu.ist.fears.client.communication.FearsService;
import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewFeatureResume;
import eu.ist.fears.client.views.ViewProject;



public class FearsServiceImpl extends RemoteServiceServlet implements FearsService {

	/**
	 * 
	 */	
	private static final long serialVersionUID = -9186875057311859285L;

	public void vote(String projectName, String name){
		Project p =FearsApp.getFears().getProject(projectName);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectName);

		FeatureRequest f= p.getFeature(name);

		if(f==null)
			throw new RuntimeException("Nao existe essa sugestao: " + name);

		f.vote();
	}

	public void addFeature(String projectName, String name, String description){
		Project p =FearsApp.getFears().getProject(projectName);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectName);

		p.addFeature(new FeatureRequest(name, description));
	}

	public ViewFeatureResume[] getFeatures(String projectName){
		Project p =FearsApp.getFears().getProject(projectName);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectName);

		return p.getViewFeaturesResumes();
	}

	public ViewFeatureDetailed getFeature(String projectName, String name){
		Project p =FearsApp.getFears().getProject(projectName);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectName);

		if(p.getFeature(name)==null)
			throw new RuntimeException("Nao existe essa sugestao: " + name);

		return p.getFeature(name).getDetailedView();

	}

	public ViewFeatureDetailed addComment(String projectName, String featureName, String comment) {
		Project p =FearsApp.getFears().getProject(projectName);

		if(p==null)
			throw new RuntimeException("Nao existe esse projecto: " + projectName);


		if(p.getFeature(featureName)==null)
			throw new RuntimeException("Nao existe essa sugestao: " + featureName);

		p.getFeature(featureName).addComment(comment);
		return p.getFeature(featureName).getDetailedView();
	}

	public void addProject(String name, String description) {
		FearsApp.getFears().addProject(new Project(name, description));
	}

	public ViewProject[] getProjects() {
		return  FearsApp.getFears().getProjects();
	}

	public void deleteProject(String name){
		FearsApp.getFears().deleteProject(name);
	}


}