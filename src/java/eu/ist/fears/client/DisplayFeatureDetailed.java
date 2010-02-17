package eu.ist.fears.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.common.communication.Communication;
import eu.ist.fears.common.exceptions.FearsAsyncCallback;
import eu.ist.fears.common.views.ViewFeatureDetailed;
import eu.ist.fears.client.interfaceweb.FeatureDetailedWidget;

public class DisplayFeatureDetailed extends Composite {

    protected VerticalPanel content;
    protected String projectID;
    protected String featureID;
    protected Communication com;
    protected FeatureDetailedWidget feature;

    public DisplayFeatureDetailed(String projectID, String featureID) {

	this.projectID = projectID;
	this.featureID = featureID;
	Fears.getPath().setFeature("", projectID, "Sugestão", "");
	com = new Communication("service");
	content = new VerticalPanel();
	initWidget(content);
	update();
    }

    protected void update() {
	com.getFeature(projectID, featureID, Cookies.getCookie("fears"), new FeatureCB(this));
    }

    public void updateUserInfo() {
	if (feature == null)
	    return;
	feature.updateUserInfo();
    }

    public void updateFeature(ViewFeatureDetailed view) {
	if (feature == null) {
	    feature = new FeatureDetailedWidget(view, new FeatureCB(DisplayFeatureDetailed.this));
	    feature.setStylePrimaryName("featureDetailed");
	    content.add(feature);

	} else {
	    feature.update(view);
	}

	Fears.getPath().setFeature(view.getProjectName(), new Integer(view.getProjectID()).toString(), "Sugestão",
		feature.getFeatureName());
    }
    // TODO: The code here must have some kind of bug, either on the widget or on the FeatureCB class
    protected class FeatureCB extends FearsAsyncCallback<Object> {
	DisplayFeatureDetailed _f;

	public FeatureCB(DisplayFeatureDetailed f) {
	    _f = f;
	}

	public void onSuccess(Object result) {

	    ViewFeatureDetailed featureView = (ViewFeatureDetailed) result;
	    if (featureView == null) {
		content.clear();
		content.add(new Label("A sugestao nao existe."));
	    } else {
		_f.updateFeature(featureView);
		Fears.getHeader().update(projectID, DisplayFeatureDetailed.this);
	    }

	}

    }
}
