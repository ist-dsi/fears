package eu.ist.fears.server;

import com.google.gwt.user.client.rpc.SerializationException;

import eu.ist.fears.client.common.exceptions.FearsException;
import eu.ist.fears.client.common.exceptions.NoUserException;
import eu.ist.fears.server.domain.FearsApp;
import eu.ist.fears.server.domain.User;

import java.io.IOException;
import javax.servlet.ServletException;
import jvstm.Atomic;


public class AddAdmin {

    public static void init() throws ServletException {
        Init.init();
    }

    /**
     * @param args - 1: Username of admin to add.
     * @throws ServletException 
     * @throws SerializationException 
     * @throws IOException 
     * @throws FearsException 
     */
    public static void main(String[] args) throws ServletException, SerializationException, IOException, FearsException {
        if(args.length!=1){
            System.out.println("Este programa recebe 1 argumento: o username do Administrador.");
            return;
        }
        if(args[0].equals("ist1XXXXX")){
            System.out.println("Tem que mudar o nome do admin no ficheiro build. (property=adminUser)");
            return;
        }
        init();
        addAdmin(args[0]);
        System.out.println("Administrador Adicionado: " + args[0]);		
    }

    @Atomic
    public static void addAdmin(String adminName) throws FearsException{
        User u=null;
        try{
            u=FearsApp.getFears().getUser(adminName);
        }catch(NoUserException e){
            u=FearsApp.getFears().createUser(adminName);
        }

        FearsApp.getFears().addAdmin(u);
    }
}
