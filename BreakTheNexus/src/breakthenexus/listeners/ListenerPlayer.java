package breakthenexus.listeners;

import breakthenexus.BreakTheNexus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ListenerPlayer implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {

        playerJoinEvent.getPlayer().teleport(BreakTheNexus.getInstance().getMapLobby().getWorld().getSpawnLocation());

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent playerRespawnEvent) {

        if (BreakTheNexus.getInstance().getMapLobby() != null) {
            playerRespawnEvent.setRespawnLocation(BreakTheNexus.getInstance().getMapLobby().getWorld().getSpawnLocation());
        }

    }

}
