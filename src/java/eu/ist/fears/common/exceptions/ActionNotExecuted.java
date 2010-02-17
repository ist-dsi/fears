package eu.ist.fears.common.exceptions;

public class ActionNotExecuted extends FearsException {
    private static final long serialVersionUID = 8844603987292021237L;
    String error;

    public ActionNotExecuted() {
    }

    public ActionNotExecuted(String error) {
	this.error = error;
    }

    public String getError() {
	return error;
    }

}
