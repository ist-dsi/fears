package eu.ist.fears.server.domain;


public class User extends User_Base {
    
    public  User(String name) {
        super();
        setUsername(name);
    }
    
    public String getName(){
    	return getUsername();
    }
    
    public Voter getVoter(Project p){
    	for(Voter v: getVoterSet()){
    		if(v.getProject()==p)
    			return v;
    	}
    	
    	//User has no voter in this project, create:
    	Voter ret = new Voter(this);
    	p.addVoter(ret);
    	return ret;    	
    }
    
    
    
}
