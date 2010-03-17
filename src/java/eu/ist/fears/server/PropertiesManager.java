package eu.ist.fears.server;

import java.io.IOException;
import java.util.Properties;

public class PropertiesManager extends pt.utl.ist.fenix.tools.util.PropertiesManager {
    
    private static final Properties properties = new Properties();
    
    static {
	try{
	    loadProperties(properties,"/build.properties");
	}catch (IOException e) {
	    throw new RuntimeException("Unable to load properties files.",e);
	}
    }
    
    public static String getProperty(final String key) {
        return properties.getProperty(key);
    }

    public static boolean getBooleanProperty(final String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public static Integer getIntegerProperty(final String key) {
        return Integer.valueOf(properties.getProperty(key));
    }                                                                                     

    public static void setProperty(final String key, final String value) {
        properties.setProperty(key, value);
    }

}
