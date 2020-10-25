package socket;

import utils.Console;
import utils.ScannerUtils;

import static utils.StaticResources.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    private PrintWriter writer;

    private final ScannerUtils scanner;

    public Client() {
        scanner = new ScannerUtils();

        try {
            socket = new Socket(scanner.getStringWithMessage(MSG_IP_REQUEST), DEFAULT_PORT);
            scanner.clearBuffer();

            this.writer = new PrintWriter(socket.getOutputStream());
        }
        catch (IOException e) {
            if (e.getMessage().contains(MSG_CONNECTION_REFUSED)) {
                System.err.println(PREFIX_SERVER_NOT_FOUND_ERROR + e.getMessage());
            }
        }
    }

    public void init() {
        Thread messageReader = new Thread(this);
        messageReader.setName("messageReader");
        messageReader.start();

        while(true) {
            sendMessage(scanner.getStringWithMessage("> "));
        }
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
        this.writer.println(message.trim());
        this.writer.flush();
    }

    private void readMessages() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message;

        while(true) {
            message = reader.readLine();
            Console.println(message);
        }
    }
}
