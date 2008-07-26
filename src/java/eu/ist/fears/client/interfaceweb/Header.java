package eu.ist.fears.client.interfaceweb;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;

import eu.ist.fears.client.Fears;

public class Header extends Composite {
	
	protected Hyperlink sessionLink; 
	protected Label userName;
	protected HorizontalPanel _headerBox; 
	
	public Header(String username,boolean loggedIn ){
		_headerBox = new HorizontalPanel();
		_headerBox.setStyleName("headerBox");
		
		HorizontalPanel left = new HorizontalPanel();
		HorizontalPanel right = new HorizontalPanel();
		HorizontalPanel header = new HorizontalPanel();
		userName = new Label();
		sessionLink = new Hyperlink();
		header.setStyleName("header");
		_headerBox.add(header);
		header.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		header.add(left);
		header.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		header.add(right);
		left.setStyleName("left");
		left.add(new Label("fears | feature request system |"));
		left.add(new HTML("&nbsp;<a href=\"Admin.html\">Admin</a>"));
		right.setStyleName("right");
		right.add(new HTML("Bem-vindo&nbsp;"));
		right.add(userName);
		right.add(new HTML("&nbsp;|&nbsp;"));
		right.add(sessionLink);
		
		
		initWidget(_headerBox);
		update();
	}
	
	public void update(){
		userName.setText(Fears.getUsername());	
		if(!Fears.isLogedIn()){
			sessionLink.setText("Login");
			sessionLink.setTargetHistoryToken("login");
		}else{
			sessionLink.setText("Logout");
			sessionLink.setTargetHistoryToken("logout");
		}
	}
	
	
	
}
