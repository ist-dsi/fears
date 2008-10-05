package eu.ist.fears.client.interfaceweb;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.client.common.views.ViewProject;


public class ProjectWidget extends Composite{

	protected VerticalPanel _project; 
	protected VerticalPanel _projectContainer;
	protected Label _name;
	protected Label _description;
	protected int _nFeatures;
	protected Label _author;
	protected Button _removeButton;
	protected HorizontalPanel _admin;

	public ProjectWidget(ViewProject p){
		_name = new Label(p.getName());
		_description= new Label(p.getDescription());
		_nFeatures = p.getNFeatures();
		_author= new Label(p.getAuthor());

		_project = new VerticalPanel();
		_projectContainer = new VerticalPanel();

		_projectContainer.add(_project);
		_projectContainer.setStyleName("project");
		
		HorizontalPanel top = new HorizontalPanel();
		top.setStyleName("project");
		Hyperlink title = new Hyperlink(_name.getText(), "Project" + p.getwebID());
		title.setStyleName("projectTitle");
		top.add(title);
		top.add(new HTML("&nbsp; ("+_nFeatures + " Sugest&otilde;es)"));
		_project.add(top); 
		_project.add(_description);
		initWidget(_projectContainer);
	}

}
