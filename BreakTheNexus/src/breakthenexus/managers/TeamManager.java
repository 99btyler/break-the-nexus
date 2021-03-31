package breakthenexus.managers;

import breakthenexus.game.Team;

public class TeamManager {

    private final Team[] teams;

    public TeamManager(Team[] teams) {
        this.teams = teams;
    }

    public final Team[] getTeams() {
        return teams;
    }

}
