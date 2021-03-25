package breakthenexus.game;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class Map {

    private final String worldFolderPath;

    public Map(String worldFolderName) {

        worldFolderPath = "worlds_BreakTheNexus/" + worldFolderName;
        new WorldCreator(worldFolderPath).createWorld(); // loads the world so players can be sent to it

    }

    public final World getWorld() {
        return Bukkit.getWorld(worldFolderPath); // the loaded world
    }

}
