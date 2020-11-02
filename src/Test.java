import exceptions.PartyAlreadyExistsException;
import socket.server.GamePartyManager;
import utils.Console;

public class Test {
    public static void main(String[] args) {

        GamePartyManager manager = new GamePartyManager();
        try {
            manager.createAndGetParty("teste");
            manager.createAndGetParty("teste2");
            manager.createAndGetParty("test3");
            manager.createAndGetParty("teste4");

            System.out.println(manager.getAllPartyNames());

            System.out.println(manager.getAllPartyNames().stream().reduce((acm, current) -> acm + "\n" + current).orElse(null));

            System.out.println(manager.includesParty("teste 4"));
        } catch (PartyAlreadyExistsException e) {
            e.printStackTrace();
        }
    }
}
