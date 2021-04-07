package com.gmail._99tylerberinger.breakthenexus.game.gamemap;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GamemapManager {

    // Contains the original copies of the gamemaps
    private final String worldsFolder = "plugins/BreakTheNexus/worlds/";

    // Will contain clones of the gamemaps, which will be played on and deleted
    private final String worldsCloneFolder = "plugins/BreakTheNexus/worlds CLONE/";

    private final Gamemap[] gamemaps;

    private final Map<String, Location> disconnectLocations = new HashMap<>();

    public GamemapManager(Gamemap lobby, Gamemap game) {
        gamemaps = new Gamemap[] {lobby, game};
        loadGamemaps();
    }

    private void loadGamemaps() {

        for (Gamemap gamemap : gamemaps) {

            // The original gamemap's folder
            final File worldFolder = new File(worldsFolder + gamemap.getWorldFolderName());

            // The clone gamemaps's folder (empty right now)
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

}
