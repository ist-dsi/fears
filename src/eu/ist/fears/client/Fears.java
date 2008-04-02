package eu.ist.fears.client;


import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Fears implements EntryPoint {



	HorizontalPanel content;
	VerticalPanel featuresBox; 
	VerticalPanel menuBox;
	HorizontalPanel topBox; 
	static VerticalPanel features;
	static ListFeaturesWidget featPanel;



	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {


		RootPanel.get().setStyleName("body");

		content= new HorizontalPanel();
		featuresBox = new VerticalPanel();
		menuBox = new VerticalPanel();
		topBox = new HorizontalPanel();

		RootPanel.get().add(topBox);
		RootPanel.get().add(content);
		content.add(featuresBox);
		content.add(menuBox);

		content.setStyleName("content");
		topBox.setStyleName("topoBox");
		featuresBox.setStyleName("sugestoesBox");
		menuBox.setStyleName("menuBox");

		topBox.add(new Label("Username: ..."));
		topBox.add(new Label("Nº de Votos Restante: ..."));

		features = new VerticalPanel();

		features.setStyleName("sugestoes");
		featuresBox.add( new HTML("<br><h1>Sugestoes</h1><br>"));

		featuresBox.add(features);

		featPanel= new ListFeaturesWidget();
		features.add(featPanel);

		VerticalPanel menu = new VerticalPanel();
		menuBox.add(menu);
		menu.setStyleName("menu");

		Label menuN = new Label("Menu1");  
		menu.add(menuN);    
		menuN = new Label("Menu2");  
		menu.add(menuN);    
		menuN = new Label("Menu3");   
		menu.add(menuN);

		Button verSugestoes = new Button("Ver Sugestoes");
		menu.add(verSugestoes);

		Button sugestoesDefault = new Button("Sugestoes Default");
		menu.add(sugestoesDefault);

		Button adicionaSugestao = new Button("Adicionar Sugestao");
		menu.add(adicionaSugestao);

		//Ir buscar as sugestões que tao no server
		featPanel.init();

		verSugestoes.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				viewFeatures();
			}
		});

		sugestoesDefault.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				featPanel.test();
			}
		});

		adicionaSugestao.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				addFeature();
			}
		});

	}	

	public static void viewFeatures(){
		features.clear();
		features.add(featPanel);
		featPanel.init();		


	}

	public static void addFeature(){
		features.clear();
		features.add(new CreateFeatureWidget());

	}

}
