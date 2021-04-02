package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Miner extends Kit {

    public Miner() {

        super(
                new ItemStack[] {
                        new ItemStack(Material.LEATHER_HELMET),
                        new ItemStack(Material.LEATHER_CHESTPLATE),
                        new ItemStack(Material.LEATHER_LEGGINGS),
                        new ItemStack(Material.LEATHER_BOOTS)
                },
                new ItemStack[] {
                        new ItemStack(Material.STONE_PICKAXE),
                        new ItemStack(Material.WOOD_SWORD),
                        new ItemStack(Material.WOOD_AXE)
                }
        );

    }

}
