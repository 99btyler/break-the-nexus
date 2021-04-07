package com.gmail._99tylerberinger.breakthenexus.game.team;

import com.gmail._99tylerberinger.breakthenexus.BreakTheNexus;
import com.gmail._99tylerberinger.breakthenexus.game.Nexus;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Team {

    private final String name;

    private boolean alive;

    private final List<String> playerNames = new ArrayList<>();

    private final List<Location> spawnpoints = new ArrayList<>();

    private final Nexus nexus;

    public Team(String name, ConfigurationSection locations) {

        this.name = name;

        alive = true;

        final World gameWorld = BreakTheNexus.getInstance().getGamemapManager().getGameWorld();

        for (int i = 0; i < 3; i++) {
            final String[] locationsSpawnpoint = locations.getString(".spawnpoints." + name + "." + i).split(";");
            spawnpoints.add(new Location(gameWorld, Double.parseDouble(locationsSpawnpoint[0]), Double.parseDouble(locationsSpawnpoint[1]), Double.parseDouble(locationsSpawnpoint[2]), Float.parseFloat(locationsSpawnpoint[3]), Float.parseFloat(locationsSpawnpoint[4])));
        }

        final String[] locationsNexus = locations.getString(".nexus." + name).split(";");
        nexus = new Nexus(new Location(gameWorld, Double.parseDouble(locationsNexus[0]), Double.parseDouble(locationsNexus[1]), Double.parseDouble(locationsNexus[2])));

    }

    public final String getName() {
        return name;
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
        return spawnpoints.get(new Random().nextInt(spawnpoints.size()));
    }

    public final Nexus getNexus() {
        return nexus;
    }

    public final void handleNexusAttack(String attackerName) {

        if (!alive) {
            Bukkit.getPlayer(attackerName).sendMessage(name + " is not alive");
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
            BreakTheNexus.getInstance().getGamemapManager().getGameWorld().playSound(nexus.getLocation(), Sound.EXPLODE, 1.0F, 0.1F);
            BreakTheNexus.getInstance().getServer().broadcastMessage(name + " has been destroyed!");

            spawnpoints.clear();
            spawnpoints.add(BreakTheNexus.getInstance().getGamemapManager().getLobbyWorld().getSpawnLocation());

            alive = false;

            return;

        }

        nexus.getLocation().getWorld().playEffect(nexus.getLocation(), Effect.LARGE_SMOKE, 0);
        nexus.getLocation().getWorld().playEffect(nexus.getLocation(), Effect.CLOUD, 0);
        BreakTheNexus.getInstance().getGamemapManager().getGameWorld().playSound(nexus.getLocation(), Sound.ANVIL_LAND, 1.0F, 0.1F);
        BreakTheNexus.getInstance().getServer().broadcastMessage(attackerName + " attacked " + name + " nexus! (" + nexus.getHealth() + ")");

    }

    public final String getInfo() {
        return name.toUpperCase() + ": " + playerNames.size() + " players @ " + nexus.getHealth();
    }

}
