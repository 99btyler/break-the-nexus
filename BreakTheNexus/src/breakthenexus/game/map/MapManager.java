package breakthenexus.game.map;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;

public class MapManager {

    private final String worldsFolder; // Contains the original copies of the maps
    private final String worldsCloneFolder; // Will contain clones of the maps, which will be played on and deleted

    private final Map[] maps;

    public MapManager(Map mapLobby, Map mapGame) {

        worldsFolder = "plugins/BreakTheNexus/worlds/";
        worldsCloneFolder = "plugins/BreakTheNexus/worlds CLONE/";

        maps = new Map[] {mapLobby, mapGame};

        loadMaps();

    }

    private void loadMaps() {

        for (Map map : maps) {

            // The original map's folder
            final File worldFolder = new File(worldsFolder + map.getWorldFolderName());

            // The clone map's folder (empty right now)
            final File worldCloneFolder = new File(worldsCloneFolder + map.getWorldFolderName());
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

    public final void unloadMaps() {

        for (Map map : maps) {

            Bukkit.getServer().unloadWorld(worldsCloneFolder + map.getWorldFolderName(), false);

        }

    }

    public final World getLobbyWorld() {
        return Bukkit.getWorld(worldsCloneFolder + maps[0].getWorldFolderName());
    }

    public final World getGameWorld() {
        return Bukkit.getWorld(worldsCloneFolder + maps[1].getWorldFolderName());
    }

}
