package eu.ist.fears.client.interfaceweb;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ist.fears.client.Fears;
import eu.ist.fears.client.views.ViewComment;
import eu.ist.fears.client.views.ViewFeatureDetailed;
import eu.ist.fears.client.views.ViewFeatureResume;

public class FeatureDetailedWidget extends FeatureResumeWidget {

	protected VerticalPanel _comments;
	protected TextArea _commentTextArea;
	protected PushButton _commentButton;
	protected Label _voters;
	protected VerticalPanel _newCommentBox;
	
	
	public FeatureDetailedWidget(ViewFeatureDetailed f, AsyncCallback cb) {
		super(f, cb);
		
		_voters= new Label("");
		_comments = new VerticalPanel();
		_newCommentBox = new VerticalPanel();
		_commentTextArea= new TextArea();
		_commentTextArea.setStyleName("commentTextArea");
		_commentButton = new PushButton(new Image("button03.gif",0,0,105,32), new Image("button03.gif",-2,-2,105,32));
		_commentButton.setStyleName("commentButton");
		_comments.setStyleName("width100");
		_feature.add(_comments);
		
		_newCommentBox.setStyleName("newComment");
		
		if(Fears.isLogedIn()){
			_newCommentBox.add(_commentTextArea);
			_newCommentBox.add(_commentButton);
		}
		
		_commentButton.addClickListener(new CommentButton());
		_commentTextArea.setVisibleLines(5);
		_commentTextArea.setCharacterWidth(50);
		
		updateComments(f);
	}
	
	public void update(ViewFeatureDetailed f){
		super.update(f);
		updateComments(f);
	}
	
	public void updateComments(ViewFeatureDetailed f){
		Label comment;
		Label header;
		
		_comments.clear();
		
		if(f.getComments().size()>0)
			for(int i=0; i<f.getComments().size();i++){
				header = new Label("Por: " + ((ViewComment)f.getComments().get(i)).getAuthor());
				header.setStyleName("meta");
				comment = header;
				Label commentText = new Label(((ViewComment)f.getComments().get(i)).getComment());
				commentText.setStyleName("commentText");
				comment = new HTML(comment.getElement().toString() + commentText );
				comment.setStyleName("comment");
				_comments.add(comment);
			} 
		_comments.add(_newCommentBox);
	}
	
	private class CommentButton implements ClickListener{

		public void onClick(Widget sender) {
			//_alert.setText("O teu comentario foi inserido.");
			_com.addComment(_projectName, _name.getText(),
			_commentTextArea.getText(), Cookies.getCookie("fears"), _cb);
			_commentTextArea.setText("");
		}

	} 

}
