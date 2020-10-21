package socket;

import utils.Console;
import utils.ScannerUtils;
import utils.Validators;

import static utils.StaticResources.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

    private Socket client;
    private String name;
    private PrintWriter writer;

    private final ScannerUtils scanner;

    public Client() {
        scanner = new ScannerUtils();
        try {
            client = new Socket(
                    scanner.getStringWithMessage(MSG_IP_REQUEST),
                    scanner.getIntWithMessage(MSG_CONNECTION_PORT_REQUEST, Validators::portValidator)
            );
            scanner.clearBuffer();

            this.name = scanner.getStringWithMessage(MSG_NAME_REQUEST);
            this.writer = new PrintWriter(client.getOutputStream());
        }
        catch (IOException e) {
            if (e.getMessage().contains(MSG_CONNECTION_REFUSED)) {
                System.err.println(PREFIX_SERVER_NOT_FOUND_ERROR + e.getMessage());
            }
        }
    }

    public void init() {
        String currentText = "";

        Thread messageReader = new Thread(this);
        messageReader.setName("messageReader");
        messageReader.start();

        while(!currentText.equalsIgnoreCase(PREFIX_LEAVE_SERVER)) {
            currentText = scanner.getStringWithMessage("").trim();

            if(currentText.length() != 0) {
                if (currentText.equalsIgnoreCase(PREFIX_CLEAR_CLI)) {
                    Console.cls();
                }
                else if (currentText.equalsIgnoreCase(PREFIX_CHANGE_NICKNAME)) {
                    currentText = this.name + " has changed its nickname to " + (this.name = scanner.getStringWithMessage(MSG_NAME_REQUEST));
                    sendMessage(currentText);
                }
                else {
                    sendMessage(currentText);
                }
            }
        }

        System.exit(0);
    }

    @Override
    public void run() {
        try {
            readMessages();
        }
        catch (IOException e) {
            Console.println(MSG_SERVER_DISCONNECTED);
        }
    }

    private void sendMessage(String message) {
        this.writer.println(this.name + ": " + message.trim());
        this.writer.flush();
    }

    private void readMessages() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String message;

        while(true) {
            message = reader.readLine();
            Console.println(message);
        }
    }
}
