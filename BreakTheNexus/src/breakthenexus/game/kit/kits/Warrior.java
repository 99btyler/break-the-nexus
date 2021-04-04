package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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

    @Override
    public void doSpecial(Player player) {

        // TODO: Warrior special ability

    }

}
