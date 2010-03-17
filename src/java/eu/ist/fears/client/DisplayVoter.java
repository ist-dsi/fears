package eu.ist.fears.client;

import java.util.List;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.common.FearsAsyncCallback;
import eu.ist.fears.common.communication.Communication;
import eu.ist.fears.common.views.ViewFeatureResume;
import eu.ist.fears.common.views.ViewVoterDetailed;

public class DisplayVoter extends Composite {

    private Communication com;
    private String projectID;
    private VerticalPanel content;
    private Label voterUsername;
    private String voterNick;

    public DisplayVoter(String projectId, String voterName) {

	projectID = projectId;
	com = new Communication("service");
	voterUsername = new Label(voterName);
	content = new VerticalPanel();

	// updateProjectName();

	initWidget(content);
	update();

    }

    protected void update() {
	com.getVoter(projectID, voterUsername.getText(), Cookies.getCookie("fears"), new VoterCB());
    }

    protected void updateVoter(List<ViewVoterDetailed> voterProjects) {
	content.clear();

	voterNick = voterProjects.get(0).getNick();
	Fears.getPath().setVoter(voterNick);

	for (int i = 0; i < voterProjects.size(); i++) {
	    ViewVoterDetailed u = voterProjects.get(i);
	    voterNick = u.getNick();

	    DisclosurePanel voterProject = new DisclosurePanel();
	    content.add(voterProject);
	    voterProject.setStyleName("voterProject");

	    HorizontalPanel projectTitleBox = new HorizontalPanel();
	    projectTitleBox.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
	    projectTitleBox.setStyleName("ProjectTitleinVoterBox");
	    voterProject.setHeader(projectTitleBox);

	    Label projectTitle = new Label(u.getProjectName());
	    projectTitleBox.add(projectTitle);
	    projectTitle.setStyleName("ProjectTitleinVoter");
	    final HTML showhideMessage = new HTML("&nbsp;&nbsp;Mostrar mais »");

	    voterProject.addOpenHandler(new OpenHandler<DisclosurePanel>() {
		public void onOpen(OpenEvent<DisclosurePanel> event) {
		    showhideMessage.setHTML("&nbsp;&nbsp;Esconder «");
		}
	    });

	    voterProject.addCloseHandler(new CloseHandler<DisclosurePanel>() {

		public void onClose(CloseEvent<DisclosurePanel> event) {
		    showhideMessage.setHTML("&nbsp;&nbsp;Mostrar mais »");
		}
	    });

	    if (projectID == u.getProjectID() || projectID == null)
		voterProject.setOpen(true);

	    projectTitleBox.add(showhideMessage);

	    VerticalPanel projectContent = new VerticalPanel();
	    voterProject.setContent(projectContent);

	    Label votedLabel = new Label("");
	    votedLabel.setStyleName("UserSubTitle");
	    projectContent.add(votedLabel);

	    if (u.getVotedFeatures() == null || u.getVotedFeatures().size() == 0)
		votedLabel.setText("Não votou em nenhuma sugestão.");
	    else {
		votedLabel.setText("Votou nas Sugestões:");
		int j = 0;
		Grid votedFeatureTable = new Grid(u.getVotedFeatures().size(), 4);
		votedFeatureTable.setStyleName("voterGrid");
		for (ViewFeatureResume f : u.getVotedFeatures()) {
		    HTML date = new HTML(f.getCreatedDate());
		    votedFeatureTable.setWidget(j, 0, date);
		    date.setStyleName("UserFeature");

		    HorizontalPanel projPanel = new HorizontalPanel();
		    projPanel.add(new HTML("&nbsp;"));
		    Hyperlink project = new Hyperlink(f.getName(), "Project" + f.getProjectID() + "&" + "viewFeature"
			    + f.getFeatureID());
		    projPanel.add(project);
		    project.setStyleName("UserFeature");
		    votedFeatureTable.setWidget(j, 1, projPanel);

		    HorizontalPanel temp = new HorizontalPanel();
		    temp.add(new HTML("&nbsp;|&nbsp;"));
		    temp.add(new Label(new Integer(f.getVotes()).toString()));
		    temp.add(new HTML("&nbsp;votos&nbsp;"));
		    votedFeatureTable.setWidget(j, 2, temp);
		    temp.setStyleName("UserFeature");

		    HorizontalPanel temp2 = new HorizontalPanel();
		    temp2.add(new HTML("|&nbsp;"));
		    temp2.add(new Label(new Integer(f.getNComments()).toString()));
		    temp2.add(new HTML("&nbsp;comentarios"));
		    temp2.add(new HTML("&nbsp;|&nbsp;Estado:&nbsp;" + f.getState()));
		    temp2.setStyleName("UserFeature");
		    votedFeatureTable.setWidget(j, 3, temp2);

		    j++;
		}
		projectContent.add(votedFeatureTable);
	    }

	    Label createdLabel = new Label("");
	    createdLabel.setStyleName("UserSubTitle");
	    projectContent.add(createdLabel);

	    if (u.getCreatedFeatures() == null || u.getCreatedFeatures().size() == 0)
		createdLabel.setText("Não criou nenhuma sugestão.");
	    else {
		createdLabel.setText("Criou as Sugestões:");
		Grid createdFeatureTable = new Grid(u.getCreatedFeatures().size(), 4);
		createdFeatureTable.setStyleName("voterGrid");
		int j = 0;
		for (ViewFeatureResume f : u.getCreatedFeatures()) {
		    HTML date = new HTML(f.getCreatedDate());
		    createdFeatureTable.setWidget(j, 0, date);
		    date.setStyleName("UserFeature");

		    HorizontalPanel projPanel = new HorizontalPanel();
		    projPanel.add(new HTML("&nbsp;"));
		    Hyperlink project = new Hyperlink(f.getName(), "Project" + f.getProjectID() + "&" + "viewFeature"
			    + f.getFeatureID());
		    projPanel.add(project);
		    project.setStyleName("UserFeature");
		    createdFeatureTable.setWidget(j, 1, projPanel);

		    HorizontalPanel temp = new HorizontalPanel();
		    temp.add(new HTML("&nbsp;|&nbsp;"));
		    temp.add(new Label(new Integer(f.getVotes()).toString()));
		    temp.add(new HTML("&nbsp;votos&nbsp;"));
		    temp.setStyleName("UserFeature");

		    createdFeatureTable.setWidget(j, 2, temp);

		    HorizontalPanel temp2 = new HorizontalPanel();
		    temp2.add(new HTML("|&nbsp;"));
		    temp2.add(new Label(new Integer(f.getNComments()).toString()));
		    temp2.add(new HTML("&nbsp;comentarios"));
		    temp2.add(new HTML("&nbsp;|&nbsp;Estado:&nbsp;" + f.getState()));
		    temp2.setStyleName("UserFeature");
		    createdFeatureTable.setWidget(j, 3, temp2);

		    j++;
		}
		projectContent.add(createdFeatureTable);
	    }

	}
    }

    protected class VoterCB extends FearsAsyncCallback<List<ViewVoterDetailed>> {

	public VoterCB() {
	}

	public void onSuccess(List<ViewVoterDetailed> userView) {
	    if (userView == null) {
		content.clear();
		content.add(new Label("O utilizador nao existe."));
	    } else {
		DisplayVoter.this.updateVoter(userView);
	    }

	}

    }

}
