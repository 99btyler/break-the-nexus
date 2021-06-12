package com.gmail._99tylerberinger.breakthenexus.game.parts.team;

import com.gmail._99tylerberinger.breakthenexus.BreakTheNexus;
import com.gmail._99tylerberinger.breakthenexus.game.parts.team.items.Nexus;
import com.gmail._99tylerberinger.breakthenexus.game.parts.team.items.ProtectedArea;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Team {

    private final String name;

    private final Nexus nexus;
    private final ProtectedArea protectedArea;

    private final List<Location> spawnpoints = new ArrayList<>();
    private final List<String> players = new ArrayList<>();
    private boolean alive;

    public Team(String name, ConfigurationSection teamData) {

        this.name = name;

        final String nexusDataValue = teamData.getString(".nexus");
        final String[] nexusCordData = nexusDataValue.split(",");
        nexus = new Nexus(new Location(BreakTheNexus.getInstance().getGamemapManager().getGameWorld(), Double.parseDouble(nexusCordData[0]), Double.parseDouble(nexusCordData[1]), Double.parseDouble(nexusCordData[2])));

        final String protectedAreaDataValue = teamData.getString(".protectedArea");
        final String[] protectedAreaXData = protectedAreaDataValue.split(" ; ")[0].split(",");
        final String[] protectedAreaZData = protectedAreaDataValue.split(" ; ")[1].split(",");
        protectedArea = new ProtectedArea(Integer.parseInt(protectedAreaXData[0]), Integer.parseInt(protectedAreaXData[1]), Integer.parseInt(protectedAreaZData[0]), Integer.parseInt(protectedAreaZData[1]));

        for (String spawnpointDataKey : teamData.getConfigurationSection(".spawnpoints").getKeys(false)) {
            final String spawnpointDataValue = teamData.getString(".spawnpoints." + spawnpointDataKey);
            final String[] spawnpointCordData = spawnpointDataValue.split(",");
            spawnpoints.add(new Location(BreakTheNexus.getInstance().getGamemapManager().getGameWorld(), Double.parseDouble(spawnpointCordData[0]), Double.parseDouble(spawnpointCordData[1]), Double.parseDouble(spawnpointCordData[2]), Float.parseFloat(spawnpointCordData[3]), Float.parseFloat(spawnpointCordData[4])));
        }

        alive = true;

    }

    public final String getName() {
        return name;
    }

    public final Nexus getNexus() {
        return nexus;
    }

    public final void handleNexusAttack(String attackerName) {

        if (!alive) {
            Bukkit.getPlayer(attackerName).sendMessage(name + " is not alive");
            return;
        }

        if (players.contains(attackerName)) {
            Bukkit.getPlayer(attackerName).sendMessage("Don't attack your own nexus");
            return;
        }

        nexus.reduceHealth(1);

        if (nexus.getHealth() >= 1) {

            BreakTheNexus.getInstance().getServer().broadcastMessage(attackerName + " attacked " + name + " nexus! (" + nexus.getHealth() + ")");
            nexus.getLocation().getWorld().playEffect(nexus.getLocation(), Effect.LARGE_SMOKE, 0);
            nexus.getLocation().getWorld().playEffect(nexus.getLocation(), Effect.CLOUD, 0);
            BreakTheNexus.getInstance().getGamemapManager().getGameWorld().playSound(nexus.getLocation(), Sound.ANVIL_LAND, 1.0F, 0.1F);

        } else {

            nexus.getLocation().getBlock().setType(Material.BEDROCK);

            BreakTheNexus.getInstance().getServer().broadcastMessage(name + " has been destroyed!");
            nexus.getLocation().getWorld().playEffect(nexus.getLocation(), Effect.EXPLOSION_HUGE, 0);
            BreakTheNexus.getInstance().getGamemapManager().getGameWorld().playSound(nexus.getLocation(), Sound.EXPLODE, 1.0F, 0.1F);

            spawnpoints.clear();
            spawnpoints.add(BreakTheNexus.getInstance().getGamemapManager().getLobbyWorld().getSpawnLocation());

            alive = false;

            BreakTheNexus.getInstance().getTeamManager().announceRemainingTeams();

            return;

        }

    }

    public final boolean protectedAreaContains(Location location) {

        final double x = location.getX();
        final double z = location.getZ();

        return x >= protectedArea.getMinX() && x <= protectedArea.getMaxX() && z >= protectedArea.getMinZ() && z <= protectedArea.getMaxZ();

    }

    public final Location getRandomSpawnpoint() {
        return spawnpoints.get(new Random().nextInt(spawnpoints.size()));
    }

    public final boolean hasPlayer(String playerName) {
        return players.contains(playerName);
    }

    public final void addPlayer(String playerName) {

        if (!players.contains(playerName)) {

            players.add(playerName);

            BreakTheNexus.getInstance().getKitManager().updateKitUsers(playerName, "Civilian");
            BreakTheNexus.getInstance().getKitManager().giveKitItemsTo(playerName);

            Bukkit.getPlayer(playerName).teleport(getRandomSpawnpoint());

        }

    }

    public final boolean isAlive() {
        return alive;
    }

    public final String getInfo() {
        return name.toUpperCase() + ": " + players.size() + " players @ " + nexus.getHealth();
    }

}
