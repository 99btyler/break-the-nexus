package breakthenexus;

import breakthenexus.listeners.ListenerPlayer;
import breakthenexus.map.Map;
import org.bukkit.plugin.java.JavaPlugin;

public class BreakTheNexus extends JavaPlugin {

    private static BreakTheNexus instance;

    private Map mapLobby;

    @Override
    public void onEnable() {

        instance = this;

        mapLobby = new Map("The Lobby");

        getServer().getPluginManager().registerEvents(new ListenerPlayer(), this);

    }

    public static final BreakTheNexus getInstance() {
        return instance;
    }

    public final Map getMapLobby() {
        return mapLobby;
    }

}
