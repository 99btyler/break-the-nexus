package breakthenexus.game.kit;

import org.bukkit.inventory.ItemStack;

public abstract class Kit {

    private final String kitName = getClass().getSimpleName();

    private final ItemStack[] armorItems;
    private final ItemStack[] toolItems;

    public Kit(ItemStack[] armorItems, ItemStack[] toolItems) {
        this.armorItems = armorItems;
        this.toolItems = toolItems;
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

}
