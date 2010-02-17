package eu.ist.fears.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import eu.ist.fears.client.interfaceweb.CreateEditProjectFeature;
import eu.ist.fears.common.exceptions.FearsAsyncCallback;

public class CreateFeature extends CreateEditProjectFeature {

    protected TextBox name;
    protected TextArea description;
    protected String projectID;
    protected HorizontalPanel projectTitle;
    protected HTML goodPracticesLong;
    Button displayPracticesButton;

    public CreateFeature(final String projectID) {
	super(2);
	this.projectID = projectID;
	projectTitle = new HorizontalPanel();
	com.userCreatedFeature(Cookies.getCookie("fears"), getCreatedFeatureCB);

	Fears.getPath().setFeatureChange("", projectID, "Nova Sugestão");

	name = new TextBox();
	name.setVisibleLength(50);
	name.setStyleName("titleTextBox");
	table.setWidget(0, 0, new HTML("T&iacute;tulo:"));
	table.setWidget(0, 1, (name));

	description = new TextArea();
	description.setStyleName("descriptionTextBox");
	table.setWidget(1, 0, new HTML("Descri&ccedil;&atilde;o:&nbsp;"));
	table.setWidget(1, 1, description);

	PushButton sendButton = new PushButton(new Image("button04.gif", 0, 0, 105, 32), new Image("button04.gif", -2, -2, 105,
		32));
	sendButton.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent sender) {
		errorPanel.clear();

		if (name.getText().length() == 0 || description.getText().length() == 0) {
		    errorPanel.add(new HTML("Erro:"));
		    if (name.getText().length() == 0)
			errorPanel.add(new HTML("Tem de preencher o nome da sugest&atilde;o."));
		    if (description.getText().length() == 0)
			errorPanel.add(new HTML("Tem de preencher a descri&ccedil;&atilde;o da sugest&atilde;o."));

		    return;
		}
		com.addFeature(projectID, name.getText(), description.getText(), Cookies.getCookie("fears"), addSugestaoCB);

	    }
	});

	PushButton cancelButton = new PushButton(new Image("button05.gif", 0, 0, 92, 32), new Image("button05.gif", -2, -2, 92,
		32));
	cancelButton.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent sender) {
		History.newItem("Project" + projectID);
	    }
	});
	sendButton.setStyleName("pright5");

	buttons.add(sendButton);
	buttons.add(cancelButton);

	FlowPanel goodPracticesPanel = new FlowPanel();
	goodPracticesPanel.add(new HTML(Fears.getgoodPracticesHead() + "."));
	goodPracticesLong = new HTML(Fears.getgoodPracticesLong());
	goodPracticesLong.setVisible(false);
	displayPracticesButton = new Button("Ler Mais");
	displayPracticesButton.addClickHandler(new ClickHandler() {
	    public void onClick(ClickEvent sender) {
		if (!goodPracticesLong.isVisible()) {
		    goodPracticesLong.setVisible(true);
		    displayPracticesButton.setText("Esconder");
		} else {
		    goodPracticesLong.setVisible(false);
		    displayPracticesButton.setText("Ler Mais");
		}

	    }
	});
	displayPracticesButton.setStyleName("moreButton");
	goodPracticesPanel.add(displayPracticesButton);
	goodPracticesPanel.add(goodPracticesLong);

	goodPractices.add(goodPracticesPanel);

	updateProjectName();
    }

    public void updateProjectName() {
	com.getProjectName(projectID, getProjectName);
    }

    protected void updateProjectName(String name) {
	Fears.getPath().setFeatureChange(name, projectID, "Nova Sugestão");
    }

    protected void getCreatedFeature(boolean created) {
	if (!created) {
	    goodPracticesLong.setVisible(true);
	    displayPracticesButton.setText("Esconder");
	}
    }

    AsyncCallback<Object> addSugestaoCB = new FearsAsyncCallback<Object>() {
	public void onSuccess(Object result) {
	    // do some UI stuff to show success
	    History.newItem("Project" + projectID);
	}
    };

    AsyncCallback<Object> getProjectName = new FearsAsyncCallback<Object>() {
	public void onSuccess(Object result) {
	    updateProjectName((String) result);
	}

    };

    AsyncCallback<Object> getCreatedFeatureCB = new FearsAsyncCallback<Object>() {
	public void onSuccess(Object result) {
	    getCreatedFeature((Boolean) result);
	}

    };

}
