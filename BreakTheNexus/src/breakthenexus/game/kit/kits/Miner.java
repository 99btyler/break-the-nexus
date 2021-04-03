package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
import org.bukkit.Material;

public class Miner extends Kit {

    @Override
    protected void setToolItemMaterials() {

        toolItemMaterials = new Material[] {
                Material.STONE_PICKAXE,
                Material.WOOD_SWORD,
                Material.WOOD_AXE
        };

    }

    @Override
    protected void setSpecialItemMaterial() {

        specialItemMaterial = Material.GOLD_NUGGET;

    }

}
