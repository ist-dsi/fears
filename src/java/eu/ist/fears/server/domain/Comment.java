package eu.ist.fears.server.domain;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.google.gwt.user.client.ui.HTML;

import eu.ist.fears.common.DateFormat;
import eu.ist.fears.common.State;
import eu.ist.fears.common.views.ViewComment;
import eu.ist.fears.server.FearsServiceImpl;

public class Comment extends Comment_Base {
    
    public  Comment(String c, Voter v, State newState, State oldState) {
        super();
    	//Remove all \r inserted by IE browser.
        c=c.replaceAll("\r","");
        c = c.replaceAll("\\<.*?\\>", "");
		//Put <br> on \n
		int count=0;
		for(int i=0;i<c.length();i++,count++){
			if(c.charAt(i)=='\n')
				c= c.subSequence(0, i) + "<br>" + c.subSequence(i+1, c.length());
		}
		setComment(c);
        setAuthor(v);
        setNewState(newState);
        setOldState(oldState);
        setCreatedTime(new DateTime());
    }

    
    public ViewComment getView(){
        return new ViewComment(getComment(),FearsServiceImpl.getNickName(getAuthor().getUser().getName()), new Long( getAuthor().getUser().getOid()).toString() , getNewState(), getOldState() , getCreatedTime().toString(DateTimeFormat.forPattern(DateFormat.DEFAULT_FORMAT)));
    }
}
