package eu.ist.fears.client.interfaceweb;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.client.common.communication.Communication;


public class CreateEditProjectFeature extends Composite {

	protected Communication _com;
	protected VerticalPanel _sugPanel;
	protected HorizontalPanel _projectTitle;
	protected Grid _table; 
	protected HorizontalPanel _buttons; 
	protected VerticalPanel _errorPanel;
	
	public CreateEditProjectFeature(int nfields){
		_com= new Communication("service");
		_sugPanel = new VerticalPanel();
		_table = new Grid(nfields, 2);
		_projectTitle = new HorizontalPanel();
		_errorPanel= new VerticalPanel();
		_buttons = new HorizontalPanel();
		
		_errorPanel.setStyleName("error");
		_buttons.setStyleName("createButtons");
		
		initWidget(_sugPanel);
		for(int i=0; i<nfields; i++){
		_table.getCellFormatter().setStyleName(i, 0, "CreateFeatureCell");
		_table.getCellFormatter().setStyleName(i, 1, "CreateFeatureCell");
		}
		
		_sugPanel.add(_table);
		_sugPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		_sugPanel.add(_buttons);
		_sugPanel.add(_errorPanel);
	}
	
}









