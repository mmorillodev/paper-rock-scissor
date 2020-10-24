package utils;

import entity.Client;
import entity.GameParty;
import exceptions.NoSuchPartyException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GamePartyManager {
    private List<GameParty> parties = new ArrayList<>();

    public void connectClientToParty(Client client, String partyName) throws NoSuchPartyException {
        GameParty party = parties.stream()
                .filter(currentParty -> currentParty.nameEquals(partyName))
                .findAny()
                .orElse(null);

        if(party == null)
            throw new NoSuchPartyException("Party " + partyName + " not found");

        party.connectClient(client);
    }

    public void createPartyAndConnect(String name, Client client) {
        GameParty party = new GameParty(name);
        parties.add(party);
        party.connectClient(client);
    }

    public void createParty(String name) {
        parties.add(new GameParty(name));
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
                .map(gameParty -> gameParty.getName())
                .collect(Collectors.toList());
    }
}
