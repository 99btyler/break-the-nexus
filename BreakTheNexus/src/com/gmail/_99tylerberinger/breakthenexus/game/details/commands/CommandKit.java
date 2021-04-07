package com.gmail._99tylerberinger.breakthenexus.game.details.commands;

import com.gmail._99tylerberinger.breakthenexus.BreakTheNexus;
import com.gmail._99tylerberinger.breakthenexus.game.kit.Kit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (args.length == 0) {

            commandSender.sendMessage("/kit [list|switch|view]");

        } else {

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

                    } else {

                        if (!(commandSender instanceof Player)) {
                            commandSender.sendMessage("That command is only for players");
                            break;
                        }

                        final Player player = (Player)commandSender;

                        if (player.getWorld() == BreakTheNexus.getInstance().getGamemapManager().getLobbyWorld()) {
                            commandSender.sendMessage("You must be in game");
                            break;
                        }

                        final boolean switched = BreakTheNexus.getInstance().getKitManager().updateKitUsers(player.getName(), args[1]);

                        if (switched) {
                            player.sendMessage("Success!");
                            player.setHealth(0);
                        }

                    }

                    break;

                case "view":

                    if (!(commandSender instanceof Player)) {
                        commandSender.sendMessage("That command is only for players");
                        break;
                    }

                    final Player player = (Player)commandSender;
                    final String kitName = BreakTheNexus.getInstance().getKitManager().getKitNameUsedBy(player.getName());

                    player.sendMessage("You are using kit " + kitName);

                    break;

            }

        }

        return true;

    }

}
