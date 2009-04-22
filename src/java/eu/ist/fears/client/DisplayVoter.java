package eu.ist.fears.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.client.common.communication.Communication;
import eu.ist.fears.client.common.exceptions.ExceptionsTreatment;
import eu.ist.fears.client.common.views.ViewFeatureResume;
import eu.ist.fears.client.common.views.ViewVoterDetailed;

public class DisplayVoter extends Composite {

	private Communication _com;
	private String _projectID;
	private VerticalPanel _content;
	private Label _voterUsername;
	private String _voterNick;
	private String _projectName;

	public DisplayVoter(String projectId, String voterName){

		_projectID=projectId;
		_com = new Communication("service");
		_voterUsername = new Label(voterName);
		_content = new VerticalPanel();

		//Fears.getPath().setVoter("", projectId, voterName);

		updateProjectName();
		initWidget(_content);	
		update();

	}

	protected void update(){
		_com.getVoter(_projectID, _voterUsername.getText() , Cookies.getCookie("fears"), new VoterCB());
	}

	protected void updateVoter(ViewVoterDetailed u){
		_content.clear();
		Label votedLabel = new HTML("Votou nas Sugest&otilde;es:");
		votedLabel.setStyleName("UserSubTitle");
		_content.add(votedLabel);
		_voterNick = u.getNick();
		Fears.getPath().setVoter(_projectName, _projectID, _voterNick);

		int i=0;
		if(u.getVotedFeatures()!=null){
			Grid votedFeatureTable = new Grid(u.getVotedFeatures().size(), 4);
			for(ViewFeatureResume f : u.getVotedFeatures()){
				HTML date = new HTML(f.getCreatedDate());
				votedFeatureTable.setWidget(i,0,date);
				date.setStyleName("UserFeature");
				
				HorizontalPanel projPanel = new HorizontalPanel();
				projPanel.add(new HTML("&nbsp;"));
				Hyperlink project= new Hyperlink(f.getName(),"Project"+_projectID+"&"+"viewFeature"+f.getFeatureID());
				projPanel.add(project);
				project.setStyleName("UserFeature");
				votedFeatureTable.setWidget(i,1,projPanel);

				HorizontalPanel temp = new HorizontalPanel();
				temp.add(new HTML("&nbsp;|&nbsp;"));
				temp.add(new Label(new Integer(f.getVotes()).toString()));
				temp.add(new HTML("&nbsp;votos&nbsp;"));
				votedFeatureTable.setWidget(i,2,temp);
				temp.setStyleName("UserFeature");

				HorizontalPanel temp2 = new HorizontalPanel();
				temp2.add(new HTML("|&nbsp;"));
				temp2.add(new Label(new Integer(f.getNComments()).toString()));
				temp2.add(new HTML("&nbsp;comentarios"));
				temp2.setStyleName("UserFeature");
				votedFeatureTable.setWidget(i,3,temp2);

				i++;
			}
			_content.add(votedFeatureTable);
		}

		Label createdLabel = new HTML("Criou as Sugest&otilde;es:");
		createdLabel.setStyleName("UserSubTitle");
		_content.add(createdLabel);


		if(u.getCreatedFeatures()!=null){
			Grid createdFeatureTable = new Grid(u.getCreatedFeatures().size(), 4);
			i=0;
			for(ViewFeatureResume f : u.getCreatedFeatures()){
				HTML date = new HTML(f.getCreatedDate());
				createdFeatureTable.setWidget(i,0,date);
				date.setStyleName("UserFeature");
				
				HorizontalPanel projPanel = new HorizontalPanel();
				projPanel.add(new HTML("&nbsp;"));
				Hyperlink project= new Hyperlink(f.getName(),"Project"+_projectID+"&"+"viewFeature"+f.getFeatureID());
				projPanel.add(project);
				project.setStyleName("UserFeature");
				createdFeatureTable.setWidget(i,1,projPanel);

				HorizontalPanel temp = new HorizontalPanel();
				temp.add(new HTML("&nbsp;|&nbsp;"));
				temp.add(new Label(new Integer(f.getVotes()).toString()));
				temp.add(new HTML("&nbsp;votos&nbsp;"));
				temp.setStyleName("UserFeature");

				createdFeatureTable.setWidget(i,2,temp);

				HorizontalPanel temp2 = new HorizontalPanel();
				temp2.add(new HTML("|&nbsp;"));
				temp2.add(new Label(new Integer(f.getNComments()).toString()));
				temp2.add(new HTML("&nbsp;comentarios"));
				temp2.setStyleName("UserFeature");
				createdFeatureTable.setWidget(i,3,temp2);

				i++;
			}
			_content.add(createdFeatureTable);
		}
	}


	protected void updateProjectName(String name){
		_projectName = name;
		Fears.getPath().setVoter(name, _projectID, _voterNick);
	}

	public void updateProjectName(){
		_com.getProjectName(_projectID, getProjectName);
	}

	AsyncCallback getProjectName = new ExceptionsTreatment() {
		public void onSuccess(Object result){ 
			updateProjectName((String)result);
		}

	};	

	protected class VoterCB extends ExceptionsTreatment{
		//DisplayVoter _v;

		public VoterCB() {
			//_v=v;
		}

		public void onSuccess(Object result){ 

			ViewVoterDetailed userView = (ViewVoterDetailed) result;
			if(userView==null){
				_content.clear();
				_content.add(new Label("O user nao existe."));	
			}else {
				DisplayVoter.this.updateVoter(userView);
			}

		}

	}

}
