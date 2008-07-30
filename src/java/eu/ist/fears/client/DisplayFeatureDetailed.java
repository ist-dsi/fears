package eu.ist.fears.client;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import eu.ist.fears.client.communication.Communication;
import eu.ist.fears.client.interfaceweb.FeatureDetailedWidget;
import eu.ist.fears.client.views.ViewFeatureDetailed;

public class DisplayFeatureDetailed extends Composite {

	protected VerticalPanel _content;
	protected String _projectID;
	protected String _projectName;
	protected String _featureName;
	protected Communication _com;
	protected FeatureDetailedWidget _feature;

	public DisplayFeatureDetailed(String projectID, String featureName){

		_projectID = projectID;
		_featureName = featureName;
		Fears.setPath("",new Integer(projectID).intValue(), "Sugest&atilde;o", true);
		_com = new Communication("service");
		_content= new VerticalPanel();
		initWidget(_content);
		update();
	}

	protected void update(){
		_com.getFeature(_projectID, _featureName, Cookies.getCookie("fears"), new FeatureCB(this));
	}
	
	public void updateFeature(ViewFeatureDetailed view){
		if(_feature==null){
			_feature= new FeatureDetailedWidget(view, new FeatureCB(DisplayFeatureDetailed.this));
			_feature.setStylePrimaryName("featureDetailed");
			_content.add(_feature);
		}
		else _feature.update(view);
	}

	protected class FeatureCB implements AsyncCallback{
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
			}

		}
	
	public void onFailure(Throwable caught) {
			_content.clear();
			_content.add((new Label("A Sugestao " + _featureName + " nao foi encontrada.")));
			try {
				throw caught;
			} catch(RuntimeException e){
				_content.add(new Label("Erro:"+e.getMessage()));

			} catch (Throwable e) {
				_content.add(new Label(e.getMessage()));
			}

		}
		
	}
}
