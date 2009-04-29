package eu.ist.fears.server;


import java.io.IOException;

import javax.servlet.ServletException;

import com.google.gwt.user.client.rpc.SerializationException;

import eu.ist.fears.server.domain.FearsApp;

import pt.ist.fenixframework.Config;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;

public class AddAdmin {

	public static void init() throws ServletException {
		Config config = new Config() {{ 
			domainModelPath = "/fears.dml";
			dbAlias = "//localhost:3306/fears"; 
			dbUsername="root";
			dbPassword = "fears";
		}};
		FenixFramework.initialize(config);
	}

	/**
	 * @param args - 1: Username of admin to add.
	 * @throws ServletException 
	 * @throws SerializationException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ServletException, SerializationException, IOException {
		if(args.length!=1){
			System.out.println("Este programa recebe 1 argumento: o username do Administrador.");
			return;
		}
		if(args[0].equals("ist1XXXXX")){
			System.out.println("Tem que mudar o nome do admin no ficheiro build. (property=adminUser)");
			return;
		}
		init();
		transaction(args[0]);
		System.out.println("Administrador Adicionado: " + args[0]);		
	}

	public static void addAdmin(String adminName){
		FearsApp.getFears().addAdmin(FearsApp.getFears().getUser(adminName));	
	}

	public static void transaction(String adminName) throws SerializationException {
		// process the RPC call within a transaction
		while (true) {
			Transaction.begin();
			//AccessControlSession.beginAccessControl(getUserFromSession("..."));
			boolean txFinished = false;
			try {
				addAdmin(adminName);
				Transaction.commit();
				txFinished = true;
				return;
			} catch (jvstm.CommitException ce) {
				Transaction.abort();
				txFinished = true;
			} finally {
				if (! txFinished) {
					Transaction.abort();
				}
			}
		}
	}

}
