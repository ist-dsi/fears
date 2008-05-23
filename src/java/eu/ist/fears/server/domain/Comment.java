package eu.ist.fears.server.domain;

import eu.ist.fears.client.views.ViewComment;

public class Comment extends Comment_Base {
    
    public  Comment(String c) {
        super();
        setComment(c);
    }

    public ViewComment getView(){
        return new ViewComment(getComment());
    }
}
