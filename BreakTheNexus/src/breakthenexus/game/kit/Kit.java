package breakthenexus.game.kit;

import org.bukkit.inventory.ItemStack;

public abstract class Kit {

    private final String kitName;

    private final ItemStack[] items;

    public Kit(ItemStack[] items) {

        kitName = getClass().getSimpleName();

        this.items = items;

    }

    public final String getKitName() {
        return kitName;
    }

    public final ItemStack[] getItems() {
        return items;
    }

}
