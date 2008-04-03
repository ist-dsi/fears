package eu.ist.fears.client;


import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Fears implements EntryPoint, HistoryListener  {



	HorizontalPanel content;
	static VerticalPanel featuresBox; 
	VerticalPanel menuBox;
	HorizontalPanel topBox; 
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
		topBox.setStyleName("topBG");
		featuresBox.setStyleName("featuresBG");
		menuBox.setStyleName("menuBG");

		topBox.add(new Label("Username: ..."));
		topBox.add(new Label("Nº de Votos Restante: ..."));
		
		featPanel= new ListFeaturesWidget();
		featuresBox.add(featPanel);

		VerticalPanel menu = new VerticalPanel();
		menuBox.add(menu);
		menu.setStyleName("menu");

		menu.add(new Hyperlink("Ver Sugestoes","listFeatures"));
		menu.add(new Hyperlink("Adicionar Sugestao","addFeature"));
		menu.add(new Hyperlink("Sugestoes Default","defaultFeatures"));
		
	
		//Ir buscar as sugestões que tao no server
		featPanel.init();
		
		History.addHistoryListener(this);
		
		
		/*Mostrar o #  actual */
		String initToken = History.getToken();
		if (initToken.length() > 0) {
			onHistoryChanged(initToken);			
		}
		

	
	}	

	public static void listFeatures(){
		featuresBox.clear();
		featuresBox.add(featPanel);
		featPanel.init();		


	}

	public static void addFeature(){
		featuresBox.clear();
		featuresBox.add(new CreateFeatureWidget());

	}
	
	public static void viewFeature(String feature){
		featuresBox.clear();
		featuresBox.add(new FeatureDisplayWidget(feature));
	}
	
	

	public void onHistoryChanged(String historyToken) {
	    // This method is called whenever the application's history changes. Set
	    // the label to reflect the current history token.
		if("listFeatures".equals(historyToken)){
			listFeatures();			
		}else if("addFeature".equals(historyToken)){
			addFeature();
		}else if("defaultFeatures".equals(historyToken)){
			featPanel.test();
			listFeatures();
		}else if(historyToken.startsWith("viewFeature")){
			viewFeature(historyToken.substring("viewFeature".length()));
		}
				
	  }
	
	
}
