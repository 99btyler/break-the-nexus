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

        mapLobby = new Map("The Lobby");
        mapLobby.loadWorld();

        mapGame = new Map("Roastal");
        mapGame.loadWorld();

        teamManager = new TeamManager(new Team[] {
                new Team("Red"),
                new Team("Blue")
        });

        mineManager = new MineManager(new Mine[] {
                new Mine(Material.IRON_ORE, 15),
                new Mine(Material.GOLD_ORE, 30),
                new Mine(Material.COAL_ORE, Material.COAL, 5),
                new Mine(Material.MELON_BLOCK, Material.MELON, 5),
                new Mine(Material.LOG, Material.WOOD, 10)
        });

        kitManager = new KitManager(new Kit[] {
                new Civilian(),
                new Miner(),
                new Warrior()
        });

        getCommand("kit").setExecutor(new CommandKit());
        getCommand("team").setExecutor(new CommandTeam());

        getServer().getPluginManager().registerEvents(new ListenerPlayer(), this);
        getServer().getPluginManager().registerEvents(new ListenerWorld(), this);

    }

    @Override
    public void onDisable() {

        for (Player player : mapGame.getWorld().getPlayers()) {
            mapLobby.bringPlayer(player.getName());
        }

        mapGame.unloadWorld();

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
                return team.getRandomSpawnpoint(); // In game world
            }
        }
        return mapLobby.getWorld().getSpawnLocation(); // In lobby world
    }

}
