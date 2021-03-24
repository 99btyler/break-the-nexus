package breakthenexus;

import breakthenexus.game.Team;
import breakthenexus.game.details.commands.CommandTeam;
import breakthenexus.game.Map;
import breakthenexus.game.details.listeners.ListenerPlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class BreakTheNexus extends JavaPlugin {

    private static BreakTheNexus instance;

    private Map mapLobby;
    private Map mapGame;

    private Team teamRed;
    private Team teamBlue;

    @Override
    public void onEnable() {

        instance = this;

        mapLobby = new Map("The Lobby");
        mapGame = new Map("Roastal");

        teamRed = new Team("Red");
        teamBlue = new Team("Blue");

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

    public final Team getTeamRed() {
        return teamRed;
    }

    public final Team getTeamBlue() {
        return teamBlue;
    }

    public final Location getPlaceToSpawn(String playerName) {

        if (teamRed.has(playerName)) {
            return new Location(mapGame.getWorld(), 0.0, 20.0, 100.0, 180.0f, 0.0f);
        } else if (teamBlue.has(playerName)) {
            return new Location(mapGame.getWorld(), 0.0, 20.0, -100.0, 0.0f, 0.0f);
        } else {

            return mapLobby.getWorld().getSpawnLocation();

        }

    }

}
