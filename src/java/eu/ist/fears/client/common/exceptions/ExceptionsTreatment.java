package eu.ist.fears.client.common.exceptions;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ist.fears.client.Fears;

public abstract class ExceptionsTreatment implements AsyncCallback {

	
	public void onFailure(Throwable caught) {
		if(caught instanceof NoProjectException) {
			Fears.setError(new HTML("O projecto n&ordm; " + ((NoProjectException)caught).getProjectID() + " n&atilde;o existe."));
		}else if (caught instanceof NoFeatureException) {
			Fears.setError(new HTML("A sugest&atilde;o n&ordm; " + ((NoFeatureException)caught).getFeatureID() + " n&atilde;o existe."));
		}else if  (caught instanceof NoUserException) {
			Fears.setError(new HTML("O utilizador" + ((NoUserException)caught).getUserID() + " n&atilde;o existe."));
		}else if (caught instanceof FearsException){
			Fears.setError(new HTML(((FearsException)caught).getError()));
		}else if (caught instanceof Throwable){
			Fears.setError(new HTML("Erro:" + caught.getMessage()));
		}
		
	}

	

}
