package breakthenexus.game.kit;

import org.bukkit.Material;

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

    protected abstract void setToolItemMaterials();
    protected abstract void setSpecialItemMaterial();

    public final Material[] getToolItemMaterials() {
        return toolItemMaterials;
    }

    public final Material getSpecialItemMaterial() {
        return specialItemMaterial;
    }

}
