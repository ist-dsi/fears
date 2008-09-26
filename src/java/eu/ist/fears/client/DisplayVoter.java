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

import eu.ist.fears.client.DisplayFeatureDetailed.FeatureCB;
import eu.ist.fears.client.communication.Communication;
import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewFeatureResume;
import eu.ist.fears.client.views.ViewUserDetailed;

public class DisplayVoter extends Composite {

	private Communication _com;
	private String _projectID;
	private VerticalPanel _content;
	private Label _voterName;

	public DisplayVoter(String projectId, String voterName){

		_projectID=projectId;
		_com = new Communication("service");
		_voterName = new Label(voterName);
		_content = new VerticalPanel();

		Fears.getPath().setVoter("", projectId, voterName);

		updateProjectName();
		initWidget(_content);	
		update();

	}

	protected void update(){
		_com.getVoter(_projectID, _voterName.getText() , Cookies.getCookie("fears"), new VoterCB());
	}

	protected void updateVoter(ViewUserDetailed u){
		_content.clear();
		Label votedLabel = new HTML("Votou nas Sugest&otilde;es:");
		votedLabel.setStyleName("UserSubTitle");
		_content.add(votedLabel);

		int i=0;
		if(u.getVotedFeatures()!=null){
			Grid votedFeatureTable = new Grid(u.getVotedFeatures().size(), 3);
			for(ViewFeatureResume f : u.getVotedFeatures()){
				Hyperlink project= new Hyperlink(f.getName(),"Project"+_projectID+"?"+"viewFeature"+f.getFeatureID());
				project.setStyleName("UserFeature");
				votedFeatureTable.setWidget(i,0,project);

				HorizontalPanel temp = new HorizontalPanel();
				temp.add(new HTML("&nbsp;|&nbsp;"));
				temp.add(new Label(new Integer(f.getVotes()).toString()));
				temp.add(new HTML("&nbsp;votos&nbsp;"));
				votedFeatureTable.setWidget(i,1,temp);
				temp.setStyleName("UserFeature");

				HorizontalPanel temp2 = new HorizontalPanel();
				temp2.add(new HTML("|&nbsp;"));
				temp2.add(new Label(new Integer(f.getNComments()).toString()));
				temp2.add(new HTML("&nbsp;comentarios"));
				temp2.setStyleName("UserFeature");
				votedFeatureTable.setWidget(i,2,temp2);

				i++;
			}
			_content.add(votedFeatureTable);
		}

		Label createdLabel = new HTML("Criou as Sugest&otilde;es:");
		createdLabel.setStyleName("UserSubTitle");
		_content.add(createdLabel);


		if(u.getVotedFeatures()!=null){
			Grid createdFeatureTable = new Grid(u.getCreatedFeatures().size(), 3);
			i=0;
			for(ViewFeatureResume f : u.getCreatedFeatures()){
				Hyperlink project= new Hyperlink(f.getName(),"Project"+_projectID+"?"+"viewFeature"+f.getFeatureID());
				project.setStyleName("UserFeature");
				createdFeatureTable.setWidget(i,0,project);

				HorizontalPanel temp = new HorizontalPanel();
				temp.add(new HTML("&nbsp;|&nbsp;"));
				temp.add(new Label(new Integer(f.getVotes()).toString()));
				temp.add(new HTML("&nbsp;votos&nbsp;"));
				temp.setStyleName("UserFeature");

				createdFeatureTable.setWidget(i,1,temp);

				HorizontalPanel temp2 = new HorizontalPanel();
				temp2.add(new HTML("|&nbsp;"));
				temp2.add(new Label(new Integer(f.getNComments()).toString()));
				temp2.add(new HTML("&nbsp;comentarios"));
				temp2.setStyleName("UserFeature");
				createdFeatureTable.setWidget(i,2,temp2);

				i++;
			}
			_content.add(createdFeatureTable);
		}
	}


	protected void updateProjectName(String name){
		Fears.getPath().setVoter(name, _projectID, _voterName.getText());
	}

	public void updateProjectName(){
		_com.getProjectName(_projectID, getProjectName);
	}

	AsyncCallback getProjectName = new AsyncCallback() {
		public void onSuccess(Object result){ 
			updateProjectName((String)result);
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Nao foi possivel contactar o servidor."));
		}
	};	

	protected class VoterCB implements AsyncCallback{
		//DisplayVoter _v;

		public VoterCB() {
			//_v=v;
		}

		public void onSuccess(Object result){ 

			ViewUserDetailed userView = (ViewUserDetailed) result;
			if(userView==null){
				_content.clear();
				_content.add(new Label("O user nao existe."));	
			}else {
				DisplayVoter.this.updateVoter(userView);
			}

		}

		public void onFailure(Throwable caught) {
			_content.clear();
			_content.add((new Label("O User " + _voterName.getText() + " nao foi encontrado neste projecto.")));
			try {
				throw caught;
			} catch(RuntimeException e){
				_content.add(new Label("Erro:"+e.getMessage()));

			} catch (Throwable e) {
				_content.add(new Label("Nao foi possivel contactar o servidor."));
			}

		}

	}

}
