package eu.ist.fears.client.interfaceweb;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;


import eu.ist.fears.client.DisplayFeatureDetailed;
import eu.ist.fears.client.Fears;
import eu.ist.fears.client.common.FearsConfig;
import eu.ist.fears.client.common.communication.Communication;
import eu.ist.fears.client.common.exceptions.ExceptionsTreatment;
import eu.ist.fears.client.common.views.ViewVoterResume;

public class Header extends Composite {

	protected HTML sessionLink; 
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
		_adminLink = new HTML("&nbsp;<a href=\"Admin.html\">Admin</a>&nbsp;|&nbsp;");
		_adminAdministrators = new Hyperlink("","");

		HorizontalPanel left = new HorizontalPanel();
		HorizontalPanel right = new HorizontalPanel();
		HorizontalPanel header = new HorizontalPanel();
		userName = new Label();
		sessionLink = new HTML();
		header.setStyleName("header");
		_headerBox.add(header);
		header.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		header.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		header.add(left);
		header.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		header.add(right);
		left.setStyleName("left");
		left.add(new Image("ist_01.gif"));
		left.add(new HTML("&nbsp;&nbsp;<a href=\"index.html\">fears</a> |"));

		left.add(_adminLink);
		left.add(_adminAdministrators);

		right.setStyleName("right");
		right.add(new HTML("Bem-vindo&nbsp;"));
		right.add(userName);
		right.add(new HTML("&nbsp;|&nbsp;"));
		right.add(_votes);
		HorizontalPanel help = new HorizontalPanel();
		help.add(new Hyperlink("Ajuda","help"));
		help.add(new HTML("&nbsp;|&nbsp;"));
		right.add(help);
		right.add(sessionLink);


		initWidget(_headerBox);
		update(false, admin);
	}

	public void update(boolean inProjectPage, boolean adminPage){

		userName.setText(Fears.getNickname());	

		if(!Fears.isLogedIn()){
			_votes.setText("");
			if(adminPage)
				sessionLink.setHTML("<a href=\""+ FearsConfig.getCasUrl() + "?service=" +GWT.getModuleBaseURL()+"Admin.html\">login</a>");
			else
				sessionLink.setHTML("<a href=\""+ FearsConfig.getCasUrl() + "?service=" +GWT.getModuleBaseURL()+"Fears.html\">login</a>");

		}else{
			if(inProjectPage)
				_votes.setHTML("Tem <b>" + Fears.getVotesLeft() + "</b> votos dispon&iacute;veis&nbsp;|&nbsp;");
			else _votes.setText("");
			if(adminPage && Fears.isAdminUser())
				sessionLink.setHTML("<a href=\"" + FearsConfig.getCasUrl() + "logout\">logout</a>");
			else
				sessionLink.setHTML("<a href=\"" + FearsConfig.getCasUrl() + "logout\">logout</a>");
		}

		if(adminPage && Fears.isAdminUser()){
			_adminAdministrators.setText("Administradores");
			_adminAdministrators.setTargetHistoryToken("admins");	
		}else {
			_adminAdministrators.setText("");
		}

	}

	public void update(String projectID, DisplayFeatureDetailed d){
		_com.getCurrentVoter(projectID, Cookies.getCookie("fears"), new GetCurrentVoter(d));
	}

	public void update(String projectID, List<FeatureResumeWidget> f){
		_com.getCurrentVoter(projectID, Cookies.getCookie("fears"), new GetCurrentVoter(f));
	}

	public void update(String projectID){
		_com.getCurrentVoter(projectID, Cookies.getCookie("fears"), new GetCurrentVoter());
	}


	protected class GetCurrentVoter extends ExceptionsTreatment{
		DisplayFeatureDetailed _d;
		List<FeatureResumeWidget> _f;

		public GetCurrentVoter(){
		}

		public GetCurrentVoter(DisplayFeatureDetailed d){
			_d=d;
		}

		public GetCurrentVoter(List<FeatureResumeWidget> f){
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

				if(_f!=null && _f.size()>0){
					for(FeatureResumeWidget f : _f ){
						f.updateUserInfo();
					}

				}
			}
			else {
				Fears.validCookie= false;
				Header.this.update(false, Fears.isAdminPage());
			}
		}


	};


}
