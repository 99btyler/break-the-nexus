package breakthenexus.game.kit;

import org.bukkit.inventory.ItemStack;

public abstract class Kit {

    private final String kitName = getClass().getSimpleName();

    protected ItemStack[] armorItems = null;
    protected ItemStack[] toolItems = null;
    protected ItemStack specialItem = null;

    public Kit() {

        setArmorItems();
        setToolItems();
        setSpecialItem();

    }

    public final String getKitName() {
        return kitName;
    }

    protected abstract void setArmorItems();
    protected abstract void setToolItems();
    protected abstract void setSpecialItem();

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
