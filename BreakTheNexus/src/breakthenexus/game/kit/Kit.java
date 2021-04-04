package breakthenexus.game.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Kit {

    private final String kitName = getClass().getSimpleName();

    protected Material[] toolItemMaterials = null;
    protected Material specialItemMaterial = null;

    public Kit() {

        setToolItemMaterials();
        setSpecialItemMaterial();

    }

    public final String getKitName() {
        return kitName;
    }

    public final Material[] getToolItemMaterials() {
        return toolItemMaterials;
    }

    protected abstract void setToolItemMaterials();

    public final Material getSpecialItemMaterial() {
        return specialItemMaterial;
    }

    protected abstract void setSpecialItemMaterial();

    public abstract void doSpecial(Player player);

}
