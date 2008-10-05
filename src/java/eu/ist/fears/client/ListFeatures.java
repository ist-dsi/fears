package eu.ist.fears.client;


import java.util.List;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ist.fears.client.common.State;
import eu.ist.fears.client.common.communication.Communication;
import eu.ist.fears.client.common.views.ViewFeatureResume;
import eu.ist.fears.client.interfaceweb.FeatureResumeWidget;


public class ListFeatures extends Composite {

	protected Communication _com;
	protected VerticalPanel _sugPanel;
	protected String _projectName;
	protected String _projectID;
	protected HorizontalPanel _search;
	protected HorizontalPanel _filter;
	protected VerticalPanel _featuresList;
	protected ListBox lb;
	protected TextBox sBox;
	protected String _actualFilter;
	protected Hyperlink[] _filterLinks;

	public ListFeatures(String projectID, String filter){
		_com= new Communication("service");
		_sugPanel = new VerticalPanel();
		_search = new HorizontalPanel();
		_filter = new HorizontalPanel();
		_featuresList = new VerticalPanel();
		_projectID = projectID;
		_sugPanel.setStyleName("listMain");
		_actualFilter = filter;
		initWidget(_sugPanel);

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

		Fears.getPath().setProject("", _projectID);


		_search.clear();

		_search.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);

		sBox.setVisibleLength(50);
		sBox.setStyleName("searchTextBox");
		_search.add(sBox);
		sBox.addKeyboardListener(new KeyboardListener(){

			public void onKeyPress(Widget arg0, char arg1, int arg2){
				if(arg1==13){
					for(int i=1;i<_filterLinks.length;i++ )
						_filterLinks[i].setStyleName("filters");
					_filterLinks[0].setStyleName("currentFilter");
					
					History.newItem("Project"+_projectID, false);
					_com.search(_projectID, sBox.getText(), lb.getItemText(lb.getSelectedIndex()), 0,"" ,Cookies.getCookie("Fears"), getFeaturesCB);
				}
			}

			public void onKeyDown(Widget arg0, char arg1, int arg2){}
			public void onKeyUp(Widget arg0, char arg1, int arg2){}			
		});




		PushButton searchButton = new PushButton(new Image("button01.gif",0,0,105,32),new Image("button01.gif",-2,-2,105,32));
		_search.add(searchButton);
		searchButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				for(int i=1;i<_filterLinks.length;i++ )
					_filterLinks[i].setStyleName("filters");
				_filterLinks[0].setStyleName("currentFilter");
				
				History.newItem("Project"+_projectID, false);
				_com.search(_projectID, sBox.getText(), lb.getItemText(lb.getSelectedIndex()), 0, "",  Cookies.getCookie("Fears"), getFeaturesCB);
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
		HorizontalPanel filtersTab = new HorizontalPanel();
		_filterLinks = new Hyperlink[5];

		_filterLinks[0] = new Hyperlink("Todos","Project"+_projectID);
		if(_actualFilter.equals(""))
			_filterLinks[0] .setStyleName("currentFilter");
		else _filterLinks[0] .setStyleName("filters");
		filtersTab.add(_filterLinks[0]);
		filtersTab.add(new HTML("&nbsp;&nbsp;|&nbsp;&nbsp;"));

		_filterLinks[1]  = new Hyperlink(State.Novo.getHTML()+"s","Project"+_projectID+"?filter"+State.Novo.toString());
		if(_actualFilter.equals(State.Novo.toString()))
			_filterLinks[1] .setStyleName("currentFilter");
		else _filterLinks[1] .setStyleName("filters");
		filtersTab.add(_filterLinks[1] );
		filtersTab.add(new HTML("&nbsp;&nbsp;|&nbsp;&nbsp;"));

		_filterLinks[2] = new Hyperlink(State.Planeado.getHTML()+"s","Project"+_projectID+"?filter"+State.Planeado.toString());
		if(_actualFilter.equals(State.Planeado.toString()))
			_filterLinks[2].setStyleName("currentFilter");
		else _filterLinks[2].setStyleName("filters");
		filtersTab.add(_filterLinks[2]);
		filtersTab.add(new HTML("&nbsp;&nbsp;|&nbsp;&nbsp;"));

		_filterLinks[3]  = new Hyperlink(State.Implementacao.getHTML(),true,"Project"+_projectID+"?filter"+State.Implementacao.toString());
		if(_actualFilter.equals(State.Implementacao.toString()))
			_filterLinks[3].setStyleName("currentFilter");
		else _filterLinks[3].setStyleName("filters");
		filtersTab.add(_filterLinks[3]);
		filtersTab.add(new HTML("&nbsp;&nbsp;|&nbsp;&nbsp;"));

		_filterLinks[4]  = new Hyperlink(State.Completo.getHTML()+"s","Project"+_projectID+"?filter"+State.Completo.toString());
		if(_actualFilter.equals(State.Completo.toString()))
			_filterLinks[4] .setStyleName("currentFilter");
		else _filterLinks[4] .setStyleName("filters");
		filtersTab.add(_filterLinks[4]);

		_filter.add(filtersTab);
		_filter.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);

		lb.addItem("Ordenar por Votos");
		lb.addItem("Ordenar por Data");
		lb.setVisibleItemCount(1);
		lb.addChangeListener(new ChangeListener(){
			public void onChange(Widget arg0) {
				_com.search(_projectID, sBox.getText(), lb.getItemText(lb.getSelectedIndex()), 0,_actualFilter ,Cookies.getCookie("Fears"), getFeaturesCB);
			}

		});
		_filter.add(lb);

	}

	public void update(){
		_com.search(_projectID, "", lb.getItemText(lb.getSelectedIndex()), 0,_actualFilter, Cookies.getCookie("Fears"), getFeaturesCB);
		_com.getProjectName(_projectID, getProjectName);
	}

	protected void updateProjectName(String name){
		Fears.getPath().setProject(name, _projectID);
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
