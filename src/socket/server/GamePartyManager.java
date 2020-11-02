package socket.server;

import entity.PlayerImpl;
import entity.GameParty;
import exceptions.FullPartyException;
import exceptions.NoSuchPartyException;
import exceptions.PartyAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GamePartyManager {
    private List<GameParty> parties = new ArrayList<>();

    public void connectClientToParty(PlayerImpl playerImpl, String partyName)
            throws NoSuchPartyException, FullPartyException {

        GameParty party = parties.stream()
                .filter(currentParty -> currentParty.nameEquals(partyName))
                .findAny()
                .orElse(null);

        if(party == null)
            throw new NoSuchPartyException("Party " + partyName + " not found");

        party.connectClient(playerImpl);

        if(party.isFull())
            party.startMatch();
    }

    public void createConnectAndStart(String name, PlayerImpl player)
            throws PartyAlreadyExistsException, FullPartyException {

        GameParty party = createAndGetParty(name);
        party.connectClient(player);
        party.startMatch();
    }

    public void createPartyAndConnect(String name, PlayerImpl playerImpl)
            throws FullPartyException, PartyAlreadyExistsException {

        createAndGetParty(name).connectClient(playerImpl);
    }

    public GameParty createAndGetParty(String name) throws PartyAlreadyExistsException {
        if(includesParty(name))
            throw new PartyAlreadyExistsException("Party '" + name + "' aready exists");

        GameParty party = new GameParty(name);
        parties.add(party);

        return party;
    }

    public boolean includesParty(String partyName) {
        return parties.stream()
                .anyMatch(gameParty -> gameParty.nameEquals(partyName));
    }

    public GameParty getParty(String partyName) {
        return parties.stream()
                .filter(party -> party.nameEquals(partyName))
                .findAny()
                .orElse(null);
    }

    public List<String> getAllPartyNames() {
        return parties.stream()
                .map(GameParty::getPartyInfos)
                .collect(Collectors.toList());
    }
}
