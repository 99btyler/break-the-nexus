package breakthenexus.game;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class Map {

    private final String worldFolderPath;

    public Map(String worldFolderName) {
        worldFolderPath = "worlds_BreakTheNexus/" + worldFolderName;
    }

    public final void loadWorld() {
        Bukkit.getServer().createWorld(new WorldCreator(worldFolderPath));
    }

    public final void unloadWorld() {
        Bukkit.getServer().unloadWorld(worldFolderPath, false);
    }

    public final World getWorld() {
        return Bukkit.getWorld(worldFolderPath);
    }

}
