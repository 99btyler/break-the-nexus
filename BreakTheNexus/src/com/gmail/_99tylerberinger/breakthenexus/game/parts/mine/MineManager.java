package com.gmail._99tylerberinger.breakthenexus.game.parts.mine;

public class MineManager {

    public final Mine[] mines;

    public MineManager(Mine[] mines) {
        this.mines = mines;
    }

    public final Mine[] getMines() {
        return mines;
    }

}
