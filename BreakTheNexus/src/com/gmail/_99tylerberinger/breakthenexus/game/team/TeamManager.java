package com.gmail._99tylerberinger.breakthenexus.game.team;

import org.bukkit.Color;

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

    public final Color getTeamColorByPlayer(String playerName) {

        final Team team = getTeamByPlayer(playerName);

        switch (team.getName()) {
            case "Red":
                return Color.RED;
            case "Blue":
                return Color.BLUE;
        }

        return null;

    }

}
