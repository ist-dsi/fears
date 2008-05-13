package eu.ist.fears.client.views;

import java.io.Serializable;

public class ViewProject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8488706481755064304L;

	private String _name;
	private String _description;
	private int _nFeatures;
	private String _author;

	public ViewProject(){

	}

	public ViewProject(String name, String description, int nFeatures, String author){
		_name=name;
		_description=description;
		_nFeatures = nFeatures;
		_author=author;
	}

	public String getName() {
		return _name;
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
