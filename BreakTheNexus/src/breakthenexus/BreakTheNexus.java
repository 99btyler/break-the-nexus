package breakthenexus;

import breakthenexus.game.Map;
import breakthenexus.game.Team;
import breakthenexus.game.details.commands.CommandTeam;
import breakthenexus.game.details.listeners.ListenerPlayer;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class BreakTheNexus extends JavaPlugin {

    private static BreakTheNexus instance;

    private Map mapLobby;
    private Map mapGame;

    private List<Team> teams = new ArrayList<>();

    @Override
    public void onEnable() {

        instance = this;

        mapLobby = new Map("The Lobby");
        mapGame = new Map("Roastal");

        teams.add(new Team("Red"));
        teams.add(new Team("Blue"));

        getCommand("team").setExecutor(new CommandTeam());

        getServer().getPluginManager().registerEvents(new ListenerPlayer(), this);

    }

    public static final BreakTheNexus getInstance() {
        return instance;
    }

    public final Map getMapLobby() {
        return mapLobby;
    }

    public final Map getMapGame() {
        return mapGame;
    }

    public final Team[] getTeams() {
        return teams.toArray(new Team[0]);
    }

    public final Team getTeam(String teamName) {
        for (Team team : teams) {
            if (team.getTeamName().equals(teamName)) {
                return team;
            }
        }
        return null;
    }

    public final Location getPlaceToSpawn(String playerName) {
        for (Team team : teams) {
            if (team.hasPlayer(playerName)) {
                return team.getRandomSpawnpoint();
            }
        }
        return mapLobby.getWorld().getSpawnLocation();
    }

}
