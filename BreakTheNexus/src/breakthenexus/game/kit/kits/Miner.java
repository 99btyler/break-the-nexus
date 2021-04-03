package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Miner extends Kit {

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

        final ItemStack enchantedPickaxe = new ItemStack(Material.STONE_PICKAXE);
        enchantedPickaxe.addEnchantment(Enchantment.DIG_SPEED, 1);

        toolItems = new ItemStack[] {
                enchantedPickaxe,
                new ItemStack(Material.WOOD_SWORD),
                new ItemStack(Material.WOOD_AXE)
        };

    }

    @Override
    protected void setSpecialItem() {

        specialItem = new ItemStack(Material.GOLD_NUGGET);

    }

}
