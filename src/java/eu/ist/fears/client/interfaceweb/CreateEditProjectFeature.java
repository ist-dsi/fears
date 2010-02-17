package eu.ist.fears.client.interfaceweb;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.common.communication.Communication;

public class CreateEditProjectFeature extends Composite {

    protected Communication com;
    protected VerticalPanel sugPanel;
    protected HorizontalPanel projectTitle;
    protected Grid table;
    protected HorizontalPanel buttons;
    protected VerticalPanel errorPanel;
    protected VerticalPanel goodPractices;

    public CreateEditProjectFeature(int nfields) {
	com = new Communication("service");
	sugPanel = new VerticalPanel();
	table = new Grid(nfields, 2);
	table.setStyleName("createFeatureForm");
	projectTitle = new HorizontalPanel();
	goodPractices = new VerticalPanel();
	errorPanel = new VerticalPanel();
	buttons = new HorizontalPanel();

	errorPanel.setStyleName("error");
	buttons.setStyleName("createButtons");

	initWidget(sugPanel);
	for (int i = 0; i < nfields; i++) {
	    table.getCellFormatter().setStyleName(i, 0, "CreateFeatureCell");
	    table.getCellFormatter().setStyleName(i, 1, "CreateFeatureCell");
	}

	sugPanel.add(goodPractices);
	sugPanel.add(table);
	sugPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
	sugPanel.add(buttons);
	sugPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
	sugPanel.add(errorPanel);

    }

}
