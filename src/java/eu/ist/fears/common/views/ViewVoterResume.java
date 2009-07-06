package eu.ist.fears.common.views;

import java.io.Serializable;



public class ViewVoterResume implements Serializable {



    /**
     * 
     */
    private static final long serialVersionUID = -5527088349945022648L;

    protected String _user;
    protected String _userNick;
    protected String _oid;
    protected int _votesLeft;
    protected boolean _isAdmin;

    public ViewVoterResume(){}

    public ViewVoterResume(String name, String nick, String oid, boolean isAdmin){
        _user=name;
        _userNick=nick;
        _isAdmin=isAdmin;
        _oid=oid;

    }

    public ViewVoterResume(String name, String nick, String oid, int votesLeft, boolean isAdmin){
        _user=name;
        _userNick=nick;
        _votesLeft=votesLeft;
        _isAdmin= isAdmin;
        _oid=oid;
    }


    public String getName(){
        return _user;
    }

    public int getVotesLeft(){
        return _votesLeft;
    }

    public boolean isAdmin(){
        return _isAdmin;
    }

    public void setName(String user){
        _user=user;
    }

    public String getNick(){
        return _userNick;
    }

    public String getOID(){
        return _oid;
    }

}
