package breakthenexus.game;

import breakthenexus.BreakTheNexus;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private final String name;

    private final List<String> playerNames = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public final void join(String playerName) {
        if (!playerNames.contains(playerName)) {
            playerNames.add(playerName);
            Bukkit.getPlayer(playerName).teleport(BreakTheNexus.getInstance().getPlaceToSpawn(playerName));
        }
    }

    public final boolean has(String playerName) {
        return playerNames.contains(playerName);
    }

    public final String getInfo() {
        return name.toUpperCase() + ": " + playerNames.size() + " players";
    }

}
