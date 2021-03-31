package breakthenexus.game.kit;

import breakthenexus.BreakTheNexus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitManager {

    private final List<Kit> kits = new ArrayList<>();

    // Key = playerName. Value = kitName.
    private final Map<String, String> kitUsers = new HashMap<>();

    public final void addKit(Kit kit) {
        kits.add(kit);
    }

    public final String getKitNameUsedBy(String playerName) {
        return kitUsers.get(playerName);
    }

    public final void updateKitUsers(String playerName, String kitName) {
        for (Kit kit : kits) {
            if (kit.getKitName().equals(kitName)) {
                kitUsers.put(playerName, kit.getKitName());
            }
        }
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
        }, 20 * 1); // 1 second

    }

}
