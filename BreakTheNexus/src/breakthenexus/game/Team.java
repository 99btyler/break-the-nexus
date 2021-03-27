package breakthenexus.game;

import breakthenexus.BreakTheNexus;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Team {

    private final String teamName;

    private final List<String> playerNames = new ArrayList<>();

    private final Location[] spawnpoints = new Location[3];
    private final Random random = new Random();

    private final Nexus nexus;

    public Team(String teamName) {

        this.teamName = teamName;

        // TODO: automatically load spawnpoints from file instead
        if (this.teamName.equals("Red")) {
            spawnpoints[0] = new Location(BreakTheNexus.getInstance().getMapGame().getWorld(), -8.0, 20.0, 95.0, 180.0f, 0.0f);
            spawnpoints[1] = new Location(BreakTheNexus.getInstance().getMapGame().getWorld(), 8.0, 20.0, 95.0, 180.0f, 0.0f);
            spawnpoints[2] = new Location(BreakTheNexus.getInstance().getMapGame().getWorld(), 0.0, 20.0, 87.0, 180.0f, 0.0f);
        } else if (this.teamName.equals("Blue")) {
            spawnpoints[0] = new Location(BreakTheNexus.getInstance().getMapGame().getWorld(), 8.0, 20.0, -95.0, 0.0f, 0.0f);
            spawnpoints[1] = new Location(BreakTheNexus.getInstance().getMapGame().getWorld(), -8.0, 20.0, -95.0, 0.0f, 0.0f);
            spawnpoints[2] = new Location(BreakTheNexus.getInstance().getMapGame().getWorld(), 0.0, 20.0, -87.0, 0.0f, 0.0f);
        }

        // TODO: automatically load nexus location from file instead
        final Location location;
        if (this.teamName.equals("Red")) {
            location = new Location(BreakTheNexus.getInstance().getMapGame().getWorld(), 0.0, 29.0, 95.0);
        } else if (this.teamName.equals("Blue")) {
            location = new Location(BreakTheNexus.getInstance().getMapGame().getWorld(), 0.0, 29.0, -95.0);
        } else {
            location = null;
        }
        nexus = new Nexus(location);

    }

    public final String getTeamName() {
        return teamName;
    }

    public final void join(String playerName) {
        if (!playerNames.contains(playerName)) {
            playerNames.add(playerName);
            Bukkit.getPlayer(playerName).teleport(getRandomSpawnpoint());
        }
    }

    public final boolean has(String playerName) {
        return playerNames.contains(playerName);
    }

    public final Location getRandomSpawnpoint() {
        return spawnpoints[random.nextInt(spawnpoints.length - 0) + 0];
    }

    public final Nexus getNexus() {
        return nexus;
    }

    public final void end() {

        for (int i = 0; i < spawnpoints.length; i++) {
            spawnpoints[i] = BreakTheNexus.getInstance().getMapLobby().getWorld().getSpawnLocation();
        }

        for (String playerName : playerNames) {
            Bukkit.getPlayer(playerName).setHealth(0);
        }

    }

    public final String getInfo() {
        return teamName.toUpperCase() + ": " + playerNames.size() + " players";
    }

}
