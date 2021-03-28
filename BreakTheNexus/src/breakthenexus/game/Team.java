package breakthenexus.game;

import breakthenexus.BreakTheNexus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Team {

    private final String teamName;
    private boolean alive;

    private final List<String> playerNames = new ArrayList<>();

    private final List<Location> spawnpoints = new ArrayList<>();
    private final Random random = new Random(); // Used for picking random spawnpoint

    private final Nexus nexus;

    public Team(String teamName) {

        this.teamName = teamName;
        alive = true;

        // TODO: automatically load spawnpoints from file instead
        final World mapGameWorld = BreakTheNexus.getInstance().getMapGame().getWorld();
        if (this.teamName.equals("Red")) {
            spawnpoints.add(new Location(mapGameWorld, -8.0, 20.0, 95.0, 180.0f, 0.0f));
            spawnpoints.add(new Location(mapGameWorld, 8.0, 20.0, 95.0, 180.0f, 0.0f));
            spawnpoints.add(new Location(mapGameWorld, 0.0, 20.0, 87.0, 180.0f, 0.0f));
        } else if (this.teamName.equals("Blue")) {
            spawnpoints.add(new Location(mapGameWorld, 8.0, 20.0, -95.0, 0.0f, 0.0f));
            spawnpoints.add(new Location(mapGameWorld, -8.0, 20.0, -95.0, 0.0f, 0.0f));
            spawnpoints.add(new Location(mapGameWorld, 0.0, 20.0, -87.0, 0.0f, 0.0f));
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

    public final boolean hasPlayer(String playerName) {
        return playerNames.contains(playerName);
    }

    public final void addPlayer(String playerName) {
        if (!playerNames.contains(playerName)) {
            playerNames.add(playerName);
            Bukkit.getPlayer(playerName).teleport(getRandomSpawnpoint());
        }
    }

    public final Location getRandomSpawnpoint() {
        return spawnpoints.get(random.nextInt(spawnpoints.size() - 0) + 0);
    }

    public final Nexus getNexus() {
        return nexus;
    }

    public final void handleNexusAttack(String attackerName) {

        if (playerNames.contains(attackerName)) {
            Bukkit.getPlayer(attackerName).sendMessage("Don't attack your own nexus");
            return;
        }

        nexus.reduceHealth(1);

        for (Player player : nexus.getLocation().getWorld().getPlayers()) {
            player.sendMessage(attackerName + " attacked " + teamName + " nexus! (" + nexus.getHealth() + ")");
        }

        if (nexus.getHealth() < 1) {

            nexus.getLocation().getBlock().setType(Material.BEDROCK);

            if (alive) {

                for (Player player : nexus.getLocation().getWorld().getPlayers()) {
                    player.sendMessage(teamName + " has been destroyed!");
                }

                kill();

            }

        }

    }

    public final void kill() {

        spawnpoints.clear();
        spawnpoints.add(BreakTheNexus.getInstance().getMapLobby().getWorld().getSpawnLocation());

        for (String playerName : playerNames) {
            Bukkit.getPlayer(playerName).setHealth(0);
        }

        alive = false;

    }

    public final String getInfo() {
        return teamName.toUpperCase() + ": " + playerNames.size() + " players";
    }

}
