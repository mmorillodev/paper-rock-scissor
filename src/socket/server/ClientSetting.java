package socket.server;

import entity.PlayerImpl;
import exceptions.FullPartyException;
import exceptions.NoSuchPartyException;
import exceptions.PartyAlreadyExistsException;
import utils.Console;

import java.util.List;

public class ClientSetting {

    private PlayerImpl playerImpl;
    private GamePartyManager manager;
    private boolean finishedSetup;

    public ClientSetting(PlayerImpl playerImpl, GamePartyManager manager) {
        this.playerImpl = playerImpl;
        this.manager = manager;
    }

    public void startInitialConfigs() {
        do {
           handlePartyDecision();
        } while (!finishedSetup);
    }

    private void handlePartyDecision() {
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

    private void handleConnectParty() {
        String partyName = playerImpl.sendQuestion("Type the name of the party you want to connect to, or type '!list' to get the list of all the available parties: ");

        if(partyName.equals("!list")) {
            listAllParties();

            finishedSetup = false;
        } else {
            searchAndConnectToParty(partyName);
        }
    }

    private void handleCreateParty() {
        String partyName = playerImpl.sendQuestion("What is the name of the party? ");
        String competitorResponse = playerImpl.sendQuestion("Do you wish to start the match with a bot? [Y] | [N]");

        boolean error = false;

        try {
            if(competitorResponse.equalsIgnoreCase("y"))
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
        if(manager.includesParty(partyName)) {
            connectToParty(playerImpl, partyName);
            playerImpl.out.sendMessage("successfully connected to party!");

            finishedSetup = true;
        } else {
            playerImpl.out.sendMessage("Party not found!");

            finishedSetup = false;
        }
    }

    private void listAllParties() {
        String allParties = "There is no parties available!";

        List<String> allPartiesList = manager.getAllPartyNames();
        if(allPartiesList != null)
            allPartiesList.stream().reduce((acm, current) -> acm + "\n" + current).orElse(null);

        playerImpl.out.sendMessage(allParties);
    }

    private void connectToParty(PlayerImpl playerImpl, String partyName) {
        try {
            manager.connectClientToParty(playerImpl, partyName);
        } catch (NoSuchPartyException | FullPartyException e) {
            Console.err(e.getMessage());
        }
    }
}