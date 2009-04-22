package eu.ist.fears.client.common;

public class FearsConfig {
	
	/* Tests: 
	protected static String _casUrl = "https://localhost:8443/cas/";
	protected static String _fearsUrl= "http://localhost:8080/webapp/";  */
	
	/* Production: */
	protected static String _casUrl = "https://id.ist.utl.pt/cas/";
	protected static String _fearsUrl= "https://fears.ist.utl.pt/";  

	public static String getCasUrl(){
		return _casUrl;
	}
	
	public static String getFearsUrl(){
		return _fearsUrl;
		
	}
}
