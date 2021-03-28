package breakthenexus.game;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Nexus {

    private final Location location;
    private int health;

    public Nexus(Location location) {

        this.location = location;
        this.location.getBlock().setType(Material.ENDER_STONE);

        health = 10;

    }

    public final Location getLocation() {
        return location;
    }

    public final boolean damageNexus(String attackerMessage) {

        if (health <= 1) {
            location.getBlock().setType(Material.BEDROCK);
            return true; // Nexus has been destroyed
        }

        health -= 1;

        for (Player player : location.getWorld().getPlayers()) {
            player.sendMessage(attackerMessage + " (" + health + ")");
        }

        return false;

    }

}
