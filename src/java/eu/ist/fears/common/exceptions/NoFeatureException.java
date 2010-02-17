package eu.ist.fears.common.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NoFeatureException extends FearsException implements IsSerializable {
    private static final long serialVersionUID = 5913900065751622271L;
    protected String featureID;
    protected String projID;

    public NoFeatureException() {
    }

    public NoFeatureException(String projID, String featureID) {
	this.projID = projID;
	this.featureID = featureID;
    }

    public String getFeatureID() {
	return featureID;
    }

    public String getProjectID() {
	return projID;
    }

}
