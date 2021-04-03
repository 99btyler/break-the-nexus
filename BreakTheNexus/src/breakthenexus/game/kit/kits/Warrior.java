package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
import org.bukkit.Material;

public class Warrior extends Kit {

    @Override
    protected void setToolItemMaterials() {

        toolItemMaterials = new Material[] {
                Material.WOOD_PICKAXE,
                Material.STONE_SWORD,
                Material.WOOD_AXE
        };

    }

    @Override
    protected void setSpecialItemMaterial() {

        specialItemMaterial = Material.BLAZE_POWDER;

    }

}
