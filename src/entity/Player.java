package entity;

public abstract class Player {

    private int wins;
    private int loses;
    private int draws;

    public abstract int getPlay();

    public void notifyLoss() {
        loses++;
    }

    public void notifyWin() {
        wins++;
    }

    public void notifyDraw() {
        draws++;
    }

    public int getLoses() {
        return loses;
    }

    public int getWins() {
        return wins;
    }

    public int getDraws() {
        return draws;
    }
}