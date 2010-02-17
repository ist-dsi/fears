package eu.ist.fears.common.views;

import java.io.Serializable;

public class ViewProject implements Serializable {

    private static final long serialVersionUID = 727148316108470024L;
    protected String name;
    protected int webID;
    protected String description;
    protected int nFeatures;
    protected String author;
    protected String authorNick;
    protected int initialVotes;
    protected int listOrder;

    public ViewProject() {

    }

    public ViewProject(String name, int webID, String description, int nFeatures, String author, String authorNick,
	    int initialVotes, int listOrder) {
	this.name = name;
	this.webID = webID;
	this.description = description;
	this.nFeatures = nFeatures;
	this.author = author;
	this.authorNick = authorNick;
	this.initialVotes = initialVotes;
	this.listOrder = listOrder;
    }

    public String getName() {
	return name;
    }

    public int getwebID() {
	return webID;
    }

    public String getDescription() {
	return description;
    }

    public int getNFeatures() {
	return nFeatures;
    }

    public String getAuthor() {
	return author;
    }

    public int getInitialVotes() {
	return initialVotes;
    }

    public String getAuthorNick() {
	return authorNick;
    }

    public int getListOrder() {
	return listOrder;
    }

}
