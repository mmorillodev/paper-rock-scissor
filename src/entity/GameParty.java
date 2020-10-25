package entity;

import exceptions.FullPartyException;
import static utils.StaticResources.*;

import java.util.ArrayList;
import java.util.List;

public class GameParty {

    public static final int MAX_PLAYERS = 2;

    private List<Client> players = new ArrayList<>(MAX_PLAYERS);
    private volatile int player1Play;
    private volatile int player2Play;
    private volatile boolean roundResolved;
    private String partyName;

    public GameParty(String partyName) {
        this.partyName = partyName;
    }

    public void connectClient(Client client) throws FullPartyException {
        if(players.size() >= MAX_PLAYERS)
            throw new FullPartyException("Party '" + partyName + "' is full!");

        players.add(client);
    }

    public void startMatch() {
        new Thread(() -> {
            do {
                player1Play = Integer.parseInt(players.get(0).sendQuestion(OPTS_PLAYS));
                while (!roundResolved || player1Play != OPT_ROCK || player1Play != OPT_PAPER || player1Play != OPT_SCIZOR)
                    ;
            } while (true);
        }).start();

        new Thread(() -> {
            do {
                player2Play = Integer.parseInt(players.get(1).sendQuestion(OPTS_PLAYS));
                while (!roundResolved  || player2Play != OPT_ROCK || player2Play != OPT_PAPER || player2Play != OPT_SCIZOR)
                    ;
            } while (true);
        }).start();

        new Thread(() -> {
            while(player1Play == 0 || player2Play == 0)
                ;
            if(player1Play == OPT_ROCK && player2Play == OPT_ROCK) {
                //draw
            }
            else if(player1Play == OPT_ROCK && player2Play == OPT_PAPER) {
                //player 2
            }
            else if(player1Play == OPT_ROCK && player2Play == OPT_SCIZOR) {
                //player 1
            }
            else if(player1Play == OPT_PAPER && player2Play == OPT_ROCK) {
                //player 1
            }
            else if(player1Play == OPT_PAPER && player2Play == OPT_PAPER) {
                //draw
            }
            else if(player1Play == OPT_PAPER && player2Play == OPT_SCIZOR) {
                //player 2
            }
            else if(player1Play == OPT_SCIZOR && player2Play == OPT_ROCK) {
                //player 2
            }
            else if (player1Play == OPT_SCIZOR && player2Play == OPT_PAPER) {
                //player 1
            }
            else if(player1Play == OPT_SCIZOR && player2Play == OPT_SCIZOR) {
                //draw
            }
            else {
                roundResolved = false;
            }
            roundResolved = true;
        }).start();
    }

    public boolean nameEquals(String name) {
        return this.partyName.equals(name);
    }

    public String getPartyName() {
        return this.partyName;
    }

    public int getPlayersQtt() {
        return this.players.size();
    }
}