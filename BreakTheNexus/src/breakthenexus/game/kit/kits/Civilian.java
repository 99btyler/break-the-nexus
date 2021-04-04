package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Civilian extends Kit {

    public Civilian() {

        super(
                new Material[] {
                       Material.STONE_PICKAXE,
                       Material.WOOD_SWORD,
                       Material.STONE_AXE,
                       Material.STONE_SPADE
                },
                Material.CLAY_BRICK
        );

    }

    @Override
    public void doSpecial(Player player) {

        // TODO: Civilian special ability

    }

}
