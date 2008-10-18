package eu.ist.fears.client.interfaceweb;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ist.fears.client.DisplayFeatureDetailed;
import eu.ist.fears.client.Fears;
import eu.ist.fears.client.common.communication.Communication;
import eu.ist.fears.client.common.exceptions.ExceptionsTreatment;
import eu.ist.fears.client.common.views.ViewVoterResume;

public class Header extends Composite {

	protected Hyperlink sessionLink; 
	protected Label userName;
	protected HorizontalPanel _headerBox; 
	protected HTML _adminLink;
	protected Hyperlink _adminAdministrators;
	protected Communication _com;
	protected HTML _votes;

	public Header(String username,boolean loggedIn, boolean admin){
		_com=new Communication("service");
		_votes= new HTML();
		_headerBox = new HorizontalPanel();
		_headerBox.setStyleName("headerBox");
		_adminLink = new HTML("&nbsp;<a href=\"Admin.html\">Admin</a>");
		_adminAdministrators = new Hyperlink("","");

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
		left.add(new HTML("<a href=\"Fears.html\">fears</a> |"));

		left.add(_adminLink);
		left.add(new HTML("&nbsp;|&nbsp;"));
		left.add(_adminAdministrators);
		right.setStyleName("right");
		right.add(_votes);
		right.add(new HTML("Bem-vindo&nbsp;"));
		right.add(userName);
		right.add(new HTML("&nbsp;|&nbsp;"));
		right.add(sessionLink);


		initWidget(_headerBox);
		update(false, admin);
	}

	public void update(boolean inProjectPage, boolean adminPage){

		userName.setText(Fears.getUsername());	

		if(!Fears.isLogedIn()){
			_votes.setText("");
			sessionLink.setText("Login");
			sessionLink.setTargetHistoryToken("login");
		}else{
			if(inProjectPage)
				_votes.setHTML("Tem " + Fears.getVotesLeft() + " votos. |&nbsp;");
			else _votes.setText("");
			sessionLink.setText("Logout");
			sessionLink.setTargetHistoryToken("logout");
		}

		if(adminPage){
			_adminAdministrators.setText("Administradores");
			_adminAdministrators.setTargetHistoryToken("admins");
		}else {
			_adminLink.setHTML("&nbsp;<a href=\"Admin.html\">Admin</a>");
			_adminAdministrators.setText("");
		}

	}

	public void update(String projectID, DisplayFeatureDetailed d){
		_com.getCurrentVoter(projectID, Cookies.getCookie("fears"), new GetCurrentVoter(d));
	}
	
	public void update(String projectID, FeatureResumeWidget f){
		_com.getCurrentVoter(projectID, Cookies.getCookie("fears"), new GetCurrentVoter(f));
	}
	
	public void update(String projectID){
		_com.getCurrentVoter(projectID, Cookies.getCookie("fears"), new GetCurrentVoter());
	}

	
	protected class GetCurrentVoter extends ExceptionsTreatment{
		DisplayFeatureDetailed _d;
		FeatureResumeWidget _f;
		
		public GetCurrentVoter(){
		}
		
		public GetCurrentVoter(DisplayFeatureDetailed d){
			_d=d;
		}
		
		public GetCurrentVoter(FeatureResumeWidget f){
			RootPanel.get().add(new Label("A actualizar votos da Feature Detailed"));
			_f=f;
		}

		public void onSuccess(Object result){
			ViewVoterResume voter = (ViewVoterResume) result;
			if(voter!=null){
				Fears.validCookie= true;
				Fears.setCurrentUser(voter);
				Header.this.update(true, Fears.isAdminPage());
				if(_d!=null){
					_d.updateUserInfo();
				}
				if(_f!=null){
					//_f.updateUserInfo();
					RootPanel.get().add(new Label("actualizado Feature Detailed"));	
				}
			}
			else {
				Fears.validCookie= false;
				Header.this.update(false, Fears.isAdminPage());
			}
		}

		
	};


}
