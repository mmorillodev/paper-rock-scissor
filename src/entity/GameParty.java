package entity;

import exceptions.FullPartyException;
import interfaces.Player;
import utils.Console;

import static utils.StaticResources.*;

public class GameParty {

    private volatile Player player1;
    private volatile Player player2;
    private final String partyName;

    public GameParty(String partyName) {
        this.partyName = partyName;
    }

    public void connectClient(PlayerImpl playerImpl) throws FullPartyException {
        if(player1 != null & player2 != null)
            throw new FullPartyException("Party '" + partyName + "' is full!");

        if(player1 == null) {
            player1 = playerImpl;
        }
        else {
            player2 = playerImpl;
        }
    }

    public void startMatch() {
        if(player2 == null) {
            player2 = new BotPlayer();
        }

        do {
            int player1Play = 0;
            int player2Play = 0;

            while(player1Play != OPT_ROCK && player1Play != OPT_PAPER && player1Play != OPT_SCISSOR) {
                player1Play = player1.getPlay();
                Console.println(player1Play);
            }

            while(player2Play != OPT_ROCK && player2Play != OPT_PAPER && player2Play != OPT_SCISSOR) {
                player2Play = player2.getPlay();
            }

            if (player1Play == OPT_ROCK && player2Play == OPT_ROCK) {
                //draw
                handleDraw();
            } else if (player1Play == OPT_ROCK && player2Play == OPT_PAPER) {
                //player 2
                handleP2Win();
            } else if (player1Play == OPT_ROCK) {
                //player 1
                handleP1Win();
            } else if (player1Play == OPT_PAPER && player2Play == OPT_ROCK) {
                //player 1
                handleP1Win();
            } else if (player1Play == OPT_PAPER && player2Play == OPT_PAPER) {
                //draw
                handleDraw();
            } else if (player1Play == OPT_PAPER && player2Play == OPT_SCISSOR) {
                //player 2
                handleP2Win();
            } else if (player1Play == OPT_SCISSOR && player2Play == OPT_ROCK) {
                //player 2
                handleP2Win();
            } else if (player1Play == OPT_SCISSOR && player2Play == OPT_PAPER) {
                //player 1
                handleP1Win();
            } else if (player1Play == OPT_SCISSOR && player2Play == OPT_SCISSOR) {
                //draw
                handleDraw();
            }
        } while(true);

    }

    private void handleDraw() {
        player1.sendMessage("Draw!");
        player2.sendMessage("Draw!");
    }

    private void handleP1Win() {
        player1.sendMessage("You win!");
        player2.sendMessage("Player 1 wins!");
    }

    private void handleP2Win() {
        player1.sendMessage("Player 2 wins!");
        player2.sendMessage("You win!");
    }

    public boolean nameEquals(String name) {
        return this.partyName.equals(name);
    }

    public String getPartyName() {
        return this.partyName;
    }

    public boolean readyToStart() {
        return player1 != null & player2 != null;
    }
}