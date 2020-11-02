package entity;

public class BotPlayer extends Player {

    @Override
    public int getPlay() {
        return (int) (Math.random() * 3 + 1);
    }
}
