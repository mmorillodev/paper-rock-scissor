package socket;

import entity.Client;
import entity.GameParty;
import utils.Console;
import utils.ScannerUtils;
import utils.Validators;

import static utils.StaticResources.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.out;

public class Server {

    private Client client;
    private java.net.ServerSocket server;
    private String name;

    private List<GameParty> parties = new LinkedList<>();

    public Server() {
        ScannerUtils scanner = new ScannerUtils();

        try {
            server = new java.net.ServerSocket(scanner.getIntWithMessage(MSG_SERVER_PORT_REQUEST, Validators::portValidator));
            scanner.clearBuffer();
            name = scanner.getStringWithMessage(MSG_NAME_REQUEST);
        }
        catch(IOException e) {
            Console.err(e.getMessage());
            e.printStackTrace();
            Console.err(ERROR_BUSY_PORT);
        }
    }

    public void init() throws IOException {
        ScannerUtils scanner = new ScannerUtils();
        String message = "";

        new AwaitClientThread().start();
    }

    private class AwaitClientThread extends Thread {

        private boolean keepWaiting = true;

        @Override
        public void run() {
            try {
                getClients();
            }
            catch (IOException e) {
                out.println(PREFIX_EXCEPTION_THROWN + e.getMessage());
                keepWaiting = false;
            }
        }

        void getClients() throws IOException {
            while (keepWaiting) {
                client = new Client(server.accept());

                this.keepWaiting = false;
            }
        }
    }
}