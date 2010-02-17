package eu.ist.fears.client.interfaceweb;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;

public class Path extends Composite {

    protected static HorizontalPanel pathBox;
    protected static HorizontalPanel path;
    protected static HorizontalPanel back;

    public Path() {
	pathBox = new HorizontalPanel();
	path = new HorizontalPanel();
	back = new HorizontalPanel();
	pathBox.add(path);
	path.add(new Hyperlink("Projectos", "projectos"));
	path.setStyleName("Path");
	pathBox.setStyleName("width100");
	pathBox.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
	pathBox.add(back);
	initWidget(pathBox);
    }

    public void setFears() {
	path.clear();

	HTML fears = new HTML("FeaRS");
	fears.setStyleName("pathGreen");
	HTML frs = new HTML("&nbsp;/&nbsp;Feature Request System");
	frs.setStyleName("pathSmall");
	path.add(fears);
	path.add(frs);

	setPageTitle("Projectos");

	back.clear();
    }

    public void setProjects() {
	path.clear();

	path.add(new Hyperlink("Projectos", "projectos"));

	setPageTitle("Projectos");

	back.clear();
    }

    public void setAdminProjects() {
	path.clear();

	path.add(new Hyperlink("Projectos", "projectos"));

	Window.setTitle("Admin - Projectos");

	back.clear();
    }

    public void setProject(String projectName, String projectID) {
	path.clear();

	path.add(new Hyperlink("Projectos", "projectos"));

	path.add(new HTML("&nbsp;/&nbsp;"));
	path.add(new Hyperlink(projectName, "Project" + projectID));

	setPageTitle(projectName);

	back.clear();
	back.add(new Hyperlink("&laquo; Back", true, ""));
    }

    public void setFeature(String projectName, String projectID, String displayOnPath, String fName) {
	path.clear();

	path.add(new Hyperlink("Projectos", "projectos"));

	path.add(new HTML("&nbsp;/&nbsp;"));
	path.add(new Hyperlink(projectName, "Project" + projectID));

	path.add(new HTML("&nbsp;/ " + displayOnPath));

	setPageTitle(fName);

	back.clear();
	back.add(new Hyperlink("&laquo; Back", true, "Project" + projectID));
    }

    public void setFeatureChange(String projectName, String projectID, String displayOnPath) {
	path.clear();

	path.add(new Hyperlink("Projectos", "projectos"));

	path.add(new HTML("&nbsp;/&nbsp;"));
	path.add(new Hyperlink(projectName, "Project" + projectID));

	path.add(new HTML("&nbsp;/ " + displayOnPath));

	setPageTitle(projectName + " - " + displayOnPath);

	back.clear();
    }

    public void setVoter(String voterNick) {
	path.clear();

	path.add(new Hyperlink("FeaRS", "projectos"));

	path.add(new HTML("&nbsp;/ Utilizadores"));

	path.add(new HTML("&nbsp;/ " + voterNick));

	setPageTitle(voterNick);

	back.clear();
    }

    public void setAdmins() {
	path.clear();
	path.add(new Hyperlink("Administradores", "admins"));

	Window.setTitle("Admin - Editar Administradores");

    }

    public void setCreateProject() {
	path.clear();
	path.add(new Hyperlink("Projectos", "projectos"));
	path.add(new HTML("&nbsp;/ " + "Criar Projecto"));

	setPageTitle("Criar Projecto");
    }

    public void setEditProject(String projectName, String projectID) {
	path.clear();
	path.add(new Hyperlink("Projectos", "projectos"));
	path.add(new HTML("&nbsp;/&nbsp;"));
	path.add(new Hyperlink(projectName, "Project" + projectID));
	path.add(new HTML("&nbsp;/Editar Projecto"));

	setPageTitle(projectName + " - Editar Projecto");
    }

    public void setEditAdmins(String projectName, String projectID) {
	path.clear();
	path.add(new Hyperlink("Projectos", "projectos"));
	path.add(new HTML("&nbsp;/&nbsp;"));
	path.add(new Hyperlink(projectName, "Project" + projectID));
	path.add(new HTML("&nbsp;/ Editar Administradores"));

	setPageTitle(projectName + " - Editar Administradores");
    }

    public void setHelp() {
	path.clear();
	path.add(new Hyperlink("Sobre o FeaRS", "help"));

	setPageTitle("Ajuda");

    }

    public void setPageTitle(String name) {
	Window.setTitle("FeaRS - " + name);
    }

}
