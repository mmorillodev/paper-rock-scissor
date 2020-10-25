package utils;

import entity.Client;
import exceptions.NoSuchPartyException;

public class ClientSetting {

    Client client;
    GamePartyManager manager;
    boolean finishedSetup;

    public ClientSetting(Client client, GamePartyManager manager) {
        this.client = client;
        this.manager = manager;
    }

    public void startInitialConfigs() {
        do {
           handlePartyDecision();
        } while (!finishedSetup);
    }

    private void handlePartyDecision() {
        String answer = client.sendQuestion("[1] Connect to an existing party\n[2] Create a new party\n> ");
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
        String partyName = client.sendQuestion("Type the name of the party you want to connect to, or type '!list' to get the list of all the available parties: ");

        if(partyName.equals("!list")) {
            listAllParties();
            finishedSetup = false;
        } else {
            searchAndConnectToParty(partyName);
        }
    }

    private void handleCreateParty() {
        String partyName = client.sendQuestion("What is the name of the party? ");
        manager.createPartyAndConnect(partyName, client);

        finishedSetup = true;
    }

    private void searchAndConnectToParty(String partyName) {
        if(manager.includesParty(partyName)) {
            connectToParty(client, partyName);
            Console.println("successfully connected to party " + partyName);
            finishedSetup = true;
        } else {
            client.sendMessage("Party not found!");
            finishedSetup = false;
        }
    }

    private void listAllParties() {
        Console.println(manager.getAllPartyNames().stream().reduce((acm, current) -> acm + "\n" + current).orElse(null));
    }

    private void connectToParty(Client client, String partyName) {
        try {
            manager.connectClientToParty(client, partyName);
        } catch (NoSuchPartyException e) {
            Console.err(e.getMessage());
        }
    }
}
