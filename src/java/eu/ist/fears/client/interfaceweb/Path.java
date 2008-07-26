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

	public void update(String project, String actual, boolean back){
		_path.clear();


		_path.add(new Hyperlink("Projectos","listProjects"));
		if(project!=null){
			_path.add(new HTML("&nbsp;/&nbsp;"));
			_path.add(new Hyperlink(project,"Project"+project));
		}

		if(actual!=null){
			//_projectTitle.add(new Hyperlink(featureName,"Project"+projectName+"?"+"viewFeature"+featureName));
			_path.add(new HTML("&nbsp;/ " + actual));
		}

		if(back){
			_back.clear();
			_back.add(new Hyperlink("&laquo; Back",true,"Project"+project));
		}else{
			_back.clear();
		}

	}

}
