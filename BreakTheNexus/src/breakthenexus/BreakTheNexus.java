package breakthenexus;

import breakthenexus.game.Map;
import breakthenexus.game.Mine;
import breakthenexus.game.Team;
import breakthenexus.game.details.commands.CommandKit;
import breakthenexus.game.details.commands.CommandTeam;
import breakthenexus.game.details.listeners.ListenerPlayer;
import breakthenexus.game.details.listeners.ListenerWorld;
import breakthenexus.game.kit.KitManager;
import breakthenexus.game.kit.kits.Civilian;
import breakthenexus.game.kit.kits.Miner;
import breakthenexus.game.kit.kits.Warrior;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class BreakTheNexus extends JavaPlugin {

    private Map mapLobby;
    private Map mapGame;

    private List<Team> teams; // TODO: make TeamManager

    private List<Mine> mines; // TODO: make MineManager

    private KitManager kitManager;

    private static BreakTheNexus instance;

    @Override
    public void onEnable() {

        instance = this;
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus being enabled...");

        mapLobby = new Map("The Lobby");
        mapGame = new Map("Roastal");
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus maps loaded!");

        teams = new ArrayList<>();
        teams.add(new Team("Red"));
        teams.add(new Team("Blue"));
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus teams loaded!");

        mines = new ArrayList<>();
        mines.add(new Mine(Material.IRON_ORE, 15));
        mines.add(new Mine(Material.GOLD_ORE, 30));
        mines.add(new Mine(Material.COAL_ORE, Material.COAL, 5));
        mines.add(new Mine(Material.MELON_BLOCK, Material.MELON, 5));
        mines.add(new Mine(Material.LOG, Material.WOOD, 10));
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus mines loaded!");

        kitManager = new KitManager();
        kitManager.addKit(new Civilian());
        kitManager.addKit(new Miner());
        kitManager.addKit(new Warrior());
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus kits loaded!");

        getCommand("kit").setExecutor(new CommandKit());
        getCommand("team").setExecutor(new CommandTeam());
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus commands registered!");

        getServer().getPluginManager().registerEvents(new ListenerPlayer(), this);
        getServer().getPluginManager().registerEvents(new ListenerWorld(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus listeners registered!");

        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus enabled!");

    }

    public static final BreakTheNexus getInstance() {
        return instance;
    }

    public final Map getMapLobby() {
        return mapLobby;
    }

    public final Map getMapGame() {
        return mapGame;
    }

    public final Team[] getTeams() {
        return teams.toArray(new Team[0]);
    }

    public final Mine[] getMines() {
        return mines.toArray(new Mine[0]);
    }

    public final KitManager getKitManager() {
        return kitManager;
    }

    public final Location getPlaceToSpawn(String playerName) {
        for (Team team : teams) {
            if (team.hasPlayer(playerName)) {
                return team.getRandomSpawnpoint();
            }
        }
        return mapLobby.getWorld().getSpawnLocation();
    }

}
