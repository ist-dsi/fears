package eu.ist.fears.client.admin;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.client.Fears;
import eu.ist.fears.common.FearsAsyncCallback;
import eu.ist.fears.common.communication.Communication;
import eu.ist.fears.common.views.ViewAdmins;

public class ListAdmins extends Composite {
    protected Communication com;
    protected VerticalPanel contentPanel;
    private TextBox newAdminName;
    private Button createAdminButton;
    protected VerticalPanel errors;
    protected String projectId;

    public ListAdmins(String projectId) {
	com = new Communication("service");
	contentPanel = new VerticalPanel();
	errors = new VerticalPanel();
	errors.setStyleName("error");
	this.projectId = projectId;

	newAdminName = new TextBox();

	initWidget(contentPanel);
	init();
	update();
    }

    private void init() {
	contentPanel.clear();

    }

    public void update() {
	if (projectId == null)
	    com.getAdmins(Cookies.getCookie("fears"), getAdminCB);
	else
	    com.getProjectAdmins(projectId, getAdminCB);
    }

    private void displayCreateAdmin() {
	if (!Fears.isAdminUser())
	    return;

	contentPanel.add(new HTML("<br><br><h2>Adicionar Administrador</h2>"));
	contentPanel.add(new Label("Nome de Utilizador (IST ID) do Administrador:"));
	newAdminName.setText("");
	contentPanel.add(newAdminName);
	createAdminButton = new Button("Adicionar Administrador");
	contentPanel.add(createAdminButton);
	contentPanel.add(errors);
	createAdminButton.addClickHandler(new ClickHandler() {

	    public void onClick(ClickEvent event) {
		errors.clear();
		if (newAdminName.getText().length() == 0) {
		    errors.add(new HTML("Erro:"));
		    errors.add(new HTML("Tem de preencher o nome do Administrador."));
		    return;
		}

		if (projectId == null)
		    com.addAdmin(newAdminName.getText(), Cookies.getCookie("fears"), getAdminCB);
		else
		    com.addProjectAdmin(newAdminName.getText(), projectId, getAdminCB);
	    }
	});

    }

    private class AdminWidget extends Composite {
	private HorizontalPanel contentPanel;
	private String name;
	private Label nickName;
	private Button removeButton;

	public AdminWidget(String name, String nickName) {
	    this.name = name;
	    this.nickName = new Label(nickName);
	    removeButton = new Button("Remover");
	    removeButton.setSize("66", "25");
	    removeButton.addClickHandler(new RemoveAdmin());
	    contentPanel = new HorizontalPanel();

	    contentPanel.add(this.nickName);
	    contentPanel.add(removeButton);

	    initWidget(contentPanel);
	}

	protected class RemoveAdmin implements ClickHandler {
	    public void onClick(ClickEvent sender) {
		if (projectId == null)
		    com.removeAdmin(name, Cookies.getCookie("fears"), getAdminCB);
		else
		    com.removeProjectAdmin(name, projectId, getAdminCB);
	    }

	}

    }

    protected void updateAdmin(ViewAdmins admins) {
	init();
	if (admins.getProjectId() == null)
	    Fears.getPath().setAdmins();
	else
	    Fears.getPath().setEditAdmins(admins.getProjectName(), admins.getProjectId());

	if (admins == null || admins.getAdmins() == null || admins.getAdmins().size() == 0) {
	    contentPanel.add(new Label("Nao ha Administradores"));
	} else {
	    for (int i = 0; i < admins.getAdmins().size(); i++) {
		AdminWidget a = new AdminWidget(admins.getAdmins().get(i), admins.getAdminsNick().get(i));
		a.setStyleName("admin");
		contentPanel.add(a);
	    }
	}

	displayCreateAdmin();

    }

    AsyncCallback<Object> getAdminCB = new FearsAsyncCallback<Object>() {
	public void onSuccess(Object result) {
	    updateAdmin((ViewAdmins) result);
	}

    };

}
