package eu.ist.fears.client.interfaceweb;

import com.google.gwt.user.client.Window;
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
		_path.add(new Hyperlink("Projectos","projectos"));
		_path.setStyleName("Path");
		_pathBox.setStyleName("width100");
		_pathBox.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		_pathBox.add(_back);
		initWidget(_pathBox);
	}
	
	public void setFears(){
		_path.clear();

		HTML fears = new HTML("FeaRS");
		fears.setStyleName("pathGreen");
		HTML frs = new HTML("&nbsp;/&nbsp;Feature Request System");
		frs.setStyleName("pathSmall");
		_path.add(fears);
		_path.add(frs);

		setPageTitle("Projectos");
		
		_back.clear();
	}
	
	public void setProjects(){
		_path.clear();

		_path.add(new Hyperlink("Projectos","projectos"));
		
		setPageTitle("Projectos");

		_back.clear();
	}
	
	public void setAdminProjects(){
		_path.clear();

		_path.add(new Hyperlink("Projectos","projectos"));
		
		Window.setTitle("Admin - Projectos");

		_back.clear();
	}
	
	public void setProject(String projectName, String projectID){
		_path.clear();
		
		_path.add(new Hyperlink("Projectos","projectos"));

		_path.add(new HTML("&nbsp;/&nbsp;"));
		_path.add(new Hyperlink(projectName,"Project"+projectID));
		
		setPageTitle(projectName);

		_back.clear();
		_back.add(new Hyperlink("&laquo; Back",true,""));
	}

	public void setFeature(String projectName, String projectID, String displayOnPath, String fName){
		_path.clear();

		_path.add(new Hyperlink("Projectos","projectos"));

		_path.add(new HTML("&nbsp;/&nbsp;"));
		_path.add(new Hyperlink(projectName,"Project"+projectID));

		_path.add(new HTML("&nbsp;/ " + displayOnPath));
		
		setPageTitle(fName);
		
		_back.clear();
		_back.add(new Hyperlink("&laquo; Back",true,"Project"+projectID));
	}

	public void setFeatureChange(String projectName, String projectID, String displayOnPath){
		_path.clear();

		_path.add(new Hyperlink("Projectos","projectos"));
		
		_path.add(new HTML("&nbsp;/&nbsp;"));
		_path.add(new Hyperlink(projectName,"Project"+projectID));
		
		_path.add(new HTML("&nbsp;/ " + displayOnPath));
		
		setPageTitle(projectName + " - " + displayOnPath);
		
		_back.clear();
	}
	
	public void setVoter(String voterNick){
		_path.clear();
		
		_path.add(new Hyperlink("FeaRS","projectos"));	
		
		_path.add(new HTML("&nbsp;/ Utilizadores"));
		
		_path.add(new HTML("&nbsp;/ " + voterNick));
		
		setPageTitle(voterNick);
		
		_back.clear();
	}

	public void setAdmins(){
		_path.clear();
		_path.add(new Hyperlink("Administradores","admins"));
		
		Window.setTitle("Admin - Editar Administradores");
		
	}

	public void setCreateProject() {
		_path.clear();
		_path.add(new Hyperlink("Projectos","projectos"));
		_path.add(new HTML("&nbsp;/ " + "Criar Projecto"));
		
		setPageTitle("Criar Projecto");
	}

	public void setEditProject(String projectName, String projectID) {
		_path.clear();
		_path.add(new Hyperlink("Projectos","projectos"));
		_path.add(new HTML("&nbsp;/&nbsp;"));
		_path.add(new Hyperlink(projectName,"Project"+projectID));
		_path.add(new HTML("&nbsp;/Editar Projecto"));
		
		setPageTitle(projectName + " - Editar Projecto");
	}
	
	public void setEditAdmins(String projectName, String projectID) {
		_path.clear();
		_path.add(new Hyperlink("Projectos","projectos"));
		_path.add(new HTML("&nbsp;/&nbsp;"));
		_path.add(new Hyperlink(projectName,"Project"+projectID));
		_path.add(new HTML("&nbsp;/ Editar Administradores"));
		
		setPageTitle(projectName + " - Editar Administradores");
	}
	
	public void setHelp(){
		_path.clear();
		_path.add(new Hyperlink("Sobre o FeaRS","help"));
		
		setPageTitle("Ajuda");
		
	}

	public void setPageTitle(String name){
		Window.setTitle("FeaRS - " + name );
	}

}
