package entity;

import exceptions.FullPartyException;
import resources.JokenpoOpts;

public class GameParty {

    private final String partyName;

    private PlayerImpl player1;
    private Player player2;
    private JokenpoOpts player1Play;
    private JokenpoOpts player2Play;

    public GameParty(String partyName) {
        this.partyName = partyName;
    }

    public void connectClient(PlayerImpl playerImpl) throws FullPartyException {
        if(player1 != null && player2 != null)
            throw new FullPartyException("Party '" + partyName + "' is full!");

        if(player1 == null) {
            player1 = playerImpl;
        }
        else {
            player2 = playerImpl;
            player1.out.sendMessage("Player 2 has entered the party!");
        }
    }


    public void startMatch() {
        if(player2 == null) {
            player2 = new BotPlayer();
        }

        do {
            player1Play = null;
            player2Play = null;

            listenForPlay();
            notifyPlayers();

            switch (comparePlays(player1Play, player2Play)) {
                case 0:
                    handleDraw();
                    break;
                case 1:
                    handleP1Win();
                    break;
                case -1:
                    handleP2Win();
                    break;
            }
        } while(true);
    }

    private void listenForPlay() {
        sendMessageTo(player2, "Waiting for Player 1's play");
        while(player1Play == null) {
            player1Play = JokenpoOpts.fromInt(player1.getPlay());
        }

        sendMessageTo(player2, "Waiting for Player 2's play");

        while(player2Play == null) {
            player2Play = JokenpoOpts.fromInt(player2.getPlay());
        }
    }

    private void notifyPlayers() {
        sendMessageTo(player1, "Player 2 played " + player2Play.getString());
        sendMessageTo(player2, "Player 1 played " + player1Play.getString());
    }

    private int comparePlays(JokenpoOpts player1Play, JokenpoOpts player2Play) {
        if (player1Play == player2Play) {
            return 0;
        }
        else if(player1Play == JokenpoOpts.PAPER && player2Play == JokenpoOpts.ROCK ||
                player1Play == JokenpoOpts.ROCK && player2Play == JokenpoOpts.SCISSOR ||
                player1Play == JokenpoOpts.SCISSOR && player2Play == JokenpoOpts.PAPER) {

            return 1;
        }
        else {
            return -1;
        }
    }

    private void handleDraw() {
        player1.notifyDraw();
        player2.notifyDraw();
    }

    private void handleP1Win() {
        player1.notifyWin();
        player2.notifyLoss();
    }

    private void handleP2Win() {
        player1.notifyLoss();
        player2.notifyWin();
    }

    private void sendMessageTo(Player p, String message) {
        if(p instanceof PlayerImpl) {
            ((PlayerImpl) p).out.sendMessage(message);
        }
    }

    public boolean nameEquals(String name) {
        return this.partyName.equals(name);
    }

    public String getPartyInfos() {
        int playersQtt = 0;

        if(player1 != null)
            playersQtt++;

        if (player2 != null)
            playersQtt++;

        return this.partyName + " - [" + playersQtt + "/2]";
    }

    public boolean isFull() {
        return player1 != null & player2 != null;
    }
}