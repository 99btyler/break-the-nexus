package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Civilian extends Kit {

    @Override
    protected void setToolItems() {

        toolItems = new ItemStack[] {
                new ItemStack(Material.STONE_PICKAXE),
                new ItemStack(Material.WOOD_SWORD),
                new ItemStack(Material.STONE_AXE),
                new ItemStack(Material.STONE_SPADE)
        };

    }

    @Override
    protected void setSpecialItem() {

        specialItem = new ItemStack(Material.CLAY_BRICK);

    }

}
