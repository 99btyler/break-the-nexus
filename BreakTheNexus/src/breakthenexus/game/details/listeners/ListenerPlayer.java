package breakthenexus.game.details.listeners;

import breakthenexus.BreakTheNexus;
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

        final Player player = (Player)entityDamageEvent.getEntity();

        if (player.getWorld() == BreakTheNexus.getInstance().getMapLobby().getWorld()) {
            entityDamageEvent.setCancelled(true);
        }

        if (entityDamageEvent.getCause() == EntityDamageEvent.DamageCause.VOID) {
            player.setHealth(0.0);
        }

    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent foodLevelChangeEvent) {
        if (foodLevelChangeEvent.getEntity().getWorld() == BreakTheNexus.getInstance().getMapLobby().getWorld()) {
            foodLevelChangeEvent.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {

        for (Team team : BreakTheNexus.getInstance().getTeams()) {
            if (blockBreakEvent.getBlock().getLocation().equals(team.getNexus().getLocation())) {

                blockBreakEvent.setCancelled(true);

                if (team.has(blockBreakEvent.getPlayer().getName())) {

                    blockBreakEvent.getPlayer().sendMessage("Don't break your own nexus");

                } else {

                    final boolean destroyed = team.getNexus().damage(blockBreakEvent.getPlayer().getName() + " attacked " + team.getTeamName() + " nexus!");

                    if (destroyed) {

                        for (Player player : team.getNexus().getLocation().getWorld().getPlayers()) {
                            player.sendMessage(team.getTeamName() + " has been destroyed!");
                        }

                        team.end();

                    }

                }
            }
        }

    }

}
