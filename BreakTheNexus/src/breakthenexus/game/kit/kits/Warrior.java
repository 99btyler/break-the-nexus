package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Warrior extends Kit {

    @Override
    protected void setArmorItems() {

        armorItems = new ItemStack[] {
                new ItemStack(Material.LEATHER_BOOTS),
                new ItemStack(Material.LEATHER_LEGGINGS),
                new ItemStack(Material.LEATHER_CHESTPLATE),
                new ItemStack(Material.LEATHER_HELMET)
        };

    }

    @Override
    protected void setToolItems() {

        toolItems = new ItemStack[] {
                new ItemStack(Material.WOOD_PICKAXE),
                new ItemStack(Material.STONE_SWORD),
                new ItemStack(Material.WOOD_AXE)
        };

    }

    @Override
    protected void setSpecialItem() {

        specialItem = new ItemStack(Material.BLAZE_POWDER);

    }

}
