package com.gmail._99tylerberinger.breakthenexus.game.team;

import com.gmail._99tylerberinger.breakthenexus.BreakTheNexus;
import org.bukkit.Color;

import java.util.ArrayList;
import java.util.List;

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

    public final void announceRemainingTeams() {

        final List<Team> remainingTeams = new ArrayList<>();

        for (Team team : teams) {
            if (team.isAlive()) {

                remainingTeams.add(team);

            }
        }

        if (remainingTeams.size() > 1) {

            BreakTheNexus.getInstance().getServer().broadcastMessage(remainingTeams.size() + " teams remain");

        } else {

            BreakTheNexus.getInstance().getServer().broadcastMessage("The game is over! " + remainingTeams.get(0).getName() + " wins!");
            BreakTheNexus.getInstance().getServer().broadcastMessage("Server stopping in 30 seconds...");
            BreakTheNexus.getInstance().getServer().getScheduler().runTaskLater(BreakTheNexus.getInstance(), () -> BreakTheNexus.getInstance().getServer().shutdown(), 20 * 30); // 20 ticks = 1 second

        }

    }

}
