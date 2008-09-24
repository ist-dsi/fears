package eu.ist.fears.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.client.communication.Communication;

public class DisplayVoter extends Composite {
	
	private Communication _com;
	private VerticalPanel _sugPanel;
	private Label _voterName;
	
	public DisplayVoter(String projectId, String voterName){
		
		
		_com = new Communication("service");
		_voterName = new Label(voterName);
		_sugPanel = new VerticalPanel();
		_sugPanel.add(_voterName);		
		
		Fears.getPath().setVoter("", projectId, voterName);
		
		
		
		initWidget(_sugPanel);		
	}

}
