package breakthenexus.managers;

import breakthenexus.game.Mine;

public class MineManager {

    public final Mine[] mines;

    public MineManager(Mine[] mines) {
        this.mines = mines;
    }

    public final Mine[] getMines() {
        return mines;
    }

}
