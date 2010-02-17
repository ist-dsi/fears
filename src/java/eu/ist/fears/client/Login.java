package eu.ist.fears.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Login extends Composite {

    private VerticalPanel main;

    public Login(Fears f) {

	/* _com= new Communication("service"); */
	main = new VerticalPanel();

	initWidget(main);
	main.setStyleName("login");
	/*
	 * //Fears.getPath().setString("Login");
	 * 
	 * HorizontalPanel row = new HorizontalPanel(); row.add(new
	 * Label("Username:  ")); _username = new TextBox(); row.add(_username);
	 * _main.add(row); _main.add(new HTML("(Qualquer um e aceite)"));
	 * _loginButton = new Button("Login");
	 * _loginButton.setStyleName("loginButton"); _alert = new Label();
	 * _main.add(_alert); _main.add(_loginButton);
	 * _loginButton.addClickListener(new LoginButton(f));
	 */

	// Window.open("://localhost:8443/cas/?service=//localhost:8080/webapp/Fears.html",
	// "_self", "");

	// Frame iframe =new
	// Frame("//localhost:8443/cas/?service=//localhost:8080/webapp/Fears.html");
	// iframe.setSize("507px", "450px");
	// _main.add(iframe);

	/*
	 * EventManager.addListener(iframe.getElement(), "load", new
	 * EventCallback() { public void execute(EventObject e) {
	 * RootPanel.get().add(new HTML("Evento:" + e.toString()));
	 * 
	 * } }, new ListenerConfig());
	 */

    }

}
