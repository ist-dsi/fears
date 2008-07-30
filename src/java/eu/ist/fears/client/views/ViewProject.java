package eu.ist.fears.client.views;

import java.io.Serializable;

public class ViewProject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8488706481755064304L;

	protected String _name;
	protected int _webID;
	protected String _description;
	protected int _nFeatures;
	protected String _author;

	public ViewProject(){

	}

	public ViewProject(String name, int webID, String description, int nFeatures, String author){
		_name=name;
		_webID=webID;
		_description=description;
		_nFeatures = nFeatures;
		_author=author;
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
