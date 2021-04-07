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
            if (kit.getKitName().equalsIgnoreCase(kitName)) {

                kitUsers.put(playerName, kit.getKitName());
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
            if (kit.getKitName().equals(kitName)) {

                // tools
                for (Material toolMaterial : kit.getTools()) {

                    final ItemStack tool = new ItemStack(toolMaterial);

                    if (kitName.equals("Miner") && toolMaterial == Material.STONE_PICKAXE) {
                        tool.addEnchantment(Enchantment.DIG_SPEED, 1);
                    }

                    final ItemMeta toolItemMeta = tool.getItemMeta();

                    toolItemMeta.setDisplayName("Soulbound Tool");
                    toolItemMeta.setLore(Arrays.asList("§6Soulbound"));

                    tool.setItemMeta(toolItemMeta);

                    player.getInventory().addItem(tool);

                }

                // special item
                final ItemStack specialItem = new ItemStack(kit.getSpecialItem());

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

                    Color color = null;
                    switch (BreakTheNexus.getInstance().getTeamManager().getTeamByPlayer(player.getName()).getTeamName()) {
                        case "Red":
                            color = Color.RED;
                            break;
                        case "Blue":
                            color = Color.BLUE;
                            break;
                    }
                    ((LeatherArmorMeta)armorItemMeta).setColor(color);

                    armorItemMeta.setDisplayName("Soulbound Armor");
                    armorItemMeta.setLore(Arrays.asList("§6Soulbound"));

                    armorItem.setItemMeta(armorItemMeta);

                }

                player.getInventory().setArmorContents(armorItems);

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
            if (kit.getKitName().equals(kitName)) {

                kit.doSpecial(player);

                cooldowns.put(player.getName(), true);
                Bukkit.getServer().getScheduler().runTaskLater(BreakTheNexus.getInstance(), () -> cooldowns.put(player.getName(), false), 20 * 30); // 20 ticks = 1 second

            }
        }

    }

}
