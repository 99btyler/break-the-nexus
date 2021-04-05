package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Warrior extends Kit {

    public Warrior() {

        super(
                new Material[] {
                        Material.WOOD_PICKAXE,
                        Material.STONE_SWORD,
                        Material.WOOD_AXE
                },
                Material.BLAZE_POWDER
        );

    }

    @Override
    public void doSpecial(Player player) {

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (20 * 15), 0));
        player.sendMessage(ChatColor.LIGHT_PURPLE + "You now have Speed for 15 seconds!");

    }

}
