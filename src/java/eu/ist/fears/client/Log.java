package eu.ist.fears.client;

public class Log {
    public static native void log(Object s)/*-{
    	if($wnd.console){
    	    $wnd.console.log(s.toString());
    	}
    }-*/;
    
    public static native void warn(Object s)/*-{
	if($wnd.console){
    	    $wnd.console.warn(s.toString());
    	}
    }-*/;
    public static native void error(Object s)/*-{
	if($wnd.console){
    	    $wnd.console.error(s.toString());
    	}
    }-*/;
}
