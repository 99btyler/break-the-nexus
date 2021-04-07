package com.gmail._99tylerberinger.breakthenexus.game.details.listeners;

import com.gmail._99tylerberinger.breakthenexus.BreakTheNexus;
import com.gmail._99tylerberinger.breakthenexus.game.mine.Mine;
import com.gmail._99tylerberinger.breakthenexus.game.team.Team;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ListenerPlayer implements Listener {

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {

        playerJoinEvent.setJoinMessage(null);

        final Player player = playerJoinEvent.getPlayer();
        final Location placeToSpawn = BreakTheNexus.getInstance().getPlaceToSpawn(player.getName());

        player.teleport(placeToSpawn);

        if (placeToSpawn.getWorld() == BreakTheNexus.getInstance().getGamemapManager().getLobbyWorld()) {

            player.getInventory().clear();
            player.setHealth(20.0);
            player.setFoodLevel(20);

        }

    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent playerQuitEvent) {

        playerQuitEvent.setQuitMessage(null);

        BreakTheNexus.getInstance().getGamemapManager().saveDisconnectLocation(playerQuitEvent.getPlayer().getName(), playerQuitEvent.getPlayer().getLocation());

    }

    @EventHandler
    private void onPlayerRespawn(PlayerRespawnEvent playerRespawnEvent) {

        final Player player = playerRespawnEvent.getPlayer();
        final Location placeToSpawn = BreakTheNexus.getInstance().getPlaceToSpawn(player.getName());

        playerRespawnEvent.setRespawnLocation(placeToSpawn);

        if (placeToSpawn.getWorld() == BreakTheNexus.getInstance().getGamemapManager().getGameWorld()) {

            // For some reason, player is briefly null after PlayerRespawnEvent.
            // This seems to be a Bukkit thing. My workaround: wait 1 second
            final BreakTheNexus breakTheNexus = BreakTheNexus.getInstance();
            breakTheNexus.getServer().getScheduler().runTaskLater(breakTheNexus, new Runnable() {
                @Override
                public void run() {
                    breakTheNexus.getKitManager().giveKitItemsTo(player.getName());
                }
            }, 20 * 1); // 20 ticks = 1 second

        }

    }

    @EventHandler
    private void onFoodLevelChange(FoodLevelChangeEvent foodLevelChangeEvent) {

        if (foodLevelChangeEvent.getEntity().getWorld() == BreakTheNexus.getInstance().getGamemapManager().getLobbyWorld()) {
            foodLevelChangeEvent.setCancelled(true);
        }

    }

    @EventHandler
    private void onEntityDamage(EntityDamageEvent entityDamageEvent) {

        if (entityDamageEvent.getEntity().getWorld() == BreakTheNexus.getInstance().getGamemapManager().getLobbyWorld()) {
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

        if (blockBreakEvent.getBlock().getWorld() == BreakTheNexus.getInstance().getGamemapManager().getLobbyWorld()) {
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
    private void onPlayerInteract(PlayerInteractEvent playerInteractEvent) {

        final Action action = playerInteractEvent.getAction();

        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        final ItemStack itemStack = playerInteractEvent.getItem();

        if (itemStack == null) {
            return;
        }

        final ItemMeta itemStackMeta = itemStack.getItemMeta();

        if (!itemStackMeta.hasLore()) {
            return;
        }

        if (itemStackMeta.getLore().contains("§dSpecial")) {
            BreakTheNexus.getInstance().getKitManager().handleDoSpecial(playerInteractEvent.getPlayer());
        }

    }

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent playerDropItemEvent) {

        final ItemMeta itemStackMeta = playerDropItemEvent.getItemDrop().getItemStack().getItemMeta();

        if (!itemStackMeta.hasLore()) {
            return;
        }

        if (itemStackMeta.getLore().contains("§6Soulbound")) {

            playerDropItemEvent.getItemDrop().remove();
            BreakTheNexus.getInstance().getGamemapManager().getGameWorld().playSound(playerDropItemEvent.getPlayer().getLocation(), Sound.ITEM_BREAK, 1.0F, 1.5F);

        } else if (itemStackMeta.getLore().contains("§dSpecial")) {

            playerDropItemEvent.setCancelled(true);

        }

    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent playerDeathEvent) {

        final List<ItemStack> dropsToRemove = new ArrayList<>();

        for (ItemStack drop : playerDeathEvent.getDrops()) {

            final ItemMeta dropMeta = drop.getItemMeta();

            if (!dropMeta.hasLore()) {
                continue;
            }

            if (dropMeta.getLore().contains("§6Soulbound") || dropMeta.getLore().contains("§dSpecial")) {
                dropsToRemove.add(drop);
            }

        }

        for (ItemStack dropToRemove : dropsToRemove) {
            playerDeathEvent.getDrops().remove(dropToRemove);
        }

    }

}
