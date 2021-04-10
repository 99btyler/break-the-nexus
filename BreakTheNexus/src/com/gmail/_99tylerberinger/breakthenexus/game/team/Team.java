package com.gmail._99tylerberinger.breakthenexus.game.team;

import com.gmail._99tylerberinger.breakthenexus.BreakTheNexus;
import com.gmail._99tylerberinger.breakthenexus.game.gamemap.ProtectedArea;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Team {

    private final String name;

    private final List<String> players = new ArrayList<>();
    private final List<Location> spawnpoints = new ArrayList<>();
    private final Nexus nexus;
    private final ProtectedArea protectedArea;

    private boolean alive;

    public Team(String name, ConfigurationSection teamData) {

        this.name = name;

        for (String spawnpointsDataKey : teamData.getConfigurationSection(".spawnpoints").getKeys(false)) {
            final String spawnpointsDataValue = teamData.getString(".spawnpoints." + spawnpointsDataKey);
            final String[] cordData = spawnpointsDataValue.split(",");
            spawnpoints.add(new Location(BreakTheNexus.getInstance().getGamemapManager().getGameWorld(), Double.parseDouble(cordData[0]), Double.parseDouble(cordData[1]), Double.parseDouble(cordData[2]), Float.parseFloat(cordData[3]), Float.parseFloat(cordData[4])));
        }

        final String nexusDataValue = teamData.getString(".nexus");
        final String[] cordData = nexusDataValue.split(",");
        nexus = new Nexus(new Location(BreakTheNexus.getInstance().getGamemapManager().getGameWorld(), Double.parseDouble(cordData[0]), Double.parseDouble(cordData[1]), Double.parseDouble(cordData[2])));

        final String protectedAreaDataValue = teamData.getString(".protectedArea");
        final String[] xData = protectedAreaDataValue.split(" ; ")[0].split(",");
        final String[] zData = protectedAreaDataValue.split(" ; ")[1].split(",");
        protectedArea = new ProtectedArea(Integer.parseInt(xData[0]), Integer.parseInt(xData[1]), Integer.parseInt(zData[0]), Integer.parseInt(zData[1]));

        alive = true;

    }

    public final String getName() {
        return name;
    }

    public final boolean hasPlayer(String playerName) {
        return players.contains(playerName);
    }

    public final void addPlayer(String playerName) {

        if (!players.contains(playerName)) {

            players.add(playerName);
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

        if (players.contains(attackerName)) {
            Bukkit.getPlayer(attackerName).sendMessage("Don't attack your own nexus");
            return;
        }

        nexus.reduceHealth(1);

        if (nexus.getHealth() < 1) {

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

        BreakTheNexus.getInstance().getServer().broadcastMessage(attackerName + " attacked " + name + " nexus! (" + nexus.getHealth() + ")");
        nexus.getLocation().getWorld().playEffect(nexus.getLocation(), Effect.LARGE_SMOKE, 0);
        nexus.getLocation().getWorld().playEffect(nexus.getLocation(), Effect.CLOUD, 0);
        BreakTheNexus.getInstance().getGamemapManager().getGameWorld().playSound(nexus.getLocation(), Sound.ANVIL_LAND, 1.0F, 0.1F);

    }

    public final boolean protectedAreaContains(Location location) {

        final double x = location.getX();
        final double z = location.getZ();

        if (x >= protectedArea.getMinX() && x <= protectedArea.getMaxX()) {
            if (z >= protectedArea.getMinZ() && z <= protectedArea.getMaxZ()) {

                return true;

            }
        }

        return false;

    }

    public final boolean isAlive() {
        return alive;
    }

    public final String getInfo() {
        return name.toUpperCase() + ": " + players.size() + " players @ " + nexus.getHealth();
    }

}
