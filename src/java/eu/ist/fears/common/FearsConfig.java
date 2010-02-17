package eu.ist.fears.common;

public class FearsConfig {
    private static String casUrl;
    private static String fearsUrl;
    public static boolean PRODUCTION;
    static {
	PRODUCTION=false;
	if (PRODUCTION) {  /* Production: */
	    casUrl = "https://id.ist.utl.pt/cas/";
	    fearsUrl = "https://fears.ist.utl.pt/";

	} else {
	    casUrl = "https://localhost:8443/cas/";
	    fearsUrl = "http://localhost:8080/webapp/";
	}
    }

    public static String getCasUrl() {
	return casUrl;
    }

    public static String getFearsUrl() {
	return fearsUrl;

    }
}
