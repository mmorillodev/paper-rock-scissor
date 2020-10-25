package utils;

import entity.PlayerImpl;
import exceptions.FullPartyException;
import exceptions.NoSuchPartyException;
import socket.server.GamePartyManager;

public class ClientSetting {

    PlayerImpl playerImpl;
    GamePartyManager manager;
    boolean finishedSetup;

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
        try {
            manager.createPartyAndConnect(partyName, playerImpl);
        } catch (FullPartyException e) {
            e.printStackTrace();
        }

        finishedSetup = true;
    }

    private void searchAndConnectToParty(String partyName) {
        if(manager.includesParty(partyName)) {
            connectToParty(playerImpl, partyName);
            Console.println("successfully connected to party " + partyName);
            finishedSetup = true;
        } else {
            playerImpl.sendMessage("Party not found!");
            finishedSetup = false;
        }
    }

    private void listAllParties() {
        Console.println(manager.getAllPartyNames().stream().reduce((acm, current) -> acm + "\n" + current).orElse(null));
    }

    private void connectToParty(PlayerImpl playerImpl, String partyName) {
        try {
            manager.connectClientToParty(playerImpl, partyName);
        } catch (NoSuchPartyException | FullPartyException e) {
            Console.err(e.getMessage());
        }
    }
}
