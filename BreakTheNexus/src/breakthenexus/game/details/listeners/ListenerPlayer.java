package breakthenexus.game.details.listeners;

import breakthenexus.BreakTheNexus;
import breakthenexus.game.Mine;
import breakthenexus.game.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ListenerPlayer implements Listener {

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        playerJoinEvent.getPlayer().teleport(BreakTheNexus.getInstance().getPlaceToSpawn(playerJoinEvent.getPlayer().getName()));
    }

    @EventHandler
    private void onPlayerRespawn(PlayerRespawnEvent playerRespawnEvent) {
        playerRespawnEvent.setRespawnLocation(BreakTheNexus.getInstance().getPlaceToSpawn(playerRespawnEvent.getPlayer().getName()));
    }

    @EventHandler
    private void onFoodLevelChange(FoodLevelChangeEvent foodLevelChangeEvent) {
        if (foodLevelChangeEvent.getEntity().getWorld() == BreakTheNexus.getInstance().getMapLobby().getWorld()) {
            foodLevelChangeEvent.setCancelled(true);
        }
    }

    @EventHandler
    private void onEntityDamage(EntityDamageEvent entityDamageEvent) {

        if (!(entityDamageEvent.getEntity() instanceof Player)) {
            return;
        }

        final Player player = (Player)entityDamageEvent.getEntity();

        if (player.getWorld() == BreakTheNexus.getInstance().getMapLobby().getWorld()) {
            entityDamageEvent.setCancelled(true);
        }

        if (entityDamageEvent.getCause() == EntityDamageEvent.DamageCause.VOID) {
            player.setHealth(0.0);
        }

    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent blockBreakEvent) {

        // Nexus
        for (Team team : BreakTheNexus.getInstance().getTeams()) {
            if (team.getNexus().getLocation().equals(blockBreakEvent.getBlock().getLocation())) {

                blockBreakEvent.setCancelled(true); // Causes the nexus block to instantly respawn
                team.handleNexusAttack(blockBreakEvent.getPlayer().getName());

            }
        }

        // Mines
        for (Mine mine : BreakTheNexus.getInstance().getMines()) {
            if (mine.getMaterial() == blockBreakEvent.getBlock().getType()) {

                blockBreakEvent.setCancelled(true); // Causes the mine block to instantly respawn
                mine.handleBlockBreak(blockBreakEvent.getBlock().getLocation());

            }
        }

    }

}
