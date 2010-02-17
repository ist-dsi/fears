package eu.ist.fears.server.domain;

import java.util.List;

import eu.ist.fears.common.views.ViewFeatureResume;
import eu.ist.fears.common.views.ViewVoterDetailed;
import eu.ist.fears.common.views.ViewVoterResume;
import eu.ist.fears.server.FearsServiceImpl;

public class Voter extends Voter_Base {

    public Voter(User user) {
	setUser(user);
    }

    public List<ViewFeatureResume> getViewFeaturesCreated() {
	return FearsApp.getViewFeaturesResumes(getFeaturesCreatedSet(), this);
    }

    public List<ViewFeatureResume> getViewFeaturesVoted() {
	return FearsApp.getViewFeaturesResumes(getFeaturesVotedSet(), this);
    }

    public User getUser() {
	return super.getUser();
    }

    public boolean hasRole(Role role) {
	System.out.println("Role foi chamado...");
	if (role.getRole().equals("admin")) {
	    return FearsApp.getFears().isAdmin(this.getUser());
	} else
	    return false;

    }

    public ViewVoterDetailed getView() {
	return new ViewVoterDetailed(getUser().getName(), FearsServiceImpl.getNickName(getUser().getName()), new Long(getUser()
		.getOid()).toString(), getViewFeaturesCreated(), getViewFeaturesVoted(), getProject().getIdInternal().toString(),
		getProject().getName());

    }

    public ViewVoterResume getCurrentVoterView(String sessionID) {
	int votesLeft;
	if (getProject().getInitialVotes() - getVotesUsed() >= 0)
	    votesLeft = getProject().getInitialVotes() - getVotesUsed();
	else
	    votesLeft = 0;

	return new ViewVoterResume(getUser().getUsername(), FearsServiceImpl.getNickName(getUser().getUsername()), new Long(
		getUser().getOid()).toString(), votesLeft, FearsApp.getFears().isAdmin(getUser()));

    }

}
