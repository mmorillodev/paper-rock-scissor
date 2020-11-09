package socket.server;

import entity.PlayerImpl;
import entity.GameParty;
import exceptions.FullPartyException;
import exceptions.NoSuchPartyException;
import exceptions.PartyAlreadyExistsException;
import interfaces.listeners.OnPlayerDisconnectedListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GamePartyManager implements OnPlayerDisconnectedListener {

    private final List<GameParty> parties = new ArrayList<>();

    public void connectClientToParty(PlayerImpl playerImpl, String partyName)
            throws NoSuchPartyException, FullPartyException {

        GameParty party = getParty(partyName);

        if(party == null)
            throw new NoSuchPartyException("Party '" + partyName + "' not found");

        if(party.isFull())
            throw new FullPartyException("Party '" + partyName + "' is full!");

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
            throw new PartyAlreadyExistsException("Party '" + name + "' already exists");

        GameParty party = new GameParty(name);
        party.setOnPlayerDisconnectedListener(this);
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

    @Override
    public void onPlayerDisconnect(GameParty party) {
        if(party.isEmpty()) {
            this.parties.remove(party);
        }
        else {
            askPlayerNextSteps(party);
        }
    }

    private void askPlayerNextSteps(GameParty party) {
        PlayerImpl player = (PlayerImpl) party.getAllPlayers().get(0);

        String opt = "";
        do {
            try {
                opt = player.sendQuestion("Choose an option:\n[1] Continue game with an bot\n[2] Wait another player\n[3] Leave match\n>");
                switch (opt) {
                    case "1":
                        party.startMatch();
                        break;
                    case "2":
                        break;
                    case "3":
                        party.destroy();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!(opt.equals("1") || opt.equals("2") || opt.equals("3")));
    }
}
