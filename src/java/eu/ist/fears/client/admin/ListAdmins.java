package eu.ist.fears.client.admin;


import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ist.fears.client.Fears;
import eu.ist.fears.client.communication.Communication;
import eu.ist.fears.client.views.ViewAdmins;

public class ListAdmins  extends Composite {
	protected Communication _com;
	protected VerticalPanel _contentPanel;
	private TextBox _newAdminName; 
	private Button _createAdminButton;

	public ListAdmins(){
		_com= new Communication("service");
		_contentPanel = new VerticalPanel();

		Fears.getPath().setAdmins();
		_newAdminName = new TextBox();

		initWidget(_contentPanel);
		init();
	}

	private void init(){
		_contentPanel.clear();

	}

	public void update(){
		_com.getAdmins(Cookies.getCookie("fears"), getProjectsCB);	
	}

	private void displayCreateAdmin(){

		_contentPanel.add(new HTML("<br><br><h2>Criar Administrador</h2>"));
		_contentPanel.add(new Label("Nome do Administrador:"));
		_newAdminName.setText("");
		_contentPanel.add(_newAdminName);
		_createAdminButton = new Button("Adicionar Administrador");
		_contentPanel.add(_createAdminButton);

		_createAdminButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				_com.addAdmin(_newAdminName.getText(), Cookies.getCookie("fears"), getProjectsCB);
			}
		}); 

	}

	private class AdminWidget extends Composite{
		HorizontalPanel _contentPanel; 
		Label _name;
		Button _removeButton;

		public AdminWidget(String name){
			_name = new Label(name);
			_removeButton = new Button("Remover");
			_removeButton.setSize("66", "25");
			_removeButton.addClickListener(new RemoveAdmin());
			_contentPanel = new HorizontalPanel();

			_contentPanel.add(_name);
			_contentPanel.add(_removeButton);

			initWidget(_contentPanel);
		}


		protected class RemoveAdmin implements ClickListener{

			public void onClick(Widget sender) {
				_com.removeAdmin(_name.getText(), Cookies.getCookie("fears"), getProjectsCB);
			}

		}

	}

	protected void updateAdmin(ViewAdmins admins){
		init();


		if(admins==null || admins.getAdmins()==null  || admins.getAdmins().size()==0){
			_contentPanel.add(new Label("Nao ha Administradores"));
		} else {
			for(int i=0;i<admins.getAdmins().size(); i++){
				AdminWidget a = new AdminWidget((String)admins.getAdmins().get(i));
				a.setStyleName("admin");
				_contentPanel.add(a);
			}
		}

		displayCreateAdmin();

	}


	AsyncCallback getProjectsCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			updateAdmin((ViewAdmins) result);
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Nao foi possivel contactar o servidor."+caught.getLocalizedMessage()));
		}
	};

}
