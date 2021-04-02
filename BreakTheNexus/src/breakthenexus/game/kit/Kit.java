package breakthenexus.game.kit;

import org.bukkit.inventory.ItemStack;

public abstract class Kit {

    private final String kitName = getClass().getSimpleName();

    private final ItemStack[] armorItems;
    private final ItemStack[] toolItems;
    private final ItemStack specialItem;

    public Kit(ItemStack[] armorItems, ItemStack[] toolItems, ItemStack specialItem) {
        this.armorItems = armorItems;
        this.toolItems = toolItems;
        this.specialItem = specialItem;
    }

    public final String getKitName() {
        return kitName;
    }

    public final ItemStack[] getArmorItems() {
        return armorItems;
    }

    public final ItemStack[] getToolItems() {
        return toolItems;
    }

    public final ItemStack getSpecialItem() {
        return specialItem;
    }

}
