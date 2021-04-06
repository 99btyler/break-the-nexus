package breakthenexus.game.team;

import breakthenexus.BreakTheNexus;
import breakthenexus.game.Nexus;
import org.bukkit.*;

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
        final World gameWorld = BreakTheNexus.getInstance().getMapManager().getGameWorld();
        if (this.teamName.equals("Red")) {
            spawnpoints.add(new Location(gameWorld, -8.0, 20.0, 95.0, 180.0f, 0.0f));
            spawnpoints.add(new Location(gameWorld, 8.0, 20.0, 95.0, 180.0f, 0.0f));
            spawnpoints.add(new Location(gameWorld, 0.0, 20.0, 87.0, 180.0f, 0.0f));
        } else if (this.teamName.equals("Blue")) {
            spawnpoints.add(new Location(gameWorld, 8.0, 20.0, -95.0, 0.0f, 0.0f));
            spawnpoints.add(new Location(gameWorld, -8.0, 20.0, -95.0, 0.0f, 0.0f));
            spawnpoints.add(new Location(gameWorld, 0.0, 20.0, -87.0, 0.0f, 0.0f));
        }

        // TODO: automatically load nexus location from file instead
        final Location location;
        if (this.teamName.equals("Red")) {
            location = new Location(BreakTheNexus.getInstance().getMapManager().getGameWorld(), 0.0, 29.0, 95.0);
        } else if (this.teamName.equals("Blue")) {
            location = new Location(BreakTheNexus.getInstance().getMapManager().getGameWorld(), 0.0, 29.0, -95.0);
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
            BreakTheNexus.getInstance().getKitManager().updateKitUsers(playerName, "Civilian");

            Bukkit.getPlayer(playerName).teleport(getRandomSpawnpoint());
            BreakTheNexus.getInstance().getKitManager().giveKitItemsTo(playerName);

        }

    }

    public final Location getRandomSpawnpoint() {
        return spawnpoints.get(random.nextInt(spawnpoints.size() - 0) + 0);
    }

    public final Nexus getNexus() {
        return nexus;
    }

    public final void handleNexusAttack(String attackerName) {

        if (!alive) {
            Bukkit.getPlayer(attackerName).sendMessage(teamName + " is not alive");
            return;
        }

        if (playerNames.contains(attackerName)) {
            Bukkit.getPlayer(attackerName).sendMessage("Don't attack your own nexus");
            return;
        }

        nexus.reduceHealth(1);

        if (nexus.getHealth() < 1) {

            nexus.getLocation().getBlock().setType(Material.BEDROCK);

            nexus.getLocation().getWorld().playEffect(nexus.getLocation(), Effect.EXPLOSION_HUGE, 0);
            BreakTheNexus.getInstance().getMapManager().getGameWorld().playSound(nexus.getLocation(), Sound.EXPLODE, 1.0F, 0.1F);
            BreakTheNexus.getInstance().getServer().broadcastMessage(teamName + " has been destroyed!");

            spawnpoints.clear();
            spawnpoints.add(BreakTheNexus.getInstance().getMapManager().getLobbyWorld().getSpawnLocation());

            alive = false;

            return;

        }

        nexus.getLocation().getWorld().playEffect(nexus.getLocation(), Effect.LARGE_SMOKE, 0);
        nexus.getLocation().getWorld().playEffect(nexus.getLocation(), Effect.CLOUD, 0);
        BreakTheNexus.getInstance().getMapManager().getGameWorld().playSound(nexus.getLocation(), Sound.ANVIL_LAND, 1.0F, 0.1F);
        BreakTheNexus.getInstance().getServer().broadcastMessage(attackerName + " attacked " + teamName + " nexus! (" + nexus.getHealth() + ")");

    }

    public final String getInfo() {
        return teamName.toUpperCase() + ": " + playerNames.size() + " players @ " + nexus.getHealth();
    }

}
