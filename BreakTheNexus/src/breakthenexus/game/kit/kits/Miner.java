package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
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

        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, (20 * 15), 0));

        player.getWorld().playEffect(player.getLocation().add(0, 2, 0), Effect.COLOURED_DUST, 0);
        player.sendMessage(ChatColor.LIGHT_PURPLE + "You now have Haste for 15 seconds!");

    }

}
