package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Civilian extends Kit {

    public Civilian() {

        super(
                new ItemStack[] {
                        new ItemStack(Material.STONE_PICKAXE),
                        new ItemStack(Material.WOOD_SWORD),
                        new ItemStack(Material.STONE_AXE),
                        new ItemStack(Material.STONE_SPADE)
                }
        );

    }

}