package entity;

public class GameParty {
    private Client[] players = new Client[2];
    private String name;

    public GameParty(String name) {
        this.name = name;
    }

    public void connectClient(Client client) {
        players[players.length - 1] = client;
    }

    public boolean nameEquals(String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return this.name;
    }
}
