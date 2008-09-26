package eu.ist.fears.client.interfaceweb;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import eu.ist.fears.client.Fears;
import eu.ist.fears.client.views.ViewComment;
import eu.ist.fears.client.views.ViewFeatureDetailed;

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
		super.update(f,true);
		updateComments(f);
	}

	public void updateComments(ViewFeatureDetailed f){
		VerticalPanel comment;
		HorizontalPanel header;

		_comments.clear();
		Label commentTitle = new HTML("Coment&aacute;rios:");
		commentTitle.setStylePrimaryName("commentTitle");
		_comments.add(commentTitle);

		if(f.getComments().size()>0){
			for(int i=0; i<f.getComments().size();i++){
				header= new HorizontalPanel();
				HorizontalPanel text = new HorizontalPanel();
				header.add(text);
				header.setStyleName("commentItem");
				text.add(new HTML("Por:&nbsp;"));
				text.add(new Hyperlink(((ViewComment)f.getComments().get(i)).getAuthor(),"Project"+_projectID+"?"+"viewUser"+((ViewComment)f.getComments().get(i)).getAuthor()));
				text.setStyleName("meta");
				Label commentText = new Label(((ViewComment)f.getComments().get(i)).getComment());
				commentText.setStyleName("commentItem");
				comment = new VerticalPanel();
				comment.setWidth("100%");
				comment.add(header);
				comment.add(commentText);
				comment.setStyleName("comment");
				_comments.add(comment);
			} 
			
		}else{
			HTML info = new HTML("Nao existem coment&aacute;rios.");
			info.setStyleName("commentTextArea");
			_comments.add(info);
		}
		
		_comments.add(_newCommentBox);
		}

	private class CommentButton implements ClickListener{

		public void onClick(Widget sender) {
			//_alert.setText("O teu comentario foi inserido.");
			_com.addComment(_projectID, _featureID,
					_commentTextArea.getText(), Cookies.getCookie("fears"), _cb);
			_commentTextArea.setText("");
		}

	} 

}
