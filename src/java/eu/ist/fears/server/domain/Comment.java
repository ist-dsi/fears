package eu.ist.fears.server.domain;

import eu.ist.fears.client.views.ViewComment;

public class Comment extends Comment_Base {
    
    public  Comment(String c, Voter v, String newState) {
        super();
        setComment(c);
        setAuthor(v);
        setNewState(newState);
    }

    public ViewComment getView(){
        return new ViewComment(getComment(), getAuthor().getUser().getName(), getNewState() );
    }
}
