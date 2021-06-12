package com.gmail._99tylerberinger.breakthenexus.game.parts.team.items;

public class ProtectedArea {

    private final int minX;
    private final int maxX;

    private final int minZ;
    private final int maxZ;

    public ProtectedArea(int minX, int maxX, int minZ, int maxZ) {

        this.minX = minX;
        this.maxX = maxX;

        this.minZ = minZ;
        this.maxZ = maxZ;

    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxZ() {
        return maxZ;
    }

}
