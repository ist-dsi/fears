package eu.ist.fears.common.views;

import java.io.Serializable;
import java.util.List;

public class ViewVoterDetailed extends ViewVoterResume implements Serializable {

    private static final long serialVersionUID = -5835833915085305137L;
    List<ViewFeatureResume> created;
    List<ViewFeatureResume> voted;
    String projectID;
    String projectName;
    String oid;

    public ViewVoterDetailed() {
    }

    public ViewVoterDetailed(String name, String nick, String oid, List<ViewFeatureResume> created,
	    List<ViewFeatureResume> voted, String projID, String projName) {
	super(name, nick, oid, false);
	this.created = created;
	this.voted = voted;
	projectID = projID;
	projectName = projName;
    }

    public List<ViewFeatureResume> getCreatedFeatures() {
	return created;
    }

    public List<ViewFeatureResume> getVotedFeatures() {
	return voted;
    }

    public String getProjectID() {
	return projectID;
    }

    public String getProjectName() {
	return projectName;
    }

}
