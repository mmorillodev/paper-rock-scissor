package entity;

import exceptions.FullPartyException;
import interfaces.listeners.OnPlayerDisconnectedListener;
import resources.JokenpoOpts;

import java.io.IOException;

public class GameParty {

    private final String partyName;

    private PlayerImpl player1;
    private Player player2;
    private JokenpoOpts player1Play;
    private JokenpoOpts player2Play;
    private OnPlayerDisconnectedListener onPlayerDisconnectedListener;

    public GameParty(String partyName) {
        this.partyName = partyName;
    }

    public void connectClient(PlayerImpl playerImpl) throws FullPartyException {
        if(player1 != null && player2 != null)
            throw new FullPartyException("Party '" + partyName + "' is full!");

        if(player1 == null) {
            player1 = playerImpl;
            sendMessageTo(player1, "Waiting another player");
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

            notifyPlayersOpponentPlay();

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
            showScoreboard();
        } while(player1 != null && player2 != null);
    }

    private void showScoreboard() {
        String scoreboardTemplate = "=================================\n" +
                                    "Player 1" +
                                    "\n  Wins - %d" +
                                    "\n  Losses - %d" +
                                "\n\nPlayer 2" +
                                    "\n  Wins - %d" +
                                    "\n  Losses - %d" +
                                "\n\nDraws - %d\n" +
                                    "=================================";

        Object[] tagsReplacement = {
                player1.getWins(),
                player1.getLosses(),
                player2.getWins(),
                player2.getLosses(),
                player1.getDraws()
        };

        notifyAllPlayers(String.format(scoreboardTemplate, tagsReplacement));
    }

    private void listenForPlay() {

        sendMessageTo(player2, "Waiting for Player 1's play");
        while(player1Play == null && player1 != null) {
            try {
                player1Play = JokenpoOpts.fromInt(player1.getPlay());
            } catch (IOException e) {
                sendMessageTo(player2, "Player 1 has quited!");
            }
        }

        sendMessageTo(player2, "Waiting for Player 2's play");
        while(player2Play == null && player2 != null) {
            try {
                player2Play = JokenpoOpts.fromInt(player2.getPlay());
            } catch (IOException e) {
                player2 = null;
                notifyClientDisconnected();
            }
        }
    }

    private void notifyClientDisconnected() {
        if(this.onPlayerDisconnectedListener != null) {
            this.onPlayerDisconnectedListener.onPlayerDisconnect(this);
        }
    }

    private void notifyPlayersOpponentPlay() {
        sendMessageTo(player1, "Player 2 used " + player2Play.getString());
        sendMessageTo(player2, "Player 1 used " + player1Play.getString());
    }

    private void notifyAllPlayers(String message) {
        sendMessageTo(player1, message);
        sendMessageTo(player2, message);
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
        if(p == null) return;

        if(p instanceof PlayerImpl) {
            ((PlayerImpl) p).out.sendMessage(message);
        }
    }

    public void setOnPlayerDisconnectedListener(OnPlayerDisconnectedListener onPlayerDisconnectedListener) {
        this.onPlayerDisconnectedListener = onPlayerDisconnectedListener;
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

    public boolean isEmpty() {
        return player1 == null && player2 == null;
    }
}