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
import eu.ist.fears.client.common.communication.Communication;
import eu.ist.fears.client.common.exceptions.ExceptionsTreatment;
import eu.ist.fears.client.common.views.ViewAdmins;

public class ListAdmins  extends Composite {
	protected Communication _com;
	protected VerticalPanel _contentPanel;
	private TextBox _newAdminName; 
	private Button _createAdminButton;
	protected VerticalPanel _errors;
	protected String _projectId;

	public ListAdmins(String projectId){
		_com= new Communication("service");
		_contentPanel = new VerticalPanel();
		_errors= new VerticalPanel();
		_errors.setStyleName("error");
		_projectId=projectId;

		_newAdminName = new TextBox();

		initWidget(_contentPanel);
		init();
		update();
	}

	private void init(){
		_contentPanel.clear();

	}

	public void update(){
		if(_projectId==null)
			_com.getAdmins(Cookies.getCookie("fears"), getAdminCB);
		else _com.getProjectAdmins(_projectId, getAdminCB);
	}

	private void displayCreateAdmin(){
		if(!Fears.isAdminUser())
			return;
		
		_contentPanel.add(new HTML("<br><br><h2>Adicionar Administrador</h2>"));
		_contentPanel.add(new Label("Nome do Administrador:"));
		_newAdminName.setText("");
		_contentPanel.add(_newAdminName);
		_createAdminButton = new Button("Adicionar Administrador");
		_contentPanel.add(_createAdminButton);
		_contentPanel.add(_errors);

		_createAdminButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				_errors.clear();
				if(_newAdminName.getText().isEmpty()){
					_errors.add(new HTML("Erro:"));
					_errors.add(new HTML("Tem de preencher o nome do Administrador."));	
					return;
				}
				
				if(_projectId==null)
					_com.addAdmin(_newAdminName.getText(), Cookies.getCookie("fears"), getAdminCB);
				else _com.addProjectAdmin(_newAdminName.getText(), _projectId, getAdminCB);
			}
		}); 

	}

	private class AdminWidget extends Composite{
		HorizontalPanel _contentPanel; 
		String _name;
		Label _nickName;
		Button _removeButton;

		public AdminWidget(String name, String nickName){
			_name = name;
			_nickName = new Label(nickName);
			_removeButton = new Button("Remover");
			_removeButton.setSize("66", "25");
			_removeButton.addClickListener(new RemoveAdmin());
			_contentPanel = new HorizontalPanel();

			_contentPanel.add(_nickName);
			_contentPanel.add(_removeButton);

			initWidget(_contentPanel);
		}


		protected class RemoveAdmin implements ClickListener{

			public void onClick(Widget sender) {
				if(_projectId==null)
				_com.removeAdmin(_name, Cookies.getCookie("fears"), getAdminCB);
				else
				_com.removeProjectAdmin(_name, _projectId, getAdminCB);
			}

		}

	}

	protected void updateAdmin(ViewAdmins admins){
		init();
		if(admins.getProjectId()==null)
			Fears.getPath().setAdmins();
		else Fears.getPath().setEditAdmins(admins.getProjectName(), admins.getProjectId());
			

		if(admins==null || admins.getAdmins()==null  || admins.getAdmins().size()==0){
			_contentPanel.add(new Label("Nao ha Administradores"));
		} else {
			for(int i=0;i<admins.getAdmins().size(); i++){
				AdminWidget a = new AdminWidget((String)admins.getAdmins().get(i), (String)admins.getAdminsNick().get(i));
				a.setStyleName("admin");
				_contentPanel.add(a);
			}
		}

		displayCreateAdmin();

	}


	AsyncCallback getAdminCB = new ExceptionsTreatment() {
		public void onSuccess(Object result){ 
			updateAdmin((ViewAdmins) result);
		}
		
	};

}
