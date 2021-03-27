package breakthenexus.game;

import breakthenexus.BreakTheNexus;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Nexus {

    private final String teamName;

    private final Location location;

    private int health;

    public Nexus(String teamName) {

        this.teamName = teamName;

        // TODO: automatically load location from file instead
        if (this.teamName.equals("Red")) {
            location = new Location(BreakTheNexus.getInstance().getMapGame().getWorld(), 0.0, 29.0, 95.0);
        } else if (this.teamName.equals("Blue")) {
            location = new Location(BreakTheNexus.getInstance().getMapGame().getWorld(), 0.0, 29.0, -95.0);
        } else {
            location = null;
        }

        location.getBlock().setType(Material.ENDER_STONE);

        health = 10;

    }

    public final Location getLocation() {
        return location;
    }

    public final void damage() {

        if (health <= 1) {
            location.getBlock().setType(Material.BEDROCK);
            return;
        }

        health -= 1;

        for (Player player : location.getWorld().getPlayers()) {
            player.sendMessage(teamName + " nexus = " + health);
        }

    }

}
