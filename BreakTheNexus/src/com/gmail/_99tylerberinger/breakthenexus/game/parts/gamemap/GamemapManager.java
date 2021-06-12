package com.gmail._99tylerberinger.breakthenexus.game.parts.gamemap;

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

    private final String worldsFolder = "plugins/BreakTheNexus/worlds/";
    private final String worldsCloneFolder = "plugins/BreakTheNexus/worlds CLONE/"; // These will be played on and deleted

    private final Gamemap[] gamemaps; // Assumes 0 will always be the lobby, 1 will always be the game
    private final Map<String, Location> disconnectLocations = new HashMap<>();

    public GamemapManager(Gamemap[] gamemaps) {

        this.gamemaps = gamemaps;

        loadGamemaps();

    }

    private void loadGamemaps() {

        for (Gamemap gamemap : gamemaps) {

            final File worldFolder = new File(worldsFolder + gamemap.getWorldFolderName());
            final File worldCloneFolder = new File(worldsCloneFolder + gamemap.getWorldFolderName());

            try {

                // Create the clone
                FileUtils.copyDirectory(worldFolder, worldCloneFolder);

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Load the clone
            Bukkit.getServer().createWorld(new WorldCreator(worldCloneFolder.getPath()));

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
