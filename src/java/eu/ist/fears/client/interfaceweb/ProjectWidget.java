package eu.ist.fears.client.interfaceweb;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.common.communication.Communication;
import eu.ist.fears.common.views.ViewProject;


public class ProjectWidget extends Composite{

	protected VerticalPanel project; 
	protected VerticalPanel projectContainer;
	protected Label name;
	protected Label description;
	protected int nFeatures;
	protected Label author;
	protected Button removeButton;
	protected HorizontalPanel admin;
	protected Communication com;

	public ProjectWidget(ViewProject p){
		com= new Communication("service");
		name = new Label(p.getName());
		description= new Label(p.getDescription());
		nFeatures = p.getNFeatures();
		author= new Label(p.getAuthor());
		project = new VerticalPanel();
		projectContainer = new VerticalPanel();

		projectContainer.add(project);
		projectContainer.setStyleName("project");
		
		HorizontalPanel top = new HorizontalPanel();
		top.setStyleName("projectTop");
		Hyperlink title = new Hyperlink(name.getText(), "Project" + p.getwebID());
		title.setStyleName("projectTitle");
		top.add(title);
		top.add(new HTML("&nbsp; ("+nFeatures + " Sugest&otilde;es)"));
		project.add(top); 
		description.setStyleName("projectDescription");
		project.add(description);
		initWidget(projectContainer);
	}

}
