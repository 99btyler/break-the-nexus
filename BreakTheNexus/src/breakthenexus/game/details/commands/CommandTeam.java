package breakthenexus.game.details.commands;

import breakthenexus.BreakTheNexus;
import breakthenexus.game.team.Team;
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

                        commandSender.sendMessage("/team join [teamName]");

                    } else {

                        if (!(commandSender instanceof Player)) {
                            commandSender.sendMessage("That command is only for players");
                            break;
                        }

                        final Player player = (Player)commandSender;

                        if (player.getWorld() == BreakTheNexus.getInstance().getMapGame().getWorld()) {
                            player.sendMessage("You're already in the game");
                            break;
                        }

                        for (Team team : BreakTheNexus.getInstance().getTeamManager().getTeams()) {
                            if (team.getTeamName().equalsIgnoreCase(args[1])) {
                                team.addPlayer(player.getName());
                            }
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

        }

        return true;

    }

}
