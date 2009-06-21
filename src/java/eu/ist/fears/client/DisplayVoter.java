package eu.ist.fears.client;

import java.util.List;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosureEvent;
import com.google.gwt.user.client.ui.DisclosureHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
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

    public DisplayVoter(String projectId, String voterName){

        _projectID=projectId;
        _com = new Communication("service");
        _voterUsername = new Label(voterName);
        _content = new VerticalPanel();

        //updateProjectName();

        initWidget(_content);	
        update();

    }

    protected void update(){
        _com.getVoter(_projectID, _voterUsername.getText() , Cookies.getCookie("fears"), new VoterCB());
    }

    protected void updateVoter(List<ViewVoterDetailed> voterProjects){
        _content.clear();

        _voterNick = voterProjects.get(0).getNick();
        Fears.getPath().setVoter(_voterNick);

        for(int i=0; i< voterProjects.size();i++){
            ViewVoterDetailed u = voterProjects.get(i);
            _voterNick = u.getNick();

            DisclosurePanel _voterProject =  new DisclosurePanel();
            _content.add(_voterProject);
            _voterProject.setStyleName("voterProject");
      
            HorizontalPanel projectTitleBox = new HorizontalPanel();
            projectTitleBox.setStyleName("ProjectTitleinVoterBox");
            _voterProject.setHeader(projectTitleBox);
            
            Label projectTitle = new Label(u.getProjectName());
            projectTitleBox.add(projectTitle);
            projectTitle.setStyleName("ProjectTitleinVoter");
            final HTML showhideMessage = new HTML("&nbsp;&nbsp;Mostrar mais »");
            
            _voterProject.addEventHandler(new DisclosureHandler(){
                public void onClose(DisclosureEvent event){
                    showhideMessage.setHTML("&nbsp;&nbsp;Mostrar mais »");
                }
                public void onOpen(DisclosureEvent event){
                    showhideMessage.setHTML("&nbsp;&nbsp;Esconder «");
                }
            });
            
            if(_projectID==u.getProjectID() || _projectID==null)
                _voterProject.setOpen(true);
            
            projectTitleBox.add(showhideMessage);
            
            VerticalPanel _projectContent = new VerticalPanel();
            _voterProject.setContent(_projectContent);
           
            Label votedLabel = new Label("");
            votedLabel.setStyleName("UserSubTitle");
            _projectContent.add(votedLabel);
            
            if(u.getVotedFeatures()==null || u.getVotedFeatures().size()==0)
                votedLabel.setText("Não votou em nenhuma sugestão.");
            else{
                votedLabel.setText("Votou nas Sugestões:");
                int j=0;
                Grid votedFeatureTable = new Grid(u.getVotedFeatures().size(), 4);
                votedFeatureTable.setStyleName("voterGrid");
                for(ViewFeatureResume f : u.getVotedFeatures()){
                    HTML date = new HTML(f.getCreatedDate());
                    votedFeatureTable.setWidget(j,0,date);
                    date.setStyleName("UserFeature");

                    HorizontalPanel projPanel = new HorizontalPanel();
                    projPanel.add(new HTML("&nbsp;"));
                    Hyperlink project= new Hyperlink(f.getName(),"Project"+f.getProjectID()+"&"+"viewFeature"+f.getFeatureID());
                    projPanel.add(project);
                    project.setStyleName("UserFeature");
                    votedFeatureTable.setWidget(j,1,projPanel);

                    HorizontalPanel temp = new HorizontalPanel();
                    temp.add(new HTML("&nbsp;|&nbsp;"));
                    temp.add(new Label(new Integer(f.getVotes()).toString()));
                    temp.add(new HTML("&nbsp;votos&nbsp;"));
                    votedFeatureTable.setWidget(j,2,temp);
                    temp.setStyleName("UserFeature");

                    HorizontalPanel temp2 = new HorizontalPanel();
                    temp2.add(new HTML("|&nbsp;"));
                    temp2.add(new Label(new Integer(f.getNComments()).toString()));
                    temp2.add(new HTML("&nbsp;comentarios"));
                    temp2.add(new HTML("&nbsp;|&nbsp;Estado:&nbsp;" + f.getState()));
                    temp2.setStyleName("UserFeature");
                    votedFeatureTable.setWidget(j,3,temp2);

                    j++;
                }
                _projectContent.add(votedFeatureTable);
            }

            Label createdLabel = new Label("");
            createdLabel.setStyleName("UserSubTitle");
            _projectContent.add(createdLabel);

            if(u.getCreatedFeatures()==null || u.getCreatedFeatures().size()==0)
                createdLabel.setText("Não criou nenhuma sugestão.");
            else{
                createdLabel.setText("Criou as Sugestões:");
                Grid createdFeatureTable = new Grid(u.getCreatedFeatures().size(), 4);
                createdFeatureTable.setStyleName("voterGrid");
                int j=0;
                for(ViewFeatureResume f : u.getCreatedFeatures()){
                    HTML date = new HTML(f.getCreatedDate());
                    createdFeatureTable.setWidget(j,0,date);
                    date.setStyleName("UserFeature");

                    HorizontalPanel projPanel = new HorizontalPanel();
                    projPanel.add(new HTML("&nbsp;"));
                    Hyperlink project= new Hyperlink(f.getName(),"Project"+f.getProjectID()+"&"+"viewFeature"+f.getFeatureID());
                    projPanel.add(project);
                    project.setStyleName("UserFeature");
                    createdFeatureTable.setWidget(j,1,projPanel);

                    HorizontalPanel temp = new HorizontalPanel();
                    temp.add(new HTML("&nbsp;|&nbsp;"));
                    temp.add(new Label(new Integer(f.getVotes()).toString()));
                    temp.add(new HTML("&nbsp;votos&nbsp;"));
                    temp.setStyleName("UserFeature");

                    createdFeatureTable.setWidget(j,2,temp);

                    HorizontalPanel temp2 = new HorizontalPanel();
                    temp2.add(new HTML("|&nbsp;"));
                    temp2.add(new Label(new Integer(f.getNComments()).toString()));
                    temp2.add(new HTML("&nbsp;comentarios"));
                    temp2.add(new HTML("&nbsp;|&nbsp;Estado:&nbsp;" + f.getState()));
                    temp2.setStyleName("UserFeature");
                    createdFeatureTable.setWidget(j,3,temp2);

                    j++;
                }			
                _projectContent.add(createdFeatureTable);	
            }

        }
    }


    /*protected void updateProjectName(String name){
		_projectName = name;
		Fears.getPath().setVoter(_voterNick);
	}

	public void updateProjectName(){
		_com.getProjectName(_projectID, getProjectName);
	}

	AsyncCallback getProjectName = new ExceptionsTreatment() {
		public void onSuccess(Object result){ 
			updateProjectName((String)result);
		}

	};	*/

    protected class VoterCB extends ExceptionsTreatment {
        //DisplayVoter _v;

        public VoterCB() {
            //_v=v;
        }

        public void onSuccess(Object result){ 

            List<ViewVoterDetailed> userView = (List<ViewVoterDetailed>)result;
            if(userView==null){
                _content.clear();
                _content.add(new Label("O utilizador nao existe."));	
            }else {
                DisplayVoter.this.updateVoter(userView);
            }

        }

    }

}
