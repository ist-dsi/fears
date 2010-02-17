package eu.ist.fears.common.views;

import java.io.Serializable;

public class ViewVoterResume implements Serializable {

    private static final long serialVersionUID = -5527088349945022648L;

    protected String user;
    protected String userNick;
    protected String oid;
    protected int votesLeft;
    protected boolean isAdmin;

    public ViewVoterResume() {
    }

    public ViewVoterResume(String name, String nick, String oid, boolean isAdmin) {
	user = name;
	userNick = nick;
	this.isAdmin = isAdmin;
	this.oid = oid;

    }

    public ViewVoterResume(String name, String nick, String oid, int votesLeft, boolean isAdmin) {
	user = name;
	userNick = nick;
	this.votesLeft = votesLeft;
	this.isAdmin = isAdmin;
	this.oid = oid;
    }

    public String getName() {
	return user;
    }

    public int getVotesLeft() {
	return votesLeft;
    }

    public boolean isAdmin() {
	return isAdmin;
    }

    public void setName(String user) {
	this.user = user;
    }

    public String getNick() {
	return userNick;
    }

    public String getOID() {
	return oid;
    }

}
