package eu.ist.fears.server;

import java.util.HashMap;

public class SimpleNameGenerator {
    private static HashMap<String, String> firstName = new HashMap<String,String>();
    private static HashMap<String, String> lastName = new HashMap<String,String>();
    static {{
	firstName.put("0", "Gabriel");
	firstName.put("1", "Bernardo");
	firstName.put("2", "Jorge");
	firstName.put("3", "Carlos");
	firstName.put("4", "Daniel");
	firstName.put("5", "Duarte");
	firstName.put("6", "Emidio");
	firstName.put("7", "Artur");
	firstName.put("8", "Luis");
	firstName.put("9", "Otavio");
	
	lastName.put("0", "Abrantes");
	lastName.put("1", "Bicudo");
	lastName.put("2", "Fazenda");
	lastName.put("3", "Caetano");
	lastName.put("4", "Eanes");
	lastName.put("5", "Falcão");
	lastName.put("6", "Gameiro");
	lastName.put("7", "Ventura");
	lastName.put("8", "Lancastre");
	lastName.put("9", "Lima");

    }}
    
    public static String solveName(String userId){
	return firstName.get(userId.substring(userId.length() - 2, userId.length() - 1)) + " " + lastName.get(userId.substring(userId.length() - 1));
    }
}
