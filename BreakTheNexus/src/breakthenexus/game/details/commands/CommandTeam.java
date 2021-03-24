package breakthenexus.game.details.commands;

import breakthenexus.BreakTheNexus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTeam implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (args.length == 0) {
            commandSender.sendMessage("/team [join|view]");
        } else {

            switch (args[0].toLowerCase()) {

                case "join":

                    if (args.length != 2) {
                        commandSender.sendMessage("/team join [red|blue]");
                    } else {

                        if (!(commandSender instanceof Player)) {
                            commandSender.sendMessage("That command is only for players");
                            break;
                        }

                        if (((Player)commandSender).getWorld().getName().equals(BreakTheNexus.getInstance().getMapGame().getWorld().getName())) {
                            commandSender.sendMessage("You're already in the game");
                            break;
                        }

                        if (args[1].equalsIgnoreCase("red")) {
                            BreakTheNexus.getInstance().getTeamRed().join(commandSender.getName());
                        } else if (args[1].equalsIgnoreCase("blue")) {
                            BreakTheNexus.getInstance().getTeamBlue().join(commandSender.getName());
                        }

                    }

                    break;

                case "view":

                    commandSender.sendMessage("----");
                    commandSender.sendMessage(BreakTheNexus.getInstance().getTeamRed().getInfo());
                    commandSender.sendMessage(BreakTheNexus.getInstance().getTeamBlue().getInfo());
                    commandSender.sendMessage("----");

                    break;

            }

        }

        return true;

    }

}
