package breakthenexus.game.details.listeners;

import breakthenexus.BreakTheNexus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ListenerPlayer implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        playerJoinEvent.getPlayer().teleport(BreakTheNexus.getInstance().getPlaceToSpawn(playerJoinEvent.getPlayer().getName()));
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent playerRespawnEvent) {
        playerRespawnEvent.setRespawnLocation(BreakTheNexus.getInstance().getPlaceToSpawn(playerRespawnEvent.getPlayer().getName()));
    }

}
