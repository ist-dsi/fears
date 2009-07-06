package eu.ist.fears.client;


import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import eu.ist.fears.common.communication.Communication;
import eu.ist.fears.common.exceptions.ExceptionsTreatment;
import eu.ist.fears.common.views.ViewFeatureDetailed;
import eu.ist.fears.client.interfaceweb.FeatureDetailedWidget;

public class DisplayFeatureDetailed extends Composite {

	protected VerticalPanel _content;
	protected String _projectID;
	protected String _featureID;
	protected Communication _com;
	protected FeatureDetailedWidget _feature;

	public DisplayFeatureDetailed(String projectID, String featureID){

		_projectID = projectID;
		_featureID= featureID;
		Fears.getPath().setFeature("", projectID, "Sugestão", "");
		_com = new Communication("service");
		_content= new VerticalPanel();
		initWidget(_content);
		update();
	}

	protected void update(){
		_com.getFeature(_projectID, _featureID , Cookies.getCookie("fears"), new FeatureCB(this));
	}
	
	public void updateUserInfo(){
		if(_feature==null)
			return;
		_feature.updateUserInfo();
	}
	
	public void updateFeature(ViewFeatureDetailed view){
		if(_feature==null){
			_feature= new FeatureDetailedWidget(view, new FeatureCB(DisplayFeatureDetailed.this));
			_feature.setStylePrimaryName("featureDetailed");
			_content.add(_feature);
			
		}
		else {
			_feature.update(view);
		}
		
		Fears.getPath().setFeature(view.getProjectName(),new Integer(view.getProjectID()).toString(), "Sugestão", _feature.getFeatureName());
	}

	protected class FeatureCB extends ExceptionsTreatment{
		DisplayFeatureDetailed _f;
		
		public FeatureCB(DisplayFeatureDetailed f) {
			_f=f;
		}
		
		public void onSuccess(Object result){ 

			ViewFeatureDetailed featureView = (ViewFeatureDetailed) result;
			if(featureView==null){
				_content.clear();
				_content.add(new Label("A sugestao nao existe."));	
			}else {
				_f.updateFeature(featureView);
				Fears.getHeader().update(_projectID,DisplayFeatureDetailed.this);
			}

		}
				
	}
}
