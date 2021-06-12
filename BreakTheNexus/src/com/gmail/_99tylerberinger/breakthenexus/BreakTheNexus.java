package com.gmail._99tylerberinger.breakthenexus;

import com.gmail._99tylerberinger.breakthenexus.game.commands.CommandKit;
import com.gmail._99tylerberinger.breakthenexus.game.commands.CommandTeam;
import com.gmail._99tylerberinger.breakthenexus.game.listeners.ListenerPlayer;
import com.gmail._99tylerberinger.breakthenexus.game.listeners.ListenerWorld;
import com.gmail._99tylerberinger.breakthenexus.game.parts.gamemap.Gamemap;
import com.gmail._99tylerberinger.breakthenexus.game.parts.gamemap.GamemapManager;
import com.gmail._99tylerberinger.breakthenexus.game.parts.kit.Kit;
import com.gmail._99tylerberinger.breakthenexus.game.parts.kit.KitManager;
import com.gmail._99tylerberinger.breakthenexus.game.parts.kit.kits.Civilian;
import com.gmail._99tylerberinger.breakthenexus.game.parts.kit.kits.Miner;
import com.gmail._99tylerberinger.breakthenexus.game.parts.kit.kits.Warrior;
import com.gmail._99tylerberinger.breakthenexus.game.parts.mine.Mine;
import com.gmail._99tylerberinger.breakthenexus.game.parts.mine.MineManager;
import com.gmail._99tylerberinger.breakthenexus.game.parts.team.Team;
import com.gmail._99tylerberinger.breakthenexus.game.parts.team.TeamManager;
import org.bukkit.Location;
import org.bukkit.Material;
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
        instance.saveDefaultConfig();

        gamemapManager = new GamemapManager(
                new Gamemap[] {
                        new Gamemap("The Lobby"),
                        new Gamemap("Roastal")
                }
        );

        kitManager = new KitManager(
                new Kit[] {
                        new Civilian(),
                        new Miner(),
                        new Warrior()
                }
        );

        mineManager = new MineManager(
                new Mine[] {
                        new Mine(Material.IRON_ORE, 15),
                        new Mine(Material.GOLD_ORE, 30),
                        new Mine(Material.COAL_ORE, Material.COAL, 5),
                        new Mine(Material.MELON_BLOCK, Material.MELON, 12, 5),
                        new Mine(Material.LOG, Material.WOOD, 4, 10)
                }
        );

        teamManager = new TeamManager(
                new Team[] {
                        new Team("Red", instance.getConfig().getConfigurationSection("Red.")),
                        new Team("Blue", instance.getConfig().getConfigurationSection("Blue."))
                }
        );

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
