package com.gmail._99tylerberinger.breakthenexus.game.gamemap;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamemapManager {

    private final Gamemap[] gamemaps;

    private final String worldsFolder = "plugins/BreakTheNexus/worlds/";
    private final String worldsCloneFolder = "plugins/BreakTheNexus/worlds CLONE/"; // These will be played on and deleted

    private final Map<String, Location> disconnectLocations = new HashMap<>();
    private final List<ProtectedArea> protectedAreas = new ArrayList<>();

    public GamemapManager(Gamemap[] gamemaps, ConfigurationSection protectedAreasData) {

        this.gamemaps = gamemaps;
        loadGamemaps();

        for (String key : protectedAreasData.getKeys(false)) {

            final String value = protectedAreasData.getString(key); // "minX,maxX ; minZ,maxZ"

            final String[] xData = value.split(" ; ")[0].split(",");
            final String[] zData = value.split(" ; ")[1].split(",");
            protectedAreas.add(new ProtectedArea(Integer.parseInt(xData[0]), Integer.parseInt(xData[1]), Integer.parseInt(zData[0]), Integer.parseInt(zData[1])));

        }

    }

    private void loadGamemaps() {

        for (Gamemap gamemap : gamemaps) {

            final File worldFolder = new File(worldsFolder + gamemap.getWorldFolderName());
            final File worldCloneFolder = new File(worldsCloneFolder + gamemap.getWorldFolderName());

            try {

                // Creating the clone
                FileUtils.copyDirectory(worldFolder, worldCloneFolder);

                // Loading the clone
                Bukkit.getServer().createWorld(new WorldCreator(worldCloneFolder.getPath()));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public final void unloadGamemaps() {

        for (Gamemap gamemap : gamemaps) {

            Bukkit.getServer().unloadWorld(worldsCloneFolder + gamemap.getWorldFolderName(), false);

        }

    }

    public final World getLobbyWorld() {
        return Bukkit.getWorld(worldsCloneFolder + gamemaps[0].getWorldFolderName());
    }

    public final World getGameWorld() {
        return Bukkit.getWorld(worldsCloneFolder + gamemaps[1].getWorldFolderName());
    }

    public final Location getDisconnectLocation(String playerName) {

        final Location disconnectLocation = disconnectLocations.get(playerName);
        if (disconnectLocation == null) {

            return null;

        } else {

            disconnectLocations.remove(playerName);
            return disconnectLocation;

        }

    }

    public final void saveDisconnectLocation(String playerName, Location location) {
        disconnectLocations.put(playerName, location);
    }

    public final boolean isInProtectedArea(Location location) {

        final double x = location.getX();
        final double z = location.getZ();

        for (ProtectedArea protectedArea : protectedAreas) {

            if (x >= protectedArea.getMinX() && x <= protectedArea.getMaxX()) {
                if (z >= protectedArea.getMinZ() && z <= protectedArea.getMaxZ()) {

                    return true;

                }
            }

        }

        return false;

    }

}
