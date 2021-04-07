package com.gmail._99tylerberinger.breakthenexus.game.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Kit {

    private final String name = getClass().getSimpleName();

    private final Material[] toolItemMaterials;
    private final Material specialItemMaterial;

    public Kit(Material[] toolItemMaterials, Material specialItemMaterial) {
        this.toolItemMaterials = toolItemMaterials;
        this.specialItemMaterial = specialItemMaterial;
    }

    public final String getName() {
        return name;
    }

    public final Material[] getToolItemMaterials() {
        return toolItemMaterials;
    }

    public final Material getSpecialItemMaterial() {
        return specialItemMaterial;
    }

    public abstract void doSpecial(Player player);

}
