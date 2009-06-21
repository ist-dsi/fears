package eu.ist.fears.client.common.views;

import java.io.Serializable;

import eu.ist.fears.client.common.State;

public class ViewComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8719491807239228411L;

	protected String _comment;
	protected String _authorNick;
	protected String _authorOID;
	protected State _oldState;
	protected State _newState;
	protected String _date;
	
	public ViewComment(){
		
	}
	
	public ViewComment(String c, String aNick, String authorOID, State newState, State oldstate, String date ){
		_comment=c;
		_authorNick=aNick;
		_newState=newState;
		_oldState=oldstate;
		_date=date;
		_authorOID=authorOID;
	}
	
	public String getComment(){
		return _comment;
	}
	
	public State getNewState(){
		return _newState;
	}
	
	public State getOldState(){
		return _oldState;
	}
	
	public String getCreatedDate(){
		return _date;
	}
	
	public String getAuthorNick(){
		return _authorNick;
	}
	
	public String getAuthorOID(){
        return _authorOID;
    }
	
	

}
