package eu.ist.fears.common.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NoProjectException extends FearsException implements IsSerializable {
    private static final long serialVersionUID = 6362589945948932611L;

    protected String projID;

    public NoProjectException() {
    }

    public NoProjectException(String projectID) {
	projID = projectID;
    }

    public String getProjectID() {
	return projID;
    }

}
