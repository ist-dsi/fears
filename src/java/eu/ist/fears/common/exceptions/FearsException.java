package eu.ist.fears.common.exceptions;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FearsException extends Exception implements IsSerializable {

    private static final long serialVersionUID = -1218590696230205271L;
    String error;

    public FearsException() {
    }

    public FearsException(String error) {
	this.error = error;
    }

    public String getError() {
	return error;
    }

}
