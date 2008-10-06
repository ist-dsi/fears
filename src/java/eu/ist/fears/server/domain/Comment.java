package eu.ist.fears.server.domain;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import eu.ist.fears.client.common.DateFormat;
import eu.ist.fears.client.common.State;
import eu.ist.fears.client.common.views.ViewComment;

public class Comment extends Comment_Base {
    
    public  Comment(String c, Voter v, State newState) {
        super();
        setComment(c);
        setAuthor(v);
        setNewState(newState);
        setCreatedTime(new DateTime());
    }

    public ViewComment getView(){
        return new ViewComment(getComment(), getAuthor().getUser().getName(), getNewState(), getCreatedTime().toString(DateTimeFormat.forPattern(DateFormat.DEFAULT_FORMAT)));
    }
}
