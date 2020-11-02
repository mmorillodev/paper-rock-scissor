package entity;

import java.io.IOException;

public abstract class Player {

    private int wins;
    private int loses;
    private int draws;

    public abstract int getPlay() throws IOException;

    public void notifyLoss() {
        loses++;
    }

    public void notifyWin() {
        wins++;
    }

    public void notifyDraw() {
        draws++;
    }

    public int getLosses() {
        return loses;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }
}
