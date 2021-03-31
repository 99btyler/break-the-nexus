package breakthenexus.game.details.commands;

import breakthenexus.BreakTheNexus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKit implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (args.length == 0) {
            commandSender.sendMessage("/kit [switch|view]");
        } else {

            switch (args[0].toLowerCase()) {

                case "switch":
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
