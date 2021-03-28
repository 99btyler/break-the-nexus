package breakthenexus.game;

import breakthenexus.BreakTheNexus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class Mine {

    private final Material material;

    public Mine(Material material) {
        this.material = material;
    }

    public final Material getMaterial() {
        return material;
    }

    public final void handleBlockBreak(Location blockLocation) {

        blockLocation.getBlock().setType(Material.BEDROCK);

        Bukkit.getScheduler().runTaskLater(BreakTheNexus.getInstance(), new Runnable() {
            @Override
            public void run() {
                blockLocation.getBlock().setType(material);
            }
        }, 20 * 5);

    }

}
