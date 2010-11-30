package eu.ist.fears.server;

public class FearsConfigServer {
    public static boolean isRunningInProduction(){
	return PropertiesManager.getProperty("deployment.type").equals("production");
    }
    
    public static String fenixAPIPassword(){
	return  PropertiesManager.getProperty("fenix.api.password");
    }
}
