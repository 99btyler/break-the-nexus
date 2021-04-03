package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
import org.bukkit.Material;

public class Civilian extends Kit {

    @Override
    protected void setToolItemMaterials() {

        toolItemMaterials = new Material[] {
                Material.STONE_PICKAXE,
                Material.WOOD_SWORD,
                Material.STONE_AXE,
                Material.STONE_SPADE
        };

    }

    @Override
    protected void setSpecialItemMaterial() {

        specialItemMaterial = Material.CLAY_BRICK;

    }

}
