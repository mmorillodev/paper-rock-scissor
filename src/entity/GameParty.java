package entity;

import exceptions.FullPartyException;

import java.util.ArrayList;
import java.util.List;

public class GameParty {

    public static final int MAX_PLAYERS = 2;

    private List<Client> players = new ArrayList<>(MAX_PLAYERS);
    private String partyName;
    private int currentPlayerIndex;

    public GameParty(String partyName) {
        this.partyName = partyName;
    }

    public void connectClient(Client client) throws FullPartyException {
        if(players.size() > 2)
            throw new FullPartyException("Party '" + partyName + "' is full!");

        players.add(client);
    }

    public void startMatch() {
        sendRespectiveInitialMessages();
        Client currPlayer = getRandomPlayer();

        currPlayer.sendMessage("You");
    }

    private void sendRespectiveInitialMessages() {

    }

    private Client getRandomPlayer() {
        this.currentPlayerIndex = (int)(Math.random() * 2);
        return this.players.get(currentPlayerIndex);
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
