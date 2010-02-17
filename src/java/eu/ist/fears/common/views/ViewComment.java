package eu.ist.fears.common.views;

import java.io.Serializable;

import eu.ist.fears.common.State;

public class ViewComment implements Serializable {

    private static final long serialVersionUID = 8719491807239228411L;

    protected String comment;
    protected String authorNick;
    protected String authorOID;
    protected State oldState;
    protected State newState;
    protected String date;

    public ViewComment() {

    }

    public ViewComment(String c, String aNick, String authorOID, State newState, State oldstate, String date) {
	comment = c;
	authorNick = aNick;
	this.newState = newState;
	oldState = oldstate;
	this.date = date;
	this.authorOID = authorOID;
    }

    public String getComment() {
	return comment;
    }

    public State getNewState() {
	return newState;
    }

    public State getOldState() {
	return oldState;
    }

    public String getCreatedDate() {
	return date;
    }

    public String getAuthorNick() {
	return authorNick;
    }

    public String getAuthorOID() {
	return authorOID;
    }

}
