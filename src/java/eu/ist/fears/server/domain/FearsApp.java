package eu.ist.fears.server.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.joda.time.format.DateTimeFormat;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.pstm.Transaction;
import eu.ist.fears.client.common.DateFormat;
import eu.ist.fears.client.common.exceptions.FearsException;
import eu.ist.fears.client.common.exceptions.NoUserException;
import eu.ist.fears.client.common.views.ViewAdmins;
import eu.ist.fears.client.common.views.ViewFeatureResume;
import eu.ist.fears.client.common.views.ViewProject;
import eu.ist.fears.server.FearsServiceImpl;

public class FearsApp extends FearsApp_Base {


    public static FearsApp getFears(){
        return FenixFramework.getRoot();
    }


    public Project getProject(String projectID){
        int projID;
        try{
            projID= new Integer(projectID).intValue();
        }catch(Throwable t){
            System.out.println("ID:" + projectID);
            throw new RuntimeException("Nao existe esse projecto: " + projectID);
        }

        for(Project p :  getProjectSet()){
            if(p.getIdInternal()==(projID))
                return p;
        }

        return null;
    }


    public void addProject(Project p, User u){
        addProject(p);
    }

    public void deleteProject(String projectID){
        int projID;
        try{
            projID= new Integer(projectID).intValue();
        }catch(Throwable t){
            System.out.println("ID:" + projectID);
            throw new RuntimeException("Nao existe esse projecto: " + projectID);
        }
        for(Project p : getProjectSet()){
            if(p.getIdInternal().equals(projID))
                removeProject(p);			
        }				
    }		


    public User getUser(String name) throws FearsException{
        for(User u : getUserSet()){
            if(u.getName().equals(name))
                return u;					
        }

        throw new NoUserException();
    }
    
    public User getUserFromID(String id) throws FearsException{
        User u = FearsApp.fromOID(new Long(id));
      
        if(u==null)
        throw new NoUserException();
        
        return u;
    }

    public User createUser(String userName){
        if(userName!=null){
            User temp = new User(userName);
            addUser(temp);
            return temp;
        } else return null;

    }

    public Collection<User> getAdmins(){
        return super.getAdminSet();
    }

    public void addAdmin(User u){
        super.addAdmin(u);
    }

    public void removeAdmin(User u){
        super.removeAdmin(u);
    } 

    public boolean isAdmin(User u){
        return getAdmins().contains(u);
    }

    public List<ViewProject> getProjectsViews() {
        List<ViewProject> res = new ArrayList<ViewProject>();

        for(Project p : getProjectSet()){
            res.add(p.getView());
        }

        Collections.sort(res, new ProjectViewOrderComparator());

        return res;
    }

    public static List<ViewFeatureResume> getViewFeaturesResumes(Collection<FeatureRequest> features, Voter user){
        if( features.size()==0)
            return null;

        ArrayList<ViewFeatureResume> res = new ArrayList<ViewFeatureResume>();

        boolean userHasvoted;

        for(FeatureRequest f : features ){
            userHasvoted=false;
            for(Voter v : f.getVoterSet()){
                if(v.equals(user))
                    userHasvoted=true;
            }
            res.add(new ViewFeatureResume(f.getProject().getName(), f.getProject().getIdInternal(), f.getName(), f.getWebID() , f.getState(), userHasvoted , f.getDescription(),
                    f.getVotes(), f.getNComments(), FearsServiceImpl.getNickName(f.getAuthor().getUser().getName()), new Long(f.getAuthor().getUser().getOid()).toString() ,f.getCreatedTime().toString(DateTimeFormat.forPattern(DateFormat.DEFAULT_FORMAT)) ));
        }

        return res;
    }

    public ViewAdmins getViewAdmins(){
        List<String >admins = new ArrayList<String>();
        List<String >adminsNicks = new ArrayList<String>();

        for(User u : getFears().getAdmins()){
            admins.add(u.getUsername());
            adminsNicks.add(FearsServiceImpl.getNickName(u.getUsername()));
        }

        return new ViewAdmins(admins,adminsNicks);	
    }

    public void projectDown(String projectId) {
        Project actual=null;
        int projID;
        int nextPosition;
        boolean isNext=false;
        List<Project> projects = new ArrayList<Project>();
        projects.addAll(getProject());
        Collections.sort(projects, new ProjectOrderComparator());
        try{
            projID= new Integer(projectId).intValue();
        }catch(Throwable t){
            System.out.println("ID:" + projectId);
            throw new RuntimeException("Nao existe esse projecto: " + projectId);
        }

        for(Project p : projects){
            System.out.println(p.getName()+"\n");
            if(isNext){
                nextPosition=p.getListPosition();
                p.setListPosition(actual.getListPosition());
                actual.setListPosition(nextPosition);
                return;
            }

            if(p.getIdInternal()==(projID)){
                isNext=true;
                actual=p;
            }
        }
    }


    public void projectUp(String projectId) {

        Project before=null;
        int projID;
        int beforePosition;
        List<Project> projects = new ArrayList<Project>();
        projects.addAll(getProject());
        Collections.sort(projects, new ProjectOrderComparator());
        try{
            projID= new Integer(projectId).intValue();
        }catch(Throwable t){
            System.out.println("ID:" + projectId);
            throw new RuntimeException("Nao existe esse projecto: " + projectId);
        }
        for(Project p : projects){
            if(p.getIdInternal()==(projID)){
                if(before==null)//Already first
                    return;
                beforePosition=before.getListPosition();
                before.setListPosition(p.getListPosition());
                p.setListPosition(beforePosition);
                return;
            }
            before=p;
        }
    }

    public List<Project> getProjectsSorted(){
        List<Project> projects = new ArrayList<Project>();
        projects.addAll(getProject());
        Collections.sort(projects, new FearsApp.ProjectOrderComparator());
        return projects;
    }


    protected class ProjectViewOrderComparator implements Comparator<ViewProject>{

        public int compare(ViewProject p1, ViewProject p2) {
            return p1.getListOrder()-p2.getListOrder();
        }
    }

    protected class ProjectOrderComparator implements Comparator<Project>{

        public int compare(Project p1, Project p2) {
            return p1.getListPosition()-p2.getListPosition();
        }
    }

}
