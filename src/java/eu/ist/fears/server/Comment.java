package eu.ist.fears.server;

import eu.ist.fears.client.views.ViewComment;

public class Comment {
	
	private String _comment;
	
	public Comment(String c ){
		_comment =c;
	}
	
	public String getComment(){
		return _comment;
	}

	public ViewComment getView(){
		return new ViewComment(_comment);
	}
	
}
