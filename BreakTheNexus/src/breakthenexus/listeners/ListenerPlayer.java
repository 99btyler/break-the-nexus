package breakthenexus.listeners;

import breakthenexus.BreakTheNexus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenerPlayer implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {

        playerJoinEvent.getPlayer().teleport(BreakTheNexus.getInstance().getMapLobby().getWorld().getSpawnLocation());

    }

}
