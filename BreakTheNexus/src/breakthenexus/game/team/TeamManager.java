package breakthenexus.game.team;

public class TeamManager {

    private final Team[] teams;

    public TeamManager(Team[] teams) {
        this.teams = teams;
    }

    public final Team[] getTeams() {
        return teams;
    }

    public final String getTeamNameByPlayer(String playerName) {
        for (Team team : teams) {
            if (team.hasPlayer(playerName)) {
                return team.getTeamName();
            }
        }
        return null;
    }

}
