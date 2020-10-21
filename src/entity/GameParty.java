package entity;

public class GameParty {
    private Client[] players = new Client[2];
    private String name;

    public GameParty() {

    }

    public boolean nameEquals(String name) {
        return this.name.equals(name);
    }
}
