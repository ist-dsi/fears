package eu.ist.fears.client.interfaceweb;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;

public class Path extends Composite{

	protected static HorizontalPanel _pathBox;
	protected static HorizontalPanel _path;
	protected static HorizontalPanel _back;

	public Path(){
		_pathBox = new HorizontalPanel();
		_path =  new HorizontalPanel();
		_back = new HorizontalPanel();
		_pathBox.add(_path);
		_path.add(new Hyperlink("Projectos","listProjects"));
		_path.setStyleName("Path");
		_pathBox.setStyleName("width100");
		_pathBox.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		_pathBox.add(_back);
		initWidget(_pathBox);
	}
	
	public void setFears(){
		_path.clear();

		_path.add(new Hyperlink("Projectos","listProjects"));

		_back.clear();
	}
	
	public void setProject(String projectName, String projectID){
		_path.clear();
		
		_path.add(new Hyperlink("Projectos","listProjects"));

		_path.add(new HTML("&nbsp;/&nbsp;"));
		_path.add(new Hyperlink(projectName,"Project"+projectID));

		_back.clear();
	}
	
	public void setProjectChange(String displayOnPath){
		_path.clear();
		
		_path.add(new Hyperlink("Projectos","listProjects"));
		_path.add(new HTML("&nbsp;/ " + displayOnPath));

		_back.clear();
	}

	public void setFeature(String projectName, String projectID, String displayOnPath  ){
		_path.clear();

		_path.add(new Hyperlink("Projectos","listProjects"));

		_path.add(new HTML("&nbsp;/&nbsp;"));
		_path.add(new Hyperlink(projectName,"Project"+projectID));

		_path.add(new HTML("&nbsp;/ " + displayOnPath));
		
		_back.clear();
		_back.add(new Hyperlink("&laquo; Back",true,"Project"+projectID));
	}

	public void setFeatureChange(String projectName, String projectID, String displayOnPath){
		_path.clear();

		_path.add(new Hyperlink("Projectos","listProjects"));
		
		_path.add(new HTML("&nbsp;/&nbsp;"));
		_path.add(new Hyperlink(projectName,"Project"+projectID));
		
		_path.add(new HTML("&nbsp;/ " + displayOnPath));
		
		_back.clear();
	}
	
	public void setVoter(String projectName, String projectID, String voter ){
		_path.clear();
		
		_path.add(new Hyperlink("Projectos","listProjects"));
		
		_path.add(new HTML("&nbsp;/&nbsp;"));
		_path.add(new Hyperlink(projectName,"Project"+projectID));
		
		_path.add(new HTML("&nbsp;/ Utilizadores"));
		
		
		_path.add(new HTML("&nbsp;/ " + voter));
		
		_back.clear();
	}
	
	public void setString(String display){
		_path.clear();
		
		_path.add(new HTML(display));	
		
		_back.clear();
	}

	public void setAdmins(){
		_path.clear();
		_path.add(new Hyperlink("Administradores","admins"));
	}

	public void setCreateProject() {
		_path.clear();
		_path.add(new Hyperlink("Projectos","listProjects"));
		_path.add(new HTML("&nbsp;/ " + "Criar Projecto"));
		
		
	}

	public void setEditProject(String projectName, String projectID) {
		_path.clear();
		_path.add(new Hyperlink("Projectos","listProjects"));
		_path.add(new HTML("&nbsp;/&nbsp;"));
		_path.add(new Hyperlink(projectName,"Project"+projectID));
		_path.add(new HTML("&nbsp;/Editar Projecto"));
	}
	
	public void setEditAdmins(String projectName, String projectID) {
		_path.clear();
		_path.add(new Hyperlink("Projectos","listProjects"));
		_path.add(new HTML("&nbsp;/&nbsp;"));
		_path.add(new Hyperlink(projectName,"Project"+projectID));
		_path.add(new HTML("&nbsp;/ Editar Administradores"));
	}
	
	public void setHelp(){
		_path.clear();
		_path.add(new Hyperlink("Sobre o FeaRS","help"));
		
	}


}
