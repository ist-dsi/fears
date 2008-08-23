package eu.ist.fears.client;


import java.util.List;
import java.lang.Character;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import eu.ist.fears.client.communication.Communication;
import eu.ist.fears.client.interfaceweb.FeatureResumeWidget;
import eu.ist.fears.client.views.ViewFeatureResume;


public class ListFeatures extends Composite {

	protected Communication _com;
	protected VerticalPanel _sugPanel;
	protected String _projectName;
	protected String _projectID;
	protected Label _stats;
	protected HorizontalPanel _search;
	protected HorizontalPanel _filter;
	protected VerticalPanel _featuresList;
	protected ListBox lb;
	protected TextBox sBox;

	public ListFeatures(String projectID){
		_com= new Communication("service");
		_sugPanel = new VerticalPanel();
		_stats= new Label("Stats....");
		_search = new HorizontalPanel();
		_filter = new HorizontalPanel();
		_featuresList = new VerticalPanel();
		_projectID = projectID;
		_sugPanel.setStyleName("listMain");

		initWidget(_sugPanel);
		_stats.setStyleName("stats");
		_sugPanel.add(_stats);

		HorizontalPanel _searchBox = new HorizontalPanel();
		_searchBox.setStyleName("searchBox");
		_sugPanel.add(_searchBox);
		_search.setStyleName("search");
		_searchBox.add(_search);
		HorizontalPanel filterBox = new HorizontalPanel();
		filterBox.setStyleName("filterBox");
		filterBox.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		_filter.setStyleName("filter");
		filterBox.add(_filter);
		_sugPanel.add(filterBox);
		_featuresList.setStyleName("featuresList");
		_sugPanel.add(_featuresList);

		init();
	}

	private void init(){
		lb = new ListBox();
		sBox = new TextBox();
		
		Fears.getPath().update("",new Integer(_projectID).intValue(), null, false);
		
		_search.clear();

		_search.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
		
		sBox.setVisibleLength(50);
		sBox.setStyleName("searchTextBox");
		_search.add(sBox);
		sBox.addKeyboardListener(new KeyboardListener(){
			
			public void onKeyPress(Widget arg0, char arg1, int arg2){
				if(arg1==13){
					_com.search(_projectID, sBox.getText(), lb.getItemText(lb.getSelectedIndex()), 0, Cookies.getCookie("Fears"), getFeaturesCB);
				}
			}

			public void onKeyDown(Widget arg0, char arg1, int arg2){}
			public void onKeyUp(Widget arg0, char arg1, int arg2){}			
		});
		
		


		PushButton searchButton = new PushButton(new Image("button01.gif",0,0,105,32),new Image("button01.gif",-2,-2,105,32));
		_search.add(searchButton);
		searchButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				_com.search(_projectID, sBox.getText(), lb.getItemText(lb.getSelectedIndex()), 0, Cookies.getCookie("Fears"), getFeaturesCB);
			}
		});
		

		PushButton addButton = new PushButton(new Image("button02.gif",0,0,135,32),new Image("button02.gif",-2,-2,135,32)); 
		_search.add(addButton);
		addButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				History.newItem("Project" + _projectID + "?" + "addFeature");

			}
		});

		_filter.clear();
		_filter.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		_filter.add(new Label ("(Varios Filtros ...)"));

		_filter.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		
		lb.addItem("Ordenar por Votos");
		lb.addItem("Ordenar por Data");
		lb.setVisibleItemCount(1);
		_filter.add(lb);

	}

	public void update(){
		_com.getFeatures(_projectID, Cookies.getCookie("fears"), getFeaturesCB);
		_com.getProjectName(_projectID, getProjectName);
	}
	
	protected void updateProjectName(String name){
		Fears.getPath().update(name,new Integer(_projectID).intValue(), null, false);
	}


	public void updateFeatures(List features){
		_featuresList.clear();

		if(features==null || features.size() ==0 ){
			_featuresList.add(new Label("Nao ha Sugestoes."));
			return;
		}

		for(int i=0;i< features.size();i++){
			FeatureResumeWidget feature =new FeatureResumeWidget((ViewFeatureResume)features.get(i), null);
			feature.setStylePrimaryName("feature");
			_featuresList.add(feature);
		}
	}

	AsyncCallback getFeaturesCB = new AsyncCallback() {
		public void onSuccess(Object result){ 
			updateFeatures((List)result);
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Nao foi possivel contactar o servidor."));
		}
	};

	AsyncCallback getProjectName = new AsyncCallback() {
		public void onSuccess(Object result){ 
			updateProjectName((String)result);
		}

		public void onFailure(Throwable caught) {
			RootPanel.get().add(new Label("Nao foi possivel contactar o servidor."));
		}
	};
	
}
