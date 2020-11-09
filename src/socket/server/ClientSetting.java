package socket.server;

import entity.PlayerImpl;
import resources.Environment;
import exceptions.FullPartyException;
import exceptions.NoSuchPartyException;
import exceptions.PartyAlreadyExistsException;

import java.io.IOException;
import java.util.List;

public class ClientSetting {

    private final PlayerImpl playerImpl;
    private final GamePartyManager manager;
    private boolean finishedSetup;

    public ClientSetting(PlayerImpl playerImpl, GamePartyManager manager) {
        this.playerImpl = playerImpl;
        this.manager = manager;
    }

    public void startInitialConfigs() throws IOException {
        do {
           handlePartyDecision();
        } while (!finishedSetup);
    }

    private void handlePartyDecision() throws IOException {
        String answer = playerImpl.sendQuestion("[1] Connect to an existing party\n[2] Create a new party\n> ");
        switch (answer) {
            case "1":
                handleConnectParty();
                break;
            case "2":
                handleCreateParty();
                break;
            default:
                finishedSetup = false;
        }
    }

    private void handleConnectParty() throws IOException {
        String partyName = playerImpl.sendQuestion("Type the name of the party you want to connect to, " +
                "or type '!list' to get the list of all the available parties: ");

        if(partyName.equals(Environment.PREFIX_LIST_PARTIES)) {
            listAllParties();

            finishedSetup = false;
        } else {
            searchAndConnectToParty(partyName);
        }
    }

    private void handleCreateParty() throws IOException {
        String partyName = playerImpl.sendQuestion("What is the name of the party? ");
        String competitorResponse = playerImpl.sendQuestion("Do you wish to start the match with a bot? [Y] | [N]");

        boolean error = false;

        try {
            if(competitorResponse.equalsIgnoreCase(Environment.OPT_YES))
                manager.createConnectAndStart(partyName, playerImpl);
            else
                manager.createPartyAndConnect(partyName, playerImpl);
        } catch (FullPartyException | PartyAlreadyExistsException e) {
            playerImpl.out.sendMessage("Error creating party: " + e.getMessage());

            error = true;
        }

        finishedSetup = !error;
    }

    private void searchAndConnectToParty(String partyName) {
        try {
            manager.connectClientToParty(playerImpl, partyName);
            playerImpl.out.sendMessage("successfully connected to party!");

            finishedSetup = true;
        } catch (NoSuchPartyException | FullPartyException e) {
            playerImpl.out.sendMessage(e.getMessage());

            finishedSetup = false;
        }
    }

    private void listAllParties() {
        String allParties = "There is no parties available!";

        List<String> allPartiesList = manager.getAllPartyNames();
        if(allPartiesList != null)
            allParties = allPartiesList.stream().reduce((acm, current) -> acm + "\n" + current).orElse(null);

        playerImpl.out.sendMessage(allParties);
    }
}
