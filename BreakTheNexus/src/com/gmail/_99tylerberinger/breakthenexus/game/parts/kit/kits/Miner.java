package com.gmail._99tylerberinger.breakthenexus.game.parts.kit.kits;

import com.gmail._99tylerberinger.breakthenexus.game.parts.kit.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Miner extends Kit {

    public Miner() {

        super(
                new Material[] {
                      Material.STONE_PICKAXE,
                      Material.WOOD_SWORD,
                      Material.WOOD_AXE
                },
                Material.GOLD_NUGGET
        );

    }

    @Override
    public void doSpecial(Player player) {

        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, (20 * 15), 0)); // 20 ticks = 1 second

        player.sendMessage(ChatColor.LIGHT_PURPLE + "You now have Haste for 15 seconds!");
        player.getWorld().playEffect(player.getLocation().add(0, 2, 0), Effect.COLOURED_DUST, 0);

    }

}
