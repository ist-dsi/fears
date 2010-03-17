package eu.ist.fears.server;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;

import eu.ist.fears.server.domain.FearsApp;

public class Init {

    public static void init() {
	Config config = new Config() {
	    {
		domainModelPath = "/fears.dml";
		dbAlias = "//localhost:3306/fears";
		dbUsername = "root";
		dbPassword = "";
		rootClass = FearsApp.class;
		updateRepositoryStructureIfNeeded = true;
	    }
	};
	FenixFramework.initialize(config);
    }
}
