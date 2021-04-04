package breakthenexus.game.details.listeners;

import breakthenexus.BreakTheNexus;
import breakthenexus.game.Mine;
import breakthenexus.game.Team;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ListenerPlayer implements Listener {

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {

        final Player player = playerJoinEvent.getPlayer();
        final Location placeToSpawn = BreakTheNexus.getInstance().getPlaceToSpawn(player.getName());

        player.teleport(placeToSpawn);

        if (placeToSpawn.getWorld() == BreakTheNexus.getInstance().getMapLobby().getWorld()) {

            player.getInventory().clear();
            player.setHealth(20.0);
            player.setFoodLevel(20);

        }

    }

    @EventHandler
    private void onPlayerRespawn(PlayerRespawnEvent playerRespawnEvent) {

        final Player player = playerRespawnEvent.getPlayer();
        final Location placeToSpawn = BreakTheNexus.getInstance().getPlaceToSpawn(player.getName());

        playerRespawnEvent.setRespawnLocation(placeToSpawn);

        if (placeToSpawn.getWorld() == BreakTheNexus.getInstance().getMapGame().getWorld()) {

            BreakTheNexus.getInstance().getKitManager().giveKitItemsTo(player.getName());

        }

    }

    @EventHandler
    private void onFoodLevelChange(FoodLevelChangeEvent foodLevelChangeEvent) {

        if (foodLevelChangeEvent.getEntity().getWorld() == BreakTheNexus.getInstance().getMapLobby().getWorld()) {
            foodLevelChangeEvent.setCancelled(true);
        }

    }

    @EventHandler
    private void onEntityDamage(EntityDamageEvent entityDamageEvent) {

        if (entityDamageEvent.getEntity().getWorld() == BreakTheNexus.getInstance().getMapLobby().getWorld()) {
            entityDamageEvent.setCancelled(true);
        }

        if (entityDamageEvent.getEntity() instanceof Player) {
            if (entityDamageEvent.getCause() == EntityDamageEvent.DamageCause.VOID) {
                ((Player)entityDamageEvent.getEntity()).setHealth(0.0);
            }
        }

    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent blockBreakEvent) {

        if (blockBreakEvent.getBlock().getWorld() == BreakTheNexus.getInstance().getMapLobby().getWorld()) {
            blockBreakEvent.setCancelled(true);
            return;
        }

        // Nexus
        for (Team team : BreakTheNexus.getInstance().getTeamManager().getTeams()) {
            if (team.getNexus().getLocation().equals(blockBreakEvent.getBlock().getLocation())) {

                blockBreakEvent.setCancelled(true); // Causes the nexus block to instantly respawn
                team.handleNexusAttack(blockBreakEvent.getPlayer().getName());
                return;

            }
        }

        // Mines
        for (Mine mine : BreakTheNexus.getInstance().getMineManager().getMines()) {
            if (mine.getMaterial() == blockBreakEvent.getBlock().getType()) {

                blockBreakEvent.setCancelled(true); // Causes the mine block to instantly respawn
                mine.handleBlockBreakEvent(blockBreakEvent);
                return;

            }
        }

    }

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent playerDropItemEvent) {

        final ItemStack itemStack = playerDropItemEvent.getItemDrop().getItemStack();
        final ItemMeta itemStackMeta = itemStack.getItemMeta();

        if (itemStackMeta.hasLore()) {

            final String lore = itemStackMeta.getLore().get(0);
            final Player player = playerDropItemEvent.getPlayer();

            switch (lore) {

                case "§6Soulbound":
                    playerDropItemEvent.getItemDrop().remove();
                    BreakTheNexus.getInstance().getMapGame().getWorld().playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 1.5F);
                    break;

                case "§dSoulbound":
                    playerDropItemEvent.setCancelled(true);
                    break;

            }

        }

    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent playerDeathEvent) {

        final List<ItemStack> dropsToRemove = new ArrayList<>();

        for (ItemStack drop : playerDeathEvent.getDrops()) {

            final ItemMeta dropMeta = drop.getItemMeta();

            if (dropMeta.hasLore()) {

                final String lore = dropMeta.getLore().get(0);

                if (lore.equals("§6Soulbound") || lore.equals("§dSoulbound")) {

                    dropsToRemove.add(drop);

                }

            }

        }

        for (ItemStack dropToRemove : dropsToRemove) {
            playerDeathEvent.getDrops().remove(dropToRemove);
        }

    }

}
