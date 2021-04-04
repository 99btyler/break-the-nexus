package breakthenexus.game.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class Kit {

    private final String kitName = getClass().getSimpleName();

    private final Material[] tools;
    private final Material specialItem;

    public Kit(Material[] tools, Material specialItem) {
        this.tools = tools;
        this.specialItem = specialItem;
    }

    public final String getKitName() {
        return kitName;
    }

    public final Material[] getTools() {
        return tools;
    }

    public final Material getSpecialItem() {
        return specialItem;
    }

    public abstract void doSpecial(Player player);

}
