package com.gmail._99tylerberinger.breakthenexus.game.commands;

import com.gmail._99tylerberinger.breakthenexus.BreakTheNexus;
import com.gmail._99tylerberinger.breakthenexus.game.parts.kit.Kit;
import com.gmail._99tylerberinger.breakthenexus.game.parts.team.Team;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (args.length == 0) {
            commandSender.sendMessage("/kit [list|switch|view]");
            return false;
        }

        switch (args[0].toLowerCase()) {

            case "list":

                String kits = "";
                for (Kit kit : BreakTheNexus.getInstance().getKitManager().getKits()) {
                    kits += kit.getName() + ", ";
                }
                commandSender.sendMessage(kits);

                break;

            case "switch":

                if (args.length != 2) {
                    commandSender.sendMessage("/kit switch [kitName]");
                    return false;
                }

                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage("That command is only for players");
                    return false;
                }

                final Player player = (Player)commandSender;

                if (player.getWorld() == BreakTheNexus.getInstance().getGamemapManager().getLobbyWorld()) {
                    commandSender.sendMessage("You must be in game");
                    return false;
                }

                final Team team = BreakTheNexus.getInstance().getTeamManager().getTeamByPlayer(player.getName());

                if (team == null) {
                    commandSender.sendMessage("You must be on a team");
                    return false;
                }

                if (!team.protectedAreaContains(player.getLocation())) {
                    commandSender.sendMessage("You must be in your team's protected area");
                    return false;
                }

                final boolean switched = BreakTheNexus.getInstance().getKitManager().updateKitUsers(player.getName(), args[1]);

                if (switched) {

                    player.teleport(team.getRandomSpawnpoint());
                    BreakTheNexus.getInstance().getKitManager().takeKitItemsFrom(player.getName());
                    BreakTheNexus.getInstance().getKitManager().giveKitItemsTo(player.getName());

                }

                break;

            case "view":

                if (!(commandSender instanceof Player)) {
                    commandSender.sendMessage("That command is only for players");
                    return false;
                }

                final String kitName = BreakTheNexus.getInstance().getKitManager().getKitNameUsedBy(commandSender.getName());
                commandSender.sendMessage("You are using kit " + kitName);

                break;

        }

        return true;

    }

}
