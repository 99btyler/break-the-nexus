package breakthenexus.game.kit.kits;

import breakthenexus.BreakTheNexus;
import breakthenexus.game.kit.Kit;
import breakthenexus.game.team.Team;
import org.bukkit.ChatColor;
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
                potentialTeammate.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + " has restored your hunger!");

            }
        }

    }

}
