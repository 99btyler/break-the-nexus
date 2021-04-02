package breakthenexus.managers;

import breakthenexus.BreakTheNexus;
import breakthenexus.game.kit.Kit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class KitManager {

    private final Kit[] kits;

    // key = playerName, value = kitName
    private final Map<String, String> kitUsers = new HashMap<>();

    public KitManager(Kit[] kits) {
        this.kits = kits;
    }

    public final Kit[] getKits() {
        return kits;
    }

    public final String getKitNameUsedBy(String playerName) {
        return kitUsers.get(playerName);
    }

    public final boolean updateKitUsers(String playerName, String kitName) {
        for (Kit kit : kits) {
            if (kit.getKitName().equalsIgnoreCase(kitName)) {

                kitUsers.put(playerName, kit.getKitName());
                return true; // Update succeeded

            }
        }
        return false; // Update failed
    }

    public final void giveKitItemsTo(String playerName) {

        // For some reason, Bukkit.getPlayer() returns null when called immediately after onPlayerRespawn().
        // This seems to be a Bukkit thing. My workaround: wait 1 second
        Bukkit.getScheduler().runTaskLater(BreakTheNexus.getInstance(), new Runnable() {
            @Override
            public void run() {

                final Player player = Bukkit.getPlayer(playerName);
                final String kitName = kitUsers.get(playerName);

                for (Kit kit : kits) {
                    if (kit.getKitName().equals(kitName)) {

                        for (ItemStack itemStack : kit.getItems()) {
                            player.getInventory().addItem(itemStack);
                        }

                    }
                }

            }
        }, 20 * 1); // 20 ticks = 1 second

    }

}
