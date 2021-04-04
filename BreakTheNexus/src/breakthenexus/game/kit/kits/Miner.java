package breakthenexus.game.kit.kits;

import breakthenexus.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Miner extends Kit {

    public Miner() {

        super(
                new Material[] {
                      Material.STONE_PICKAXE,
                      Material.WOOD_SWORD,
                      Material.WOOD_AXE
                },
                Material.GOLD_NUGGET
        );

    }

    @Override
    public void doSpecial(Player player) {

        // TODO: Civilian special ability

    }

}
