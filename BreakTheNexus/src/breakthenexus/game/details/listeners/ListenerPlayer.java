package breakthenexus.game.details.listeners;

import breakthenexus.BreakTheNexus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
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

    @EventHandler
    public void onEntityDamage(EntityDamageEvent entityDamageEvent) {

        if (!(entityDamageEvent.getEntity() instanceof Player)) {
            return;
        }

        if (entityDamageEvent.getEntity().getWorld() == BreakTheNexus.getInstance().getMapLobby().getWorld()) {

            entityDamageEvent.setCancelled(true);

            if (entityDamageEvent.getCause() == EntityDamageEvent.DamageCause.VOID) {
                entityDamageEvent.getEntity().teleport(BreakTheNexus.getInstance().getPlaceToSpawn(entityDamageEvent.getEntity().getName()));
            }

        }

    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent foodLevelChangeEvent) {
        if (foodLevelChangeEvent.getEntity().getWorld() == BreakTheNexus.getInstance().getMapLobby().getWorld()) {
            foodLevelChangeEvent.setCancelled(true);
        }
    }

}
