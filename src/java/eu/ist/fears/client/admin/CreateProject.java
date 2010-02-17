package eu.ist.fears.client.admin;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import eu.ist.fears.client.Fears;
import eu.ist.fears.client.interfaceweb.CreateEditProjectFeature;
import eu.ist.fears.common.exceptions.FearsAsyncCallback;

public class CreateProject extends CreateEditProjectFeature {

    protected TextBox name;
    protected TextArea description;
    protected TextBox nInitialVotes;
    protected HorizontalPanel projectTitle;

    public CreateProject() {
	super(3);
	projectTitle = new HorizontalPanel();

	Fears.getPath().setCreateProject();

	name = new TextBox();
	name.setVisibleLength(50);
	name.setStyleName("titleTextBox");
	table.setWidget(0, 0, new HTML("T&iacute;tulo:"));
	table.setWidget(0, 1, (name));

	description = new TextArea();
	description.setStyleName("descriptionTextBox");
	table.setWidget(1, 0, new HTML("Descri&ccedil;&atilde;o:&nbsp;"));
	table.setWidget(1, 1, description);

	nInitialVotes = new TextBox();
	nInitialVotes.setVisibleLength(4);
	nInitialVotes.setStyleName("titleTextBox");
	table.setWidget(2, 0, new HTML("Votos:"));
	table.setWidget(2, 1, (nInitialVotes));

	PushButton sendButton = new PushButton(new Image("button04.gif", 0, 0, 105, 32), new Image("button04.gif", -2, -2, 105,
		32));
	
	sendButton.addClickHandler(new ClickHandler() {
	    
	    public void onClick(ClickEvent event) {
		errorPanel.clear();

		boolean error = false;

		if (name.getText().length() == 0) {
		    errorPanel.add(new HTML("Tem de preencher o nome do projecto."));
		    error = true;
		}
		try {
		    if (nInitialVotes.getText().length() == 0 || new Integer(nInitialVotes.getText()) < 0) {
			errorPanel.add(new HTML("O numero de votos do projecto tem que ser maior ou igual a zero."));
			error = true;
		    }
		} catch (NumberFormatException e) {
		    errorPanel.add(new HTML("O numero de votos tem que ser um numero inteiro."));
		    error = true;
		}
		if (description.getText().length() == 0) {
		    errorPanel.add(new HTML("Tem de preencher a descri&ccedil;&atilde;o do projecto."));
		    error = true;
		}

		if (error)
		    return;

		com.addProject(name.getText(), description.getText(), new Integer(nInitialVotes.getText()).intValue(), Cookies
			.getCookie("fears"), addProjectCB);
		
	    }
	});

	PushButton cancelButton = new PushButton(new Image("button05.gif", 0, 0, 92, 32), new Image("button05.gif", -2, -2, 92,
		32));
	
	cancelButton.addClickHandler(new ClickHandler() {
	    
	    public void onClick(ClickEvent event) {
		History.newItem("");
	    }
	});
	
	sendButton.setStyleName("pright5");

	buttons.add(sendButton);
	buttons.add(cancelButton);
    }

    AsyncCallback<Object> addProjectCB = new FearsAsyncCallback<Object>() {
	public void onSuccess(Object result) {
	    History.newItem("");
	}
    };

}
