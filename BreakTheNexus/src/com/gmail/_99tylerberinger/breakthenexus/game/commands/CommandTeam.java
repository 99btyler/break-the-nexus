package com.gmail._99tylerberinger.breakthenexus.game.commands;

import com.gmail._99tylerberinger.breakthenexus.BreakTheNexus;
import com.gmail._99tylerberinger.breakthenexus.game.parts.team.Team;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTeam implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (args.length == 0) {
            commandSender.sendMessage("/team [join|view]");
            return false;
        }

        switch (args[0].toLowerCase()) {

            case "join":

                if (args.length != 2) {
                    commandSender.sendMessage("/team join [teamName]");
                    return false;
                }

                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage("That command is only for players");
                    return false;
                }

                final Player player = (Player)commandSender;

                if (player.getWorld() == BreakTheNexus.getInstance().getGamemapManager().getGameWorld()) {
                    player.sendMessage("You're already in the game");
                    return false;
                }

                if (BreakTheNexus.getInstance().getTeamManager().getTeamByPlayer(player.getName()) != null) {
                    player.sendMessage("You're already on a team");
                    return false;
                }

                for (Team team : BreakTheNexus.getInstance().getTeamManager().getTeams()) {
                    if (team.getName().equalsIgnoreCase(args[1])) {

                        team.addPlayer(player.getName());

                    }
                }

                break;

            case "view":

                commandSender.sendMessage("");
                for (Team team : BreakTheNexus.getInstance().getTeamManager().getTeams()) {
                    commandSender.sendMessage(team.getInfo());
                }
                commandSender.sendMessage("");

                break;

        }

        return true;

    }

}
