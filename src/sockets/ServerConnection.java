package sockets;

import utils.Console;
import utils.ScannerUtils;
import utils.Validators;

import static utils.StaticResources.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;

public class ServerConnection {

    private Client client;
    private ServerSocket server;
    private String name;

    public ServerConnection() {
        ScannerUtils scanner = new ScannerUtils();

        try {
            server = new ServerSocket(scanner.getIntWithMessage(MSG_SERVER_PORT_REQUEST, Validators::portValidator));

            scanner.clearBuffer();

            name = scanner.getStringWithMessage(MSG_NAME_REQUEST);
        }
        catch(IOException e) {
            System.err.println(ERROR_BUSY_PORT);
        }
    }

    public void init() {
        ScannerUtils scanner = new ScannerUtils();
        String message = "";

        new KeepWaitingClientThread().start();

        while (!message.equalsIgnoreCase(PREFIX_SHUTDOWN_SERVER)) {
            message = scanner.getString().trim();

            if(message.length() == 0) continue;

            //TODO - HANDLE ATTACK INPUTS

        }

        System.exit(0);
    }

    private class KeepWaitingClientThread extends Thread {

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

    private class WaitMessages implements Runnable {
        Client client;

        WaitMessages(Client client) {
            this.client = client;
        }

        @Override
        public void run() {
            boolean isRunning = true;
            String message;

            while(isRunning) {
                try {
                    message = client.reader.readLine();
                    Console.println(message);
                }
                catch (IOException e) {
                    Console.println(MSG_DISCONNECTED_CLIENT);

                    client = null;
                    new KeepWaitingClientThread().start();

                    isRunning = false;
                }
            }
        }
    }
    class Client {
        private Socket clientSocket;
        private PrintWriter writer;
        private BufferedReader reader;

        public Client(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            this.reader       = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.writer       = new PrintWriter(clientSocket.getOutputStream());

            out.println(MSG_CONNECTED_CLIENT);

            Thread messageReader = new Thread(new ServerConnection.WaitMessages(this));
            messageReader.setName("messageReader-" + toString());
            messageReader.start();
        }

        public void sendMessage(String message) {
            writer.println(message);
            writer.flush();
        }
    }
}