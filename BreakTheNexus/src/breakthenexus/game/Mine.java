package breakthenexus.game;

import breakthenexus.BreakTheNexus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Mine {

    private final Material material;
    private final Material materialOutput; // What gets added to player's inventory
    private final int blockRespawnDelay;

    public Mine(Material material, int blockRespawnDelay) {
        this.material = material;
        materialOutput = material;
        this.blockRespawnDelay = blockRespawnDelay;
    }

    public Mine(Material material, Material materialOutput, int blockRespawnDelay) {
        this.material = material;
        this.materialOutput = materialOutput;
        this.blockRespawnDelay = blockRespawnDelay;
    }

    public final Material getMaterial() {
        return material;
    }

    public final void handleBlockBreakEvent(BlockBreakEvent blockBreakEvent) {

        blockBreakEvent.getPlayer().getInventory().addItem(new ItemStack(materialOutput));
        blockBreakEvent.getBlock().setType(Material.BEDROCK);

        Bukkit.getScheduler().runTaskLater(BreakTheNexus.getInstance(), new Runnable() {
            @Override
            public void run() {
                blockBreakEvent.getBlock().setType(material);
            }
        }, 20 * blockRespawnDelay);

    }

}
