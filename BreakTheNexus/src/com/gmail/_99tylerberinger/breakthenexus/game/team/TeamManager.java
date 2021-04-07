package com.gmail._99tylerberinger.breakthenexus.game.team;

public class TeamManager {

    private final Team[] teams;

    public TeamManager(Team[] teams) {
        this.teams = teams;
    }

    public final Team[] getTeams() {
        return teams;
    }

    public final Team getTeamByPlayer(String playerName) {
        for (Team team : teams) {
            if (team.hasPlayer(playerName)) {
                return team;
            }
        }
        return null;
    }

}
