package eu.ist.fears.server.domain;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import eu.ist.fears.client.common.DateFormat;
import eu.ist.fears.client.common.State;
import eu.ist.fears.client.common.views.ViewComment;
import eu.ist.fears.server.FearsServiceImpl;

public class Comment extends Comment_Base {
    
    public  Comment(String c, Voter v, State newState, State oldState) {
        super();
    	//Remove all \r inserted by IE browser.
        c=c.replaceAll("\r","");
        
        int count=0;
		for(int i=0;i<c.length();i++,count++){
			if(c.charAt(i)=='\n'){
				count=0;
				continue;
			}else if(count>=100){
				c=insertNewLine(i,c);
				count=0;
			}
		}
		setComment(c);
        setAuthor(v);
        setNewState(newState);
        setOldState(oldState);
        setCreatedTime(new DateTime());
    }

  //Inserts a new line before the ith position, in the position of a white space
	//(for not cutting words)
    private String insertNewLine(int i,String c){
		for(int j=i;j>0;j--){
			if(c.charAt(j)==' '){
				return c.substring(0,j) + "\n" + c.substring(j, c.length());
			}
		}
		return c;
	}
    
    public ViewComment getView(){
        return new ViewComment(getComment(), getAuthor().getUser().getName(),FearsServiceImpl.getNickName(getAuthor().getUser().getName()) , getNewState(), getOldState() , getCreatedTime().toString(DateTimeFormat.forPattern(DateFormat.DEFAULT_FORMAT)));
    }
}
