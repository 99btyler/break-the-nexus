package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Warrior extends Kit {

    public Warrior() {

        super(
                new Material[] {
                        Material.WOOD_PICKAXE,
                        Material.STONE_SWORD,
                        Material.WOOD_AXE
                },
                Material.BLAZE_POWDER
        );

    }

    @Override
    public void doSpecial(Player player) {

        // TODO: Civilian special ability

    }

}
