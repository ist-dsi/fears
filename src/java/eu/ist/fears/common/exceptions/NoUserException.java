package eu.ist.fears.common.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NoUserException extends FearsException implements IsSerializable {

    private static final long serialVersionUID = -5219981592415619789L;

    protected String projectID;
    protected String userID;

    public NoUserException() {
    }

    public NoUserException(String projectID, String userID) {
	initCause(this);
	this.userID = userID;
	this.projectID = projectID;
    }

    public String getProjectID() {
	return projectID;
    }

    public String getUserID() {
	return userID;
    }

}
