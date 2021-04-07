package com.gmail._99tylerberinger.breakthenexus.game.kit.kits;

import com.gmail._99tylerberinger.breakthenexus.BreakTheNexus;
import com.gmail._99tylerberinger.breakthenexus.game.kit.Kit;
import com.gmail._99tylerberinger.breakthenexus.game.team.Team;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Civilian extends Kit {

    public Civilian() {

        super(
                new Material[] {
                       Material.STONE_PICKAXE,
                       Material.WOOD_SWORD,
                       Material.STONE_AXE,
                       Material.STONE_SPADE
                },
                Material.CLAY_BRICK
        );

    }

    @Override
    public void doSpecial(Player player) {

        final Team team = BreakTheNexus.getInstance().getTeamManager().getTeamByPlayer(player.getName());

        for (Player potentialTeammate : player.getWorld().getPlayers()) {
            if (team.hasPlayer(potentialTeammate.getName())) {

                if (potentialTeammate.getLocation().distance(player.getLocation()) > 5) {
                    continue;
                }

                potentialTeammate.setFoodLevel(20);
                potentialTeammate.setSaturation(5.0f);

                potentialTeammate.getWorld().playEffect(potentialTeammate.getLocation().add(0, 2, 0), Effect.HEART, 0);
                potentialTeammate.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + " has restored your hunger!");

            }
        }

    }

}
