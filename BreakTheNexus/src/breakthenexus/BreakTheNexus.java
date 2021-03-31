package breakthenexus;

import breakthenexus.game.Map;
import breakthenexus.game.Mine;
import breakthenexus.game.Team;
import breakthenexus.game.details.commands.CommandKit;
import breakthenexus.game.details.commands.CommandTeam;
import breakthenexus.game.details.listeners.ListenerPlayer;
import breakthenexus.game.details.listeners.ListenerWorld;
import breakthenexus.game.kit.Kit;
import breakthenexus.game.kit.kits.Civilian;
import breakthenexus.game.kit.kits.Miner;
import breakthenexus.game.kit.kits.Warrior;
import breakthenexus.managers.KitManager;
import breakthenexus.managers.MineManager;
import breakthenexus.managers.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BreakTheNexus extends JavaPlugin {

    private Map mapLobby;

    private Map mapGame;

    private TeamManager teamManager;

    private MineManager mineManager;

    private KitManager kitManager;

    private static BreakTheNexus instance;

    @Override
    public void onEnable() {

        instance = this;
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus being enabled...");

        mapLobby = new Map("The Lobby");
        mapLobby.loadWorld();
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus mapLobby loaded!");

        mapGame = new Map("Roastal");
        mapGame.loadWorld();
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus mapGame loaded!");

        teamManager = new TeamManager(new Team[] {
                new Team("Red"),
                new Team("Blue")
        });
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus teams loaded!");

        mineManager = new MineManager(new Mine[] {
                new Mine(Material.IRON_ORE, 15),
                new Mine(Material.GOLD_ORE, 30),
                new Mine(Material.COAL_ORE, Material.COAL, 5),
                new Mine(Material.MELON_BLOCK, Material.MELON, 5),
                new Mine(Material.LOG, Material.WOOD, 10)
        });
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus mines loaded!");

        kitManager = new KitManager(new Kit[] {
                new Civilian(),
                new Miner(),
                new Warrior()
        });
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus kits loaded!");

        getCommand("kit").setExecutor(new CommandKit());
        getCommand("team").setExecutor(new CommandTeam());
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus commands registered!");

        getServer().getPluginManager().registerEvents(new ListenerPlayer(), this);
        getServer().getPluginManager().registerEvents(new ListenerWorld(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus listeners registered!");

        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus enabled!");

    }

    @Override
    public void onDisable() {

        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus being disabled...");

        for (Player player : mapGame.getWorld().getPlayers()) {
            player.getInventory().clear();
            player.teleport(mapLobby.getWorld().getSpawnLocation());
            player.setHealth(20.0);
            player.setFoodLevel(20);
        }
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus mapGame players moved to mapLobby!");

        mapGame.unloadWorld();
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus mapGame unloaded");

        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "BreakTheNexus disabled");

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

    public final TeamManager getTeamManager() {
        return teamManager;
    }

    public final MineManager getMineManager() {
        return mineManager;
    }

    public final KitManager getKitManager() {
        return kitManager;
    }

    public final Location getPlaceToSpawn(String playerName) {
        for (Team team : teamManager.getTeams()) {
            if (team.hasPlayer(playerName)) {
                return team.getRandomSpawnpoint();
            }
        }
        return mapLobby.getWorld().getSpawnLocation();
    }

}
