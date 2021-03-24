package breakthenexus.game;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class Map {

    private final String worldFolderPath;

    public Map(String worldFolderName) {

        worldFolderPath = "worlds_BreakTheNexus/" + worldFolderName;

        new WorldCreator(worldFolderPath).createWorld();

    }

    public final World getWorld() {
        return Bukkit.getWorld(worldFolderPath);
    }

}
