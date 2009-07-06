package eu.ist.fears.client.interfaceweb;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.common.communication.Communication;


public class CreateEditProjectFeature extends Composite {

	protected Communication _com;
	protected VerticalPanel _sugPanel;
	protected HorizontalPanel _projectTitle;
	protected Grid _table; 
	protected HorizontalPanel _buttons; 
	protected VerticalPanel _errorPanel;
	protected VerticalPanel _goodPractices;
	
	public CreateEditProjectFeature(int nfields){
		_com= new Communication("service");
		_sugPanel = new VerticalPanel();
		_table = new Grid(nfields, 2);
		_table.setStyleName("createFeatureForm");
		_projectTitle = new HorizontalPanel();
		_goodPractices= new VerticalPanel();
		_errorPanel= new VerticalPanel();
		_buttons = new HorizontalPanel();
		
		_errorPanel.setStyleName("error");
		_buttons.setStyleName("createButtons");
		
		initWidget(_sugPanel);
		for(int i=0; i<nfields; i++){
		_table.getCellFormatter().setStyleName(i, 0, "CreateFeatureCell");
		_table.getCellFormatter().setStyleName(i, 1, "CreateFeatureCell");
		}
		
		_sugPanel.add(_goodPractices);
		_sugPanel.add(_table);
		_sugPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		_sugPanel.add(_buttons);
		_sugPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		_sugPanel.add(_errorPanel);
		
	}
	
}









