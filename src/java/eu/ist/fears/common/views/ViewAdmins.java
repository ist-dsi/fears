package eu.ist.fears.common.views;

import java.io.Serializable;
import java.util.List;

public class ViewAdmins implements Serializable {

    private static final long serialVersionUID = 3812674061992808340L;

    protected List<java.lang.String> admins;
    protected List<java.lang.String> adminsNicks;
    protected String projectID;
    protected String projectName;

    public ViewAdmins() {
    }

    public ViewAdmins(List<java.lang.String> admins, List<java.lang.String> adminsNick, String projectId, String projectName) {
	this.admins = admins;
	this.adminsNicks = adminsNick;
	projectID = projectId;
	this.projectName = projectName;
    }

    public ViewAdmins(List<java.lang.String> admins, List<java.lang.String> adminsNick) {
	this.admins = admins;
	adminsNicks = adminsNick;
    }

    public List<java.lang.String> getAdmins() {
	return admins;
    }

    public List<java.lang.String> getAdminsNick() {
	return adminsNicks;
    }

    public String getProjectId() {
	return projectID;
    }

    public String getProjectName() {
	return projectName;
    }

}
