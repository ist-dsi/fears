package eu.ist.fears.server.domain;

import pt.ist.fenixframework.pstm.Transaction;

public class FearsApp extends FearsApp_Base {
    
    public FearsApp() {
        super();
    }

    public static FearsApp getFears(){
        return (FearsApp)Transaction.getDomainObject(FearsApp.class.getName(), 1);
    }
}
