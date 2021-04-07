package com.gmail._99tylerberinger.breakthenexus;

import com.gmail._99tylerberinger.breakthenexus.game.details.commands.CommandKit;
import com.gmail._99tylerberinger.breakthenexus.game.details.commands.CommandTeam;
import com.gmail._99tylerberinger.breakthenexus.game.details.listeners.ListenerPlayer;
import com.gmail._99tylerberinger.breakthenexus.game.details.listeners.ListenerWorld;
import com.gmail._99tylerberinger.breakthenexus.game.gamemap.Gamemap;
import com.gmail._99tylerberinger.breakthenexus.game.gamemap.GamemapManager;
import com.gmail._99tylerberinger.breakthenexus.game.kit.Kit;
import com.gmail._99tylerberinger.breakthenexus.game.kit.KitManager;
import com.gmail._99tylerberinger.breakthenexus.game.kit.kits.Civilian;
import com.gmail._99tylerberinger.breakthenexus.game.kit.kits.Miner;
import com.gmail._99tylerberinger.breakthenexus.game.kit.kits.Warrior;
import com.gmail._99tylerberinger.breakthenexus.game.mine.Mine;
import com.gmail._99tylerberinger.breakthenexus.game.mine.MineManager;
import com.gmail._99tylerberinger.breakthenexus.game.team.Team;
import com.gmail._99tylerberinger.breakthenexus.game.team.TeamManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class BreakTheNexus extends JavaPlugin {

    private static BreakTheNexus instance;

    private GamemapManager gamemapManager;
    private TeamManager teamManager;
    private MineManager mineManager;
    private KitManager kitManager;

    @Override
    public void onEnable() {

        instance = this;

        gamemapManager = new GamemapManager(
                new Gamemap("The Lobby"),
                new Gamemap("Roastal")
        );

        final ConfigurationSection locations = instance.getConfig().getConfigurationSection("locations");
        teamManager = new TeamManager(new Team[] {
                new Team("Red", locations),
                new Team("Blue", locations)
        });

        mineManager = new MineManager(new Mine[] {
                new Mine(Material.IRON_ORE, 15),
                new Mine(Material.GOLD_ORE, 30),
                new Mine(Material.COAL_ORE, Material.COAL, 5),
                new Mine(Material.MELON_BLOCK, Material.MELON, 12, 5),
                new Mine(Material.LOG, Material.WOOD, 4, 10)
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

        gamemapManager.unloadGamemaps();

    }

    public static final BreakTheNexus getInstance() {
        return instance;
    }

    public final GamemapManager getGamemapManager() {
        return gamemapManager;
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

        final Location disconnectLocation = gamemapManager.getDisconnectLocation(playerName);
        if (disconnectLocation != null) {

            return disconnectLocation;

        }

        for (Team team : teamManager.getTeams()) {
            if (team.hasPlayer(playerName)) {

                return team.getRandomSpawnpoint();

            }
        }

        return gamemapManager.getLobbyWorld().getSpawnLocation();

    }

}
