package entity;

import interfaces.Player;
import utils.Console;

public class BotPlayer implements Player {
    @Override
    public int getPlay() {
        return (int) (Math.random() * 3 + 1);
    }

    @Override
    public void sendMessage(String message) {
        Console.println(message);
    }
}
