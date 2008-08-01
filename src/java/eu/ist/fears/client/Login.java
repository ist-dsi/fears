package eu.ist.fears.client;


import com.google.gwt.user.client.History;
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

import eu.ist.fears.client.communication.Communication;
import eu.ist.fears.client.views.ViewVoter;

public class Login extends Composite {

	private Communication _com;
	private VerticalPanel _main;
	private TextBox _username; 
	private Button _loginButton;
	private Label _alert;


	public Login(Fears f){

		_com= new Communication("service");
		_main = new VerticalPanel();
		initWidget(_main);
		_main.setStyleName("login");
		
		Fears.getPath().update(null, 0, "Login", false);

		HorizontalPanel row = new HorizontalPanel();
		row.add(new Label("Username:  "));
		_username = new TextBox();
		row.add(_username);
		_main.add(row);
		_main.add(new HTML("(Qualquer um e aceite)<br><br>"));
		_loginButton = new Button("Login");
		_alert = new Label();
		_main.add(_alert);
		_main.add(_loginButton);

		_loginButton.addClickListener(new LoginButton(f));
	}

	private class LoginButton implements ClickListener{
		Fears _f;

		public LoginButton(Fears f){
			_f=f;
		}

		public void onClick(Widget sender) {
			_alert.setText("A fazer Login");
			_com.login(_username.getText(),  "password", new LoginCB(_f));
		}

	}


	private class LoginCB implements AsyncCallback{
		Fears _f;

		public LoginCB(Fears f){
			_f=f;
		}

		public void onSuccess(Object result){ 
			ViewVoter voter = (ViewVoter) result;
			_f.setCookie(voter.getSessionID(), voter.getName());
			if(History.getToken().equals("login")){
				History.back();
			}else
			_f.onHistoryChanged(History.getToken());
			
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("login failure"));
		}
	};

}
