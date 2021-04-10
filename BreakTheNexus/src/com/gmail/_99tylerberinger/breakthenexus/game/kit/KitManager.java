package com.gmail._99tylerberinger.breakthenexus.game.kit;

import com.gmail._99tylerberinger.breakthenexus.BreakTheNexus;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class KitManager {

    private final Kit[] kits;

    private final Map<String, String> kitUsers = new HashMap<>(); // key = playerName, value = kitName
    private final Map<String, Boolean> cooldowns = new HashMap<>(); // key = playerName, value = isOnCooldown

    public KitManager(Kit[] kits) {
        this.kits = kits;
    }

    public final Kit[] getKits() {
        return kits;
    }

    public final String getKitNameUsedBy(String playerName) {
        return kitUsers.get(playerName);
    }

    public final boolean updateKitUsers(String playerName, String kitName) {

        for (Kit kit : kits) {
            if (kit.getName().equalsIgnoreCase(kitName)) {

                kitUsers.put(playerName, kit.getName());
                cooldowns.put(playerName, false);
                return true;

            }
        }

        return false;

    }

    public final void giveKitItemsTo(String playerName) {

        final Player player = Bukkit.getPlayer(playerName);
        final String kitName = kitUsers.get(player.getName());

        for (Kit kit : kits) {
            if (kit.getName().equals(kitName)) {

                // tools
                for (Material toolItemMaterial : kit.getToolItemMaterials()) {

                    final ItemStack toolItem = new ItemStack(toolItemMaterial);

                    if (kit.getName().equals("Miner") && toolItemMaterial == Material.STONE_PICKAXE) {
                        toolItem.addEnchantment(Enchantment.DIG_SPEED, 1);
                    }

                    final ItemMeta toolItemMeta = toolItem.getItemMeta();

                    toolItemMeta.setDisplayName("Soulbound Tool");
                    toolItemMeta.setLore(Arrays.asList("§6Soulbound"));

                    toolItem.setItemMeta(toolItemMeta);

                    player.getInventory().addItem(toolItem);

                }

                // special item
                final ItemStack specialItem = new ItemStack(kit.getSpecialItemMaterial());

                final ItemMeta specialItemMeta = specialItem.getItemMeta();

                specialItemMeta.setDisplayName("Special Item");
                specialItemMeta.setLore(Arrays.asList("§dSpecial"));

                specialItem.setItemMeta(specialItemMeta);

                player.getInventory().addItem(specialItem);

                // armor
                final ItemStack[] armorItems = new ItemStack[] {
                        new ItemStack(Material.LEATHER_BOOTS),
                        new ItemStack(Material.LEATHER_LEGGINGS),
                        new ItemStack(Material.LEATHER_CHESTPLATE),
                        new ItemStack(Material.LEATHER_HELMET)
                };

                for (ItemStack armorItem : armorItems) {

                    final ItemMeta armorItemMeta = armorItem.getItemMeta();

                    final Color color = BreakTheNexus.getInstance().getTeamManager().getTeamColorByPlayer(player.getName());
                    ((LeatherArmorMeta)armorItemMeta).setColor(color);

                    armorItemMeta.setDisplayName("Soulbound Armor");
                    armorItemMeta.setLore(Arrays.asList("§6Soulbound"));

                    armorItem.setItemMeta(armorItemMeta);

                }

                if (player.getInventory().getBoots() == null) {
                    player.getInventory().setBoots(armorItems[0]);
                }

                if (player.getInventory().getLeggings() == null) {
                    player.getInventory().setLeggings(armorItems[1]);
                }

                if (player.getInventory().getChestplate() == null) {
                    player.getInventory().setChestplate(armorItems[2]);
                }

                if (player.getInventory().getHelmet() == null) {
                    player.getInventory().setHelmet(armorItems[3]);
                }

            }
        }

    }

    public final void takeKitItemsFrom(String playerName) {

        final Player player = Bukkit.getPlayer(playerName);

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null) {

                final ItemMeta itemStackMeta = itemStack.getItemMeta();

                if (!itemStackMeta.hasLore()) {
                    continue;
                }

                if (itemStackMeta.getLore().contains("§6Soulbound") || itemStackMeta.getLore().contains("§dSpecial")) {

                    player.getInventory().remove(itemStack);

                }

            }
        }

    }

    public final void handleDoSpecial(Player player) {

        if (cooldowns.get(player.getName())) {
            player.sendMessage("30 second cooldown is still active");
            return;
        }

        final String kitName = kitUsers.get(player.getName());

        for (Kit kit : kits) {
            if (kit.getName().equals(kitName)) {

                kit.doSpecial(player);

                cooldowns.put(player.getName(), true);
                Bukkit.getServer().getScheduler().runTaskLater(BreakTheNexus.getInstance(), () -> cooldowns.put(player.getName(), false), 20 * 30); // 20 ticks = 1 second

            }
        }

    }

}
