package com.gmail._99tylerberinger.breakthenexus.game.parts.mine;

import com.gmail._99tylerberinger.breakthenexus.BreakTheNexus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Mine {

    private final Material material;

    private final Material materialOutput;
    private final int materialOutputAmount;
    private final int blockRespawnDelay;

    public Mine(Material material, int blockRespawnDelay) {

        this.material = material;

        materialOutput = material;
        materialOutputAmount = 1;
        this.blockRespawnDelay = blockRespawnDelay;

    }

    public Mine(Material material, Material materialOutput, int blockRespawnDelay) {

        this.material = material;

        this.materialOutput = materialOutput;
        materialOutputAmount = 1;
        this.blockRespawnDelay = blockRespawnDelay;

    }

    public Mine(Material material, Material materialOutput, int materialOutputAmount, int blockRespawnDelay) {

        this.material = material;

        this.materialOutput = materialOutput;
        this.materialOutputAmount = materialOutputAmount;
        this.blockRespawnDelay = blockRespawnDelay;

    }

    public final Material getMaterial() {
        return material;
    }

    public final void handleBlockBreakEvent(BlockBreakEvent blockBreakEvent) {

        // For logs (storing original direction)
        final byte blockData = blockBreakEvent.getBlock().getData();

        blockBreakEvent.getBlock().setType(Material.BEDROCK);
        blockBreakEvent.getPlayer().getInventory().addItem(new ItemStack(materialOutput, materialOutputAmount));

        Bukkit.getScheduler().runTaskLater(BreakTheNexus.getInstance(), new Runnable() {
            @Override
            public void run() {

                blockBreakEvent.getBlock().setType(material);

                if (material == Material.LOG) {
                    blockBreakEvent.getBlock().setData(blockData);
                }

            }
        }, 20 * blockRespawnDelay); // 20 ticks = 1 second

    }

}
