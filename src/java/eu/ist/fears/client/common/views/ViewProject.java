package eu.ist.fears.client.common.views;

import java.io.Serializable;

public class ViewProject implements Serializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 727148316108470024L;
	protected String _name;
	protected int _webID;
	protected String _description;
	protected int _nFeatures;
	protected String _author;
	protected int _votesLeft;

	public ViewProject(){

	}

	public ViewProject(String name, int webID, String description, int nFeatures, String author, int votesLeft){
		_name=name;
		_webID=webID;
		_description=description;
		_nFeatures = nFeatures;
		_author=author;
		_votesLeft = votesLeft;
	}

	public String getName() {
		return _name;
	}

	public int getwebID() {
		return _webID;
	}
	
	public String getDescription() {
		return _description;
	}

	public int getNFeatures() {
		return _nFeatures;
	}
	
	public String getAuthor() {
		return _author;
	}
	
	

}
