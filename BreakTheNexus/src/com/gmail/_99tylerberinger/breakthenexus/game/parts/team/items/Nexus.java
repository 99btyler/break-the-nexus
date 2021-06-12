package com.gmail._99tylerberinger.breakthenexus.game.parts.team.items;

import org.bukkit.Location;
import org.bukkit.Material;

public class Nexus {

    private final Location location;

    private int health;

    public Nexus(Location location) {

        this.location = location;
        this.location.getBlock().setType(Material.ENDER_STONE);

        health = 10;

    }

    public final Location getLocation() {
        return location;
    }

    public final int getHealth() {
        return health;
    }

    public final void reduceHealth(int amount) {
        health -= amount;
    }

}
